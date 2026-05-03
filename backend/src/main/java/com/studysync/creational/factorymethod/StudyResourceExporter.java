package com.studysync.creational.factorymethod;

/**
 * Product interface for the Factory Method pattern.
 * Represents a strategy for exporting study session resources.
 */
public interface StudyResourceExporter {
    /**
     * Exports the given content to the target format.
     *
     * @param content the study resource content to export
     * @return true if the export succeeded
     */
    boolean export(String content);

    /**
     * Returns the name of this export format.
     *
     * @return format identifier string
     */
    String getFormatName();
}
