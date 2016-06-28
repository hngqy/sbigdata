package com.sf;

/**
 * Created by 01110635 on 2016/6/15.
 */

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

public class StripPDFContent {
    public static String getText(File file) throws Exception {
        boolean sort = false;
        int startPage = 1;
        int endPage = 10;
        PDDocument document = null;
        try {
            try {
                document = PDDocument.load(file);
            } catch (MalformedURLException e) {

            }
            PDFTextStripper stripper = new PDFTextStripper();
//            stripper.setSortByPosition(sort);
            stripper.setStartPage(startPage);
            stripper.setEndPage(endPage);
            return stripper.getText(document);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
        }
        return null;
    }


    public static void toImages(File file) throws Exception {
        PDDocument document = document = PDDocument.load(file);
        PDDocumentCatalog cata = document.getDocumentCatalog();
        List pages = cata.getAllPages();
        int count = 1;
        for (int i = 0; i < pages.size(); i++) {
            PDPage page = (PDPage) pages.get(i);
            if (null != page) {
                BufferedImage img1 = page.convertToImage();

                File f = new File("E:\\images" + i + ".PNG");
                ImageIO.write(img1, "PNG", f);
            }
        }
    }

    public static void main(String[] args){
        File file=new File("C:\\Users\\Public\\Desktop\\a.pdf");
        try{
             toImages(file);
//            String cont=getText(file);
//            System.out.println(cont);
        }catch(Exception e){
            System.out.println("Strip failed.");
            e.printStackTrace();
        }
    }
}