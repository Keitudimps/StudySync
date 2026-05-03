package com.studysync.creational.factorymethod;

/**
 * Concrete Creator: produces a PdfExporter.
 */
public class PdfExporterFactory extends StudyResourceExporterFactory {

    @Override
    public StudyResourceExporter createExporter() {
        return new PdfExporter();
    }
}
