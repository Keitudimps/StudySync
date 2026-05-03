package com.studysync.creational.factorymethod;

/**
 * Concrete Product: exports study resources as a CSV file.
 */
public class CsvExporter implements StudyResourceExporter {

    @Override
    public boolean export(String content) {
        System.out.println("Exporting to CSV: " + content);
        return true;
    }

    @Override
    public String getFormatName() {
        return "CSV";
    }
}
