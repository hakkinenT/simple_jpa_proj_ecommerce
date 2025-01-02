package com.example.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.example.models.views.ProductReport;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;


public class PDFGenerator {
    public static void generateProductReport(String filePath, List<ProductReport> products) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph(new Text("Relatório de Produtos").setFontSize(16).setStrokeWidth(2)));
        document.add(new Paragraph(" "));

        float[] columnWidths = {1, 3, 2};
        Table table = new Table(columnWidths);
        Color headerColor = new DeviceRgb(200, 200, 200);

        table.addHeaderCell(new Cell().add(new Paragraph("ID")).setBackgroundColor(headerColor).setPadding(5));
        table.addHeaderCell(new Cell().add(new Paragraph("Nome")).setBackgroundColor(headerColor).setPadding(5));
        table.addHeaderCell(new Cell().add(new Paragraph("Total de Vendas")).setBackgroundColor(headerColor).setPadding(5));

        for (ProductReport product : products) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(product.getId()))).setPadding(5));
            table.addCell(new Cell().add(new Paragraph(product.getName())).setPadding(5));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(product.getTotalSales()))).setPadding(5));
        }

        document.add(table);
        document.close();
    }

    public static void generateProductReportView(String filePath, List<ProductReport> products) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Relatório de Produtos").setFontSize(16));

        float[] columnWidths = {1, 3, 2};
        Table table = new Table(columnWidths);
        table.addHeaderCell("ID");
        table.addHeaderCell("Nome");
        table.addHeaderCell("Total de Vendas");

        for (ProductReport product : products) {
            table.addCell(String.valueOf(product.getId()));
            table.addCell(product.getName());
            table.addCell(String.valueOf(product.getTotalSales()));
        }

        document.add(table);
        document.close();
    }
}
