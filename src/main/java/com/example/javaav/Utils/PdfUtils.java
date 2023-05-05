package com.example.javaav.Utils;

import com.example.javaav.Model.Employees;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.scene.control.Alert;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PdfUtils {
    public Document document;

    /**
     * @param nameDoc
      */
    public PdfUtils(String nameDoc) {
        document = new Document();
        try {
            String name = Arrays.stream(nameDoc.split("-")).collect(Collectors.toList()).get(0);
            String userHome = System.getProperty("user.home");
            Path desktopPath = Paths.get(userHome, "Desktop", "pdfBeyrout", name);

            if (!Files.exists(desktopPath)) {
                Files.createDirectories(desktopPath);
            }
            Path filePath = desktopPath.resolve(nameDoc);
            PdfWriter.getInstance(document, new FileOutputStream(filePath.toFile()));
            document.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param table
     * @param nameColum
     */
    public void addTableHeader(PdfPTable table, List<String> nameColum) {
        nameColum.stream()
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });

    }

    /**
     * @param table
     * @param dataRow
     * @param nameColum
     */
    public void addRows(PdfPTable table, List<HashMap<String, String>> dataRow, List<String> nameColum) {
        dataRow.stream().forEach(row -> {
            nameColum.stream().forEach(nameCol -> {
                String data = row.get(nameCol);
                table.addCell(data);
            });
        });
    }

    /**
     * @param table
     * @throws DocumentException
     */
    public void saveTable(PdfPTable table) throws DocumentException {
        document.add(table);
    }

    /**
     * close the PDF
     */
    public void closePDf() {
        document.close();
    }

    static class Footer extends PdfPageEventHelper {
        public void onEndPage(PdfWriter writer, Document document) {
            Paragraph footer = new Paragraph("© 2023, MyCompany");
            footer.setAlignment(Element.ALIGN_CENTER);
            try {
                document.add(new Phrase("\n"));
                document.add(footer);
            } catch (DocumentException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Erreur de cfreation");
                alert.show();
            }
        }
    }
}
