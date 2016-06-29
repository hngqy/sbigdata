package com.sf.utils;

import com.sf.hbase.PdfReader;
import org.apache.tools.bzip2.CBZip2InputStream;
import org.apache.tools.bzip2.CBZip2OutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.*;
/*import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZInputStream;
import com.jcraft.jzlib.ZOutputStream;*/


public class ByteCompress {
    /***
     * 压缩GZip
     *
     * @param data
     * @return
     */
    public static byte[] gZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }
    /***
     * 解压GZip
     *
     * @param data
     * @return
     */
    public static byte[] unGZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            gzip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }
    /***
     * 压缩Zip
     *
     * @param data
     * @return
     */
    public static byte[] zip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zip = new ZipOutputStream(bos);
            ZipEntry entry = new ZipEntry("zip");
            entry.setSize(data.length);
            zip.putNextEntry(entry);
            zip.write(data);
            zip.closeEntry();
            zip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }
    /***
     * 解压Zip
     *
     * @param data
     * @return
     */
    public static byte[] unZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ZipInputStream zip = new ZipInputStream(bis);
            while (zip.getNextEntry() != null) {
                byte[] buf = new byte[1024];
                int num = -1;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((num = zip.read(buf, 0, buf.length)) != -1) {
                    baos.write(buf, 0, num);
                }
                b = baos.toByteArray();
                baos.flush();
                baos.close();
            }
            zip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }
    /***
     * 压缩BZip2
     *
     * @param data
     * @return
     */
    public static byte[] bZip2(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            CBZip2OutputStream bzip2 = new CBZip2OutputStream(bos);
            bzip2.write(data);
            bzip2.flush();
            bzip2.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }
    /***
     * 解压BZip2
     *
     * @param data
     * @return
     */
    public static byte[] unBZip2(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            CBZip2InputStream bzip2 = new CBZip2InputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = bzip2.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            bzip2.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }
    /**
     * 把字节数组转换成16进制字符串
     *
     * @param bArray
     * @return
     */
    public static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    /**
     * 压缩数据
     *
     * @param object
     * @return
     * @throws IOException
     */
/*    public static byte[] jzlib(byte[] object) {
        byte[] data = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ZOutputStream zOut = new ZOutputStream(out,
                    JZlib.Z_DEFAULT_COMPRESSION);
            DataOutputStream objOut = new DataOutputStream(zOut);
            objOut.write(object);
            objOut.flush();
            zOut.close();
            data = out.toByteArray();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }*/
    /**
     * 解压被压缩的数据
     *
     * @param
     * @return
     * @throws IOException
     */
   /* public static byte[] unjzlib(byte[] object) {
        byte[] data = null;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(object);
            ZInputStream zIn = new ZInputStream(in);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = zIn.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            data = baos.toByteArray();
            baos.flush();
            baos.close();
            zIn.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }*/
    public static void main(String[] args) throws Exception{
        String s = "this is a test";

        byte[] b1 = zip(s.getBytes());
        System.out.println("length:"+b1.length+ "  zip:" + bytesToHexString(b1)+"  length:"+bytesToHexString(b1).length());
        byte[] b2 = unZip(b1);
        System.out.println("length:"+b2.length+ "  unZip:" + new String(b2));
        byte[] b3 = bZip2(s.getBytes());
        System.out.println("bZip2:" + bytesToHexString(b3)+"  length:"+bytesToHexString(b3).length());
        byte[] b4 = unBZip2(b3);
        System.out.println("unBZip2:" + new String(b4));
        byte[] b5 = gZip(s.getBytes());
        System.out.println("bZip2:" + bytesToHexString(b5)+"  length:"+bytesToHexString(b5).length());
        byte[] b6 = unGZip(b5);
        System.out.println("unBZip2:" + new String(b6));
    /*    byte[] b7 = jzlib(s.getBytes());
        System.out.println("jzlib:" + bytesToHexString(b7)+"  length:"+bytesToHexString(b7).length());
        byte[] b8 = unjzlib(b7);
        System.out.println("unjzlib:" + new String(b8));*/

        byte[] data = PdfReader.readFile("C:\\Users\\Public\\Desktop\\a.pdf");
//        putByteData(conf, "pdf010", data);
        byte[] bZip2Data = ByteCompress.bZip2(data);
        byte[] gZipData = ByteCompress.gZip(data);
        byte[] zipData = ByteCompress.zip(data);
        byte[] henData = ByteCompress.bytesToHexString(data).getBytes();
        System.out.print("data:"+data.length);
        System.out.print("bZip2Data:"+bZip2Data.length);
        System.out.print("gZipData:"+gZipData.length);
        System.out.print("zipData:"+zipData.length);

        PdfReader.write(ByteCompress.unBZip2(bZip2Data),"C:\\Users\\Public\\Desktop\\d1.pdf");
/*        PdfReader.write(gZipData,"C:\\Users\\Public\\Desktop\\d2.pdf");
        PdfReader.write(zipData,"C:\\Users\\Public\\Desktop\\d3.pdf");
        PdfReader.write(henData,"C:\\Users\\Public\\Desktop\\d5.pdf");*/

    }
}