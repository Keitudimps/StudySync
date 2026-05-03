package com.studysync.creational;

import com.studysync.creational.prototype.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GroupPrototypeTest {

    @Test
    void testTemplateCloning() {
        System.out.println("\n=== TEST: Template Cloning ===");
        System.out.println("Creating original template: Exam Prep...");

        TemplateStudyGroup original = new TemplateStudyGroup("Exam Prep", "CS301", 8);
        original.setPrivate(true);
        original.addTag("priority", "high");

        System.out.println("Cloning template...");
        TemplateStudyGroup cloned = (TemplateStudyGroup) original.clone();

        System.out.println("Original name     : " + original.getTemplateName());
        System.out.println("Clone name        : " + cloned.getTemplateName());
        System.out.println("Original course   : " + original.getSuggestedCourse());
        System.out.println("Clone course      : " + cloned.getSuggestedCourse());
        System.out.println("Original capacity : " + original.getRecommendedCapacity());
        System.out.println("Clone capacity    : " + cloned.getRecommendedCapacity());
        System.out.println("Original private  : " + original.isPrivate());
        System.out.println("Clone private     : " + cloned.isPrivate());

        assertNotSame(original, cloned, "Clone must be a different object reference");
        assertEquals(original.getTemplateName(), cloned.getTemplateName(),
                "Clone must have the same template name");
        assertEquals(original.getSuggestedCourse(), cloned.getSuggestedCourse(),
                "Clone must have the same suggested course");
        assertEquals(original.getRecommendedCapacity(), cloned.getRecommendedCapacity(),
                "Clone must have the same recommended capacity");
        assertEquals(original.isPrivate(), cloned.isPrivate(),
                "Clone must have the same privacy setting");

        System.out.println("Mutating clone name to 'Modified Name'...");
        cloned.customize("Modified Name", "Modified description");
        System.out.println("Original name after clone mutation: " + original.getTemplateName());

        assertEquals("Exam Prep", original.getTemplateName(),
                "Mutating the clone must not change the original's name");

        System.out.println("✓ PASS — template cloning successful: objects are independent");
    }

    @Test
    void testRegistryReturnsClonedTemplates() {
        System.out.println("\n=== TEST: Registry Returns Cloned Templates ===");
        System.out.println("Retrieving same template key 'exam_prep' twice from registry...");

        GroupPrototype template1 = GroupTemplateRegistry.getTemplate("exam_prep");
        GroupPrototype template2 = GroupTemplateRegistry.getTemplate("exam_prep");

        System.out.println("Template 1 hash: " + System.identityHashCode(template1));
        System.out.println("Template 2 hash: " + System.identityHashCode(template2));

        assertNotNull(template1, "Registry must return a non-null template");
        assertNotNull(template2, "Registry must return a non-null template on second call");
        assertNotSame(template1, template2,
                "Registry must return independent clones, not the same object");

        System.out.println("✓ PASS — registry correctly returns independent clones");
    }

    @Test
    void testCustomizeCloneDoesNotAffectRegistry() {
        System.out.println("\n=== TEST: Customize Clone Does Not Affect Registry ===");
        System.out.println("Getting 'assignment' template from registry...");

        TemplateStudyGroup template = (TemplateStudyGroup) GroupTemplateRegistry.getTemplate("assignment");
        String originalName = template.getTemplateName();
        System.out.println("Original template name: " + originalName);

        System.out.println("Customizing retrieved clone to 'Custom Assignment Group'...");
        template.customize("Custom Assignment Group", "Custom description");
        System.out.println("Clone name after customization: " + template.getTemplateName());

        System.out.println("Fetching a fresh clone from registry...");
        TemplateStudyGroup freshTemplate = (TemplateStudyGroup) GroupTemplateRegistry.getTemplate("assignment");
        System.out.println("Fresh template name: " + freshTemplate.getTemplateName());

        assertEquals(originalName, freshTemplate.getTemplateName(),
                "Customizing a retrieved clone must not affect the registry's stored prototype");

        System.out.println("✓ PASS — customization did not affect the registry's original template");
    }

    @Test
    void testUnknownTemplateThrowsException() {
        System.out.println("\n=== TEST: Unknown Template Throws Exception ===");
        System.out.println("Attempting to get non-existent template key 'non_existent'...");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> GroupTemplateRegistry.getTemplate("non_existent"),
                "Requesting an unknown template key must throw IllegalArgumentException"
        );

        System.out.println("Exception caught: " + exception.getMessage());

        assertTrue(exception.getMessage().contains("No template found"),
                "Exception message must indicate no template was found");

        System.out.println("✓ PASS — exception handling working correctly");
    }
}
