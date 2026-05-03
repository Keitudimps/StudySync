package com.studysync.creational.factorymethod;

/**
 * Concrete Product: exports study resources as a PDF document.
 */
public class PdfExporter implements StudyResourceExporter {

    @Override
    public boolean export(String content) {
        System.out.println("Exporting to PDF: " + content);
        return true;
    }

    @Override
    public String getFormatName() {
        return "PDF";
    }
}
