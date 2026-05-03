package com.studysync.creational;

import com.studysync.creational.factorymethod.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudyResourceExporterFactoryTest {

    @Test
    void testMarkdownExporter() {
        System.out.println("\n=== TEST: Markdown Exporter ===");
        System.out.println("Creating MarkdownExporterFactory...");

        StudyResourceExporterFactory factory = new MarkdownExporterFactory();
        StudyResourceExporter exporter = factory.createExporter();

        System.out.println("Concrete type : " + exporter.getClass().getSimpleName());
        System.out.println("Format name   : " + exporter.getFormatName());

        assertNotNull(exporter, "MarkdownExporterFactory must produce a non-null exporter");
        assertInstanceOf(MarkdownExporter.class, exporter,
                "MarkdownExporterFactory must produce a MarkdownExporter");
        assertEquals("MARKDOWN", exporter.getFormatName(),
                "Markdown exporter must report format name as 'MARKDOWN'");

        System.out.println("Exporting study notes...");
        boolean result = exporter.export("# Session Notes\n- Topic: Recursion");
        System.out.println("Export result : " + result);

        assertTrue(result, "Markdown export must return true on success");

        System.out.println("✓ PASS — Markdown exporter working correctly");
    }

    @Test
    void testPdfExporter() {
        System.out.println("\n=== TEST: PDF Exporter ===");
        System.out.println("Creating PdfExporterFactory...");

        StudyResourceExporterFactory factory = new PdfExporterFactory();
        StudyResourceExporter exporter = factory.createExporter();

        System.out.println("Concrete type : " + exporter.getClass().getSimpleName());
        System.out.println("Format name   : " + exporter.getFormatName());

        assertNotNull(exporter, "PdfExporterFactory must produce a non-null exporter");
        assertInstanceOf(PdfExporter.class, exporter,
                "PdfExporterFactory must produce a PdfExporter");
        assertEquals("PDF", exporter.getFormatName(),
                "PDF exporter must report format name as 'PDF'");

        System.out.println("Exporting session summary...");
        boolean result = exporter.export("Session: Algorithms Review — Room 3.24");
        System.out.println("Export result : " + result);

        assertTrue(result, "PDF export must return true on success");

        System.out.println("✓ PASS — PDF exporter working correctly");
    }

    @Test
    void testCsvExporter() {
        System.out.println("\n=== TEST: CSV Exporter ===");
        System.out.println("Creating CsvExporterFactory...");

        StudyResourceExporterFactory factory = new CsvExporterFactory();
        StudyResourceExporter exporter = factory.createExporter();

        System.out.println("Concrete type : " + exporter.getClass().getSimpleName());
        System.out.println("Format name   : " + exporter.getFormatName());

        assertNotNull(exporter, "CsvExporterFactory must produce a non-null exporter");
        assertInstanceOf(CsvExporter.class, exporter,
                "CsvExporterFactory must produce a CsvExporter");
        assertEquals("CSV", exporter.getFormatName(),
                "CSV exporter must report format name as 'CSV'");

        System.out.println("Exporting session attendance list...");
        boolean result = exporter.export("name,email\nAlice,alice@uni.ac.za\nBob,bob@uni.ac.za");
        System.out.println("Export result : " + result);

        assertTrue(result, "CSV export must return true on success");

        System.out.println("✓ PASS — CSV exporter working correctly");
    }

    @Test
    void testTemplateMethodDelegatesToConcreteExporter() {
        System.out.println("\n=== TEST: Template Method Delegates to Concrete Exporter ===");
        System.out.println("Testing exportResource() template method on all three factories...");

        StudyResourceExporterFactory markdownFactory = new MarkdownExporterFactory();
        StudyResourceExporterFactory pdfFactory = new PdfExporterFactory();
        StudyResourceExporterFactory csvFactory = new CsvExporterFactory();

        String content = "CS301 Exam Prep — Session Notes";

        System.out.println("\nCalling markdownFactory.exportResource()...");
        boolean mdResult = markdownFactory.exportResource(content);
        System.out.println("Result: " + mdResult);
        assertTrue(mdResult, "MarkdownExporterFactory.exportResource() must return true");

        System.out.println("\nCalling pdfFactory.exportResource()...");
        boolean pdfResult = pdfFactory.exportResource(content);
        System.out.println("Result: " + pdfResult);
        assertTrue(pdfResult, "PdfExporterFactory.exportResource() must return true");

        System.out.println("\nCalling csvFactory.exportResource()...");
        boolean csvResult = csvFactory.exportResource(content);
        System.out.println("Result: " + csvResult);
        assertTrue(csvResult, "CsvExporterFactory.exportResource() must return true");

        System.out.println("\nVerifying each factory produces a distinct exporter type...");
        Class<?> mdType  = markdownFactory.createExporter().getClass();
        Class<?> pdfType = pdfFactory.createExporter().getClass();
        Class<?> csvType = csvFactory.createExporter().getClass();

        System.out.println("Markdown exporter type : " + mdType.getSimpleName());
        System.out.println("PDF exporter type      : " + pdfType.getSimpleName());
        System.out.println("CSV exporter type      : " + csvType.getSimpleName());

        assertNotEquals(mdType, pdfType,
                "Markdown and PDF factories must produce different exporter types");
        assertNotEquals(pdfType, csvType,
                "PDF and CSV factories must produce different exporter types");
        assertNotEquals(mdType, csvType,
                "Markdown and CSV factories must produce different exporter types");

        System.out.println("✓ PASS — template method pattern working correctly");
    }
}
