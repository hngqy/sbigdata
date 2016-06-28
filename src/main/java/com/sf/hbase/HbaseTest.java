package com.sf.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;

/**
 * Created by 01110635 on 2016/5/16.
 * 电子发票测试用例（hbase存放pdf文件）
 * pdf以二进制读写到hbase
 */
public class HbaseTest {

    public static Configuration conf = null;

    public static void main(String[] args) throws Exception {
        runjob(args);
    }


    static {
        conf  = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","CNSZ22PL0060,CNSZ22PL0058,CNSZ22PL0059");
        conf.set("hbase.zookeeper.property.clientPort","2181");
        conf.set("hbase.master", "CNSZ22PL0058:600000");
//        conf.addResource("/resources/hbase-site.xml");
//        conf.addResource("/resources/hdfs-site.xml");
    }

    public static void runjob(String[] args) throws Exception {
        //create table

//        createTableOfUser("test",new String[]{"cf"});
    /*    StripPDFContent pdf = new StripPDFContent();
        File file=new File("C:\\Users\\Public\\Desktop\\a.pdf");
        String cont=pdf.getText(file);
        putData(conf,"56789",cont);*/
    /*    byte[] data = PdfReader.readFile("C:\\Users\\Public\\Desktop\\a.pdf");
        putByteData(conf, "pdf002", data);*/

      /*
*/
        /* byte[] data = getData(conf,"56789");
         System.out.print(new String(data));*/
//        byte[] data = PdfReader.readFile("C:\\Users\\Public\\Desktop\\a.pdf");
//        byte[] data = PdfReader.readFile("E:\\images\\a.pptx");
//        putByteData(conf,"ppt01",data);
//        putData(conf,"ppt",new String(data));
       /* byte[] data = getData(conf,"ppt01");

        System.out.println("len:" + data.length);
        PdfReader.write(data,"E:\\images\\b.pptx");

        System.out.println("====");
        for (int i = 0; i <1000 ; i++) {
            System.out.print(data[i] + " ");
            if(i % 10 == 0){
                System.out.println("");
            }
        }*/



    }

    /**
     * @throws Exception
     * 从hbase读取图片（读取到解析写入桌面花费3秒）
     */
    public static void readPDFFromHbase()throws Exception{
        long begintime = System.currentTimeMillis();
        System.out.println("begin time"+begintime);
        byte[] data = getData(conf, "pdf002");

        System.out.println("len:" + data.length);
        PdfReader.write(data, "C:\\Users\\Public\\Desktop\\bbb.pdf");

        long endtime = System.currentTimeMillis();
        System.out.println("end time"+endtime);
        System.out.println(" time:"+(endtime-begintime)/1000);
    }

    /**
     * @throws Exception
     * 去读PDF写入hbase
     */
    public static void writePDFToHBase() throws Exception{
        byte[] data = PdfReader.readFile("C:\\Users\\Public\\Desktop\\bbb.pdf");
        putByteData(conf, "pdf010", data);
    }

    public static void createTableOfUser(String tablename,String[] familys) throws  Exception{
        HBaseAdmin hBaseAdmin = new HBaseAdmin(conf);
        if(hBaseAdmin.tableExists(tablename.getBytes())){
            System.out.println("hbase is exist table:"+tablename);
        }else {
            HTableDescriptor table = new HTableDescriptor(tablename);
            for (String family : familys) {
                table.addFamily(new HColumnDescriptor(family));
            }
            hBaseAdmin.createTable(table);
        }
    }

    /**
     * 这种方式存放二进制会有问题，String类型对长度有限制
     * @param config
     * @param rowkey
     * @param data
     * @throws Exception
     */
    public static void putData(Configuration config,String rowkey,String data) throws  Exception{
        Connection conn = ConnectionFactory.createConnection(config);
        HTable table = (HTable)conn.getTable(TableName.valueOf("paper".getBytes()));
        Put put = new Put(rowkey.getBytes());
        put.addColumn("cf".getBytes(), "pdf".getBytes(), data.getBytes());
        table.put(put);
        table.close();
        conn.close();
    }
    public static byte[] getData(Configuration config,String rowkey) throws  Exception{
        Connection conn = ConnectionFactory.createConnection(config);
        HTable table = (HTable)conn.getTable(TableName.valueOf("paper".getBytes()));
        Get get = new Get(rowkey.getBytes());
        Result rs = table.get(get);
        byte[] data = rs.getValue("cf".getBytes(),"pdf".getBytes());
        table.close();
        conn.close();
        return data;
    }

    public static void putByteData(Configuration config,String rowkey,byte[] data) throws  Exception{
        Connection conn = ConnectionFactory.createConnection(config);
        HTable table = (HTable)conn.getTable(TableName.valueOf("paper".getBytes()));
        Put put = new Put(rowkey.getBytes());
        //data String存放二进制文件有长度限制（pdf）
        put.addColumn("cf".getBytes(), "pdf".getBytes(), data);
        table.put(put);
        table.close();
        conn.close();
    }

}
