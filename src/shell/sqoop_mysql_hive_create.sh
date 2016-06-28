#!/bin/bash

log=../log/sqoop_create_table.log
date=`date +"%Y%m%d %H:%M:%S"`
partdate=partdate

db_config_file=/opt/shell/simon/config/sqoop_create_table_config

cat ${db_config_file} | while read line
do
	hive_database=`echo $line | awk -F '|' '{print $2}'`
	ip=`echo $line | awk -F '|' '{print $4}'`
	port=`echo $line | awk -F '|' '{print $5}'`
	database=`echo $line | awk -F '|' '{print $6}'`
	username=`echo $line | awk -F '|' '{print $7}'`
	password=`echo $line | awk -F '|' '{print $8}'`
	tablename=`echo $line | awk -F '|' '{print $9}'`
	url=jdbc:mysql://${ip}:${port}/${database}
	echo "`date +"%Y%m%d %H:%M:%S"` .............$hive_database ------ $ip ---- $port ----$database ------$username -------$password ------$tablename" >>$log 2>&1
    #hive-partition-key 添加分区（目前只支持一个或者改动源码）
	sqoop create-hive-table --connect ${url} --table ${tablename} --username ${username} --password ${password} --hive-database ${hive_database} --hive-table ${tablename} --hive-partition-key ${partdate} >>$log 2>&1
done

if [ $? == 0 ];then
	echo "`date +"%Y%m%d %H:%M:%S"` ........sqoop create table is success!!!" >>$log 2>&1
fi

