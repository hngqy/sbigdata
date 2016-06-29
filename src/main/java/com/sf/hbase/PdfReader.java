package com.sf.hbase;

import java.io.*;

public class PdfReader {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        PdfReader pdfReader = new PdfReader();
        try {
//            PDDocument pdf = PDDocument.load(new File("C:\\Users\\Public\\Desktop\\a.pdf"));
           byte[] data = readFile("C:\\Users\\Public\\Desktop\\a.pdf");
            String file ="C:\\Users\\Public\\Desktop\\c.pdf";
           System.out.print( System.currentTimeMillis());
            write(data, file);
            System.out.print(System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static  byte[] readFile(String file)throws  Exception{
        FileInputStream fis = new FileInputStream(new File(file));
        byte[] data = new byte[] {};
        data = inputStreamToByte(fis);
        /*for (byte a:data){
            System.out.print(a);
        }*/
        return data;
    }

    public static  byte [] inputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bAOutputStream = new ByteArrayOutputStream();
        int ch;
        while((ch = is.read() ) != -1){
            bAOutputStream.write(ch);
        }
        byte data [] =bAOutputStream.toByteArray();
        bAOutputStream.close();
        return data;
    }

    public static void write(byte[] data,String file)throws Exception{
        FileOutputStream fos = new FileOutputStream (file);
        int size = 0;
        if (data.length > 0) {
            fos.write(data, 0, data.length);
        }
        fos.close();
    }

    public static void writeFile(byte[] data,String file)throws Exception{
        FileOutputStream fos = new FileOutputStream (file);
        int size = 0;
        if (data.length > 0) {

        }
        fos.close();
    }

}