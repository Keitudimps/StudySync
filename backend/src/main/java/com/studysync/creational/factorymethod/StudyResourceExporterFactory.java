package com.studysync.creational.factorymethod;

/**
 * Abstract Creator for the Factory Method pattern.
 * Subclasses override createExporter() to supply a concrete exporter.
 * The template method exportResource() orchestrates the export workflow.
 */
public abstract class StudyResourceExporterFactory {

    /**
     * Factory method — subclasses decide which exporter to instantiate.
     *
     * @return a concrete StudyResourceExporter
     */
    public abstract StudyResourceExporter createExporter();

    /**
     * Template method: creates the exporter and runs the export.
     *
     * @param content the study resource content to export
     * @return true if the export succeeded
     */
    public boolean exportResource(String content) {
        StudyResourceExporter exporter = createExporter();
        System.out.print("[" + exporter.getFormatName() + "] ");
        return exporter.export(content);
    }
}
