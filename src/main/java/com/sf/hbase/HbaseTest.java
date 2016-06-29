package com.sf.hbase;

import com.sf.utils.ByteCompress;
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
//        writeCompressPDFToHBase("pdf012");
        readUnCompressPDFFromHbase("pdf012");
    }

    /**
     * @throws Exception
     * 从hbase读取图片（读取到解析写入桌面花费3秒）
     */
    public static void readPDFFromHbase(String rowkey)throws Exception{
        long begintime = System.currentTimeMillis();
        System.out.println("begin time"+begintime);
//        byte[] data = getData(conf, "pdf002");
        byte[] data = getData(conf, rowkey);
        System.out.println("len:" + data.length);
        PdfReader.write(data, "C:\\Users\\Public\\Desktop\\bbb.pdf");

        long endtime = System.currentTimeMillis();
        System.out.println("end time"+endtime);
        System.out.println(" time:"+(endtime-begintime)/1000);
    }

    /**
     * @throws Exception
     * 从hbase读取图片（读取到解析写入桌面花费3秒）
     */
    public static void readUnCompressPDFFromHbase(String rowkey)throws Exception{
        long begintime = System.currentTimeMillis();
        System.out.println("begin time"+begintime);
//        byte[] data = getData(conf, "pdf002");
        byte[] data = getData(conf, rowkey);
        byte[] unCompressData = ByteCompress.unBZip2(data);
        System.out.println("len:"+data.length+"uncompress len:" + unCompressData.length);
        PdfReader.write(unCompressData, "C:\\Users\\Public\\Desktop\\cccc.pdf");

        long endtime = System.currentTimeMillis();
        System.out.println("end time"+endtime);
        System.out.println(" time:"+(endtime-begintime)/1000);
    }

    /**
     * @throws Exception
     * 去读PDF写入hbase
     */
    public static void writePDFToHBase(String rowkey) throws Exception{
        byte[] data = PdfReader.readFile("C:\\Users\\Public\\Desktop\\bbb.pdf");
//        putByteData(conf, "pdf010", data);
        putByteData(conf, rowkey, data);
    }

    /**
     * @throws Exception
     * 压缩去读PDF写入hbase
     */
    public static void writeCompressPDFToHBase(String rowkey) throws Exception{
        byte[] data = PdfReader.readFile("C:\\Users\\Public\\Desktop\\bbb.pdf");
//        putByteData(conf, "pdf010", data);
        byte[] compressByteData = ByteCompress.bZip2(data);
        System.out.println("byte[] len:"+data.length+"  compress len:"+compressByteData.length);
//        byte[] toHexData=ByteCompress.bytesToHexString(compressByteData);
        putByteData(conf, rowkey, compressByteData);
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
