package com.studysync.creational.factorymethod;

/**
 * Concrete Creator: produces a MarkdownExporter.
 */
public class MarkdownExporterFactory extends StudyResourceExporterFactory {

    @Override
    public StudyResourceExporter createExporter() {
        return new MarkdownExporter();
    }
}
