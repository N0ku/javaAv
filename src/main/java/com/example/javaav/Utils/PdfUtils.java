package com.example.javaav.Utils;

import com.example.javaav.Model.Employees;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class PdfUtils {
    public Document document;
    public PdfUtils(String nameDoc) {
        document = new Document();
        try{
            String userHome = System.getProperty("user.home");
            Path desktopPath = Paths.get(userHome, "Desktop");
            Path filePath = desktopPath.resolve(nameDoc);
            PdfWriter.getInstance(document, new FileOutputStream(filePath.toFile()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addTableHeader(PdfPTable table, List<String> nameColum) {
       nameColum.stream()
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
        document.open();
    }

    public void addRows(PdfPTable table, List<HashMap<String,String>> dataRow, List<String> nameColum) {
        dataRow.stream().forEach(row ->{
            nameColum.stream().forEach(nameCol -> {
               String data =  row.get(nameCol);
                table.addCell(data);
            });
        });
    }

    public void saveTable(PdfPTable table) throws DocumentException {
        document.add(table);
    }

    public void closePDf(){
        document.close();
    }


}
