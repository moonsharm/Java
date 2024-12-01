package com.example.demo.services;

import com.example.demo.model.CReportItem;
import com.example.demo.repositories.IRepositoryCars;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class CServiceReport {

    @Autowired
    private IRepositoryCars repositoryCars;
    public byte[] createReport()  {
        try(XWPFDocument document = new XWPFDocument())
        {
            //Заголовок
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            run.setFontSize(16);
            run.setFontFamily("Times New Roman");
            run.setText("Самые продаваемые машины");
            run.setBold(true);

            // create table with 3 rows and 4 columns
            XWPFTable table = document
                    .createTable(1, 3);
            table.setWidth("100%");

            // write to first row, first column
            XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0);
            p1.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun r1 = p1.createRun();
            r1.setBold(true);
            r1.setText("Машина");

            // write to first row, second column
            XWPFParagraph p2 = table.getRow(0).getCell(1).getParagraphs().get(0);
            p2.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun r2 = p2.createRun();
            r2.setBold(true);
            r2.setText("Цена");

            // write to first row, third column
            XWPFParagraph p3 = table.getRow(0).getCell(2).getParagraphs().get(0);
            p3.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun r3 = p3.createRun();
            r3.setBold(true);
            r3.setText("Количество проданных машин");

            List<CReportItem> items = repositoryCars.bestSellingCars();

            XWPFTableRow row;
            XWPFTableCell cell;
            for (CReportItem item : items) {
                row = table.createRow();
                cell = row.getCell(0);
                cell.setText(item.getName());
                cell = row.getCell(1);
                cell.setText(item.getPrice().toString());
                cell = row.getCell(2);
                cell.setText(item.getTotalSoldCount().toString());
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            document.write(stream);
            return stream.toByteArray();
        }
        catch(IOException ignored)
        {
            return new byte[0];
        }
    }
}
