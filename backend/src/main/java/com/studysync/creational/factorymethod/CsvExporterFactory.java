package com.studysync.creational.factorymethod;

/**
 * Concrete Creator: produces a CsvExporter.
 */
public class CsvExporterFactory extends StudyResourceExporterFactory {

    @Override
    public StudyResourceExporter createExporter() {
        return new CsvExporter();
    }
}
