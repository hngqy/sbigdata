package com.sf.java;

import java.io.*;

/**
 * Created by 01110635 on 2016/5/13.
 */
public class TString {
    public static void main(String[] args){
/*//        String path = "d:\\user\\01110635\\桌面\\Ewindows2.txt";
        String path = "//10.202.34.200//home//sfapp/10010.cap";
        try{
            long begin = System.currentTimeMillis();
//            readRemoteFile(path);
            readLine("/resources/log4j.properties");
            long end = System.currentTimeMillis();
            System.out.println("====="+(end-begin));
        }catch (Exception e){

        }*/
            System.out.println("ssss");
    }

    public static void readRemoteFile(String path)throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),"utf-8"));
        String line = "";
        while ((line= br.readLine()) != null){
            System.out.println(line);
        }
    }


    public static String readLine(String path) throws Exception {
        String outpath="d:\\user\\01110635\\桌面\\test.txt";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),"utf-8"));
        String line = "";
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outpath)),"utf-8"));
        while((line = br.readLine()) != null){
//            System.out.print(line);
            String data[] = line.split(" ");
            if(line.contains("Account Name") && line.contains(" Account Domain")) {
                int acount_bindex = line.indexOf("Account Name:");
                int acount_eindex = line.indexOf(" Account Domain");
                String acount_name = line.substring(acount_bindex + "Account Name:".length(), acount_eindex);
                bw.write(acount_name);
            }

        }
        return "";
    }
}
