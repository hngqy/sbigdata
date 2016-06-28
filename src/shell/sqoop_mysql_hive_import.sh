#!/bin/bash

date=`date --date="-1 day" +"%Y%m%d"`

if [ $# != 0 ];then
	date=${1}
	flag=${2}
fi
echo "${date}-------${flag}"
log=../log/sqoop_import_data_${date}.log
partdate=$date
partition_key=partdate

db_config_file=/opt/shell/simon/config/sqoop_table_data_config
hive_path=/user/hive/warehouse
sqoop_m=1
count=0
imp_num=32
sleep_second=5


cat ${db_config_file} | while read line
do
	hive_database=`echo $line | awk -F '|' '{print $2}'`
	ip=`echo $line | awk -F '|' '{print $4}'`
	port=`echo $line | awk -F '|' '{print $5}'`
	database=`echo $line | awk -F '|' '{print $6}'`
	username=`echo $line | awk -F '|' '{print $7}'`
	password=`echo $line | awk -F '|' '{print $8}'`
	tablename=`echo $line | awk -F '|' '{print $9}'`
	ct=`echo $line | awk -F '|' '{print $10}'`
	pkey=`echo $line | awk -F '|' '{print $11}'`
	url="jdbc:mysql://${ip}:${port}/${database}?zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false&useUnicode=true&characterEncoding=utf-8"

	targetdir=${hive_path}/${hive_database}.db/${tablename}/${partdate}/${ip}_${database}
	if [ "${flag}" == "0" ];then
		 sqlText="select * from ${database}.${tablename}  WHERE  \$CONDITIONS"
	else
		sqlText="select * from ${database}.${tablename}  WHERE DATE_FORMAT(${ct},'%Y%m%d')='${partdate}' and \$CONDITIONS"
	fi

	echo "${sqlText}"
	echo "`date +"%Y%m%d %H:%M:%S"` .............$hive_database ---$ip ---$port ---$database ----$username -------$password ------$tablename" >>$log 2>&1

	 hive -e "
              	use ${hive_database};
               	alter table ${tablename} add if not exists partition (partdate='${partdate}',host_db='${ip}_${database}') location '${targetdir}';
             	" >>$log 2>&1

    # wait  等待上面的任务完成才往下执行
	if  [ ${count} -eq ${imp_num} ];then
		echo "${username} is waiting ....................`date +"%Y%m%d %H:%M:%S"`" >>$log 2>&1
                count=1
                wait
	fi
    # { }& 中间的代码表示后台运行
    #--split-by 查询数据根据这个字段分隔，最好是Integer数字类型 m>1采用这个属性
    #-D mapreduce.job.jvm.numtasks 虚拟机重用（默认hadoop关闭了这个） 避免并行任务不断重启虚拟机
    {
             sqoop import -D mapred.job.name=sqoop_${tablename}_${date} -D mapreduce.job.jvm.numtasks=-1 -connect ${url} --username ${username} --password ${password}  --query "${sqlText}" --split-by ${pkey} -m ${sqoop_m}  --target-dir ${targetdir}  --delete-target-dir  --null-string '\\N' --null-non-string '\\N' --fields-terminated-by '\001'  --hive-drop-import-delims >>$log 2>&1

        } &
	echo "thread is ${count}+++++++++++++++++++++" >>$log 2>&1
	count=`expr $count + 1 `

	sleep ${sleep_second}
done

if [ $? == 0 ];then
	echo "`date +"%Y%m%d %H:%M:%S"` ........sqoop create table is success!!!" >>$log 2>&1
fi

