package com.studysync.creational.factorymethod;

/**
 * Concrete Product: exports study resources as a Markdown file.
 */
public class MarkdownExporter implements StudyResourceExporter {

    @Override
    public boolean export(String content) {
        System.out.println("Exporting to Markdown: " + content);
        return true;
    }

    @Override
    public String getFormatName() {
        return "MARKDOWN";
    }
}
