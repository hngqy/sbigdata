package com.sf.hbase;

/**
 * Created by 01110635 on 2016/6/15.
 */
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.net.MalformedURLException;

public class StripPDFContent {
    public static String getText(File file)throws Exception{
        boolean sort=false;
        int startPage=1;
        int endPage=10;
        PDDocument document=null;
        try{
            try{
                document=PDDocument.load(file);
            }catch(MalformedURLException e){

            }
            PDFTextStripper stripper=new PDFTextStripper();
//            stripper.setSortByPosition(sort);
            stripper.setStartPage(startPage);
            stripper.setEndPage(endPage);
            return stripper.getText(document);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(document!=null){
                document.close();
            }
        }
        return null;
    }

    public static void main(String[] args){
        File file=new File("C:\\Users\\Public\\Desktop\\a.pdf");
        try{
            String cont=getText(file);
            System.out.println(cont);
        }catch(Exception e){
            System.out.println("Strip failed.");
            e.printStackTrace();
        }
    }
}