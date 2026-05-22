package com.studysync.creational;

import com.studysync.creational.prototype.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Prototype Pattern — GroupTemplateRegistry and TemplateStudyGroup")
class GroupPrototypeTest {

    @Test
    @DisplayName("Clone is a different object but has identical field values")
    void testTemplateCloning() {
        System.out.println("\n--- TEST: Template Cloning ---");

        TemplateStudyGroup original = new TemplateStudyGroup("Exam Prep", "CS301", 8);
        original.setPrivate(true);
        original.addTag("priority", "high");

        TemplateStudyGroup cloned = (TemplateStudyGroup) original.clone();

        System.out.println("Original object hash: " + System.identityHashCode(original));
        System.out.println("Clone object hash: " + System.identityHashCode(cloned));

        assertNotSame(original, cloned, "Clone must be a different object in memory");
        System.out.println("Assertion passed: clone is a different object.");

        assertEquals(original.getTemplateName(), cloned.getTemplateName(), "Template name must be copied");
        System.out.println("Assertion passed: template name copied.");

        assertEquals(original.getSuggestedCourse(), cloned.getSuggestedCourse(), "Suggested course must be copied");
        System.out.println("Assertion passed: suggested course copied.");

        assertEquals(original.getRecommendedCapacity(), cloned.getRecommendedCapacity(), "Capacity must be copied");
        System.out.println("Assertion passed: recommended capacity copied.");

        assertEquals(original.isPrivate(), cloned.isPrivate(), "Private flag must be copied");
        System.out.println("Assertion passed: private flag copied.");

        System.out.println("PASS");
    }

    @Test
    @DisplayName("Modifying clone does not affect original")
    void testCloningIsDeep() {
        System.out.println("\n--- TEST: Deep Copy Isolation ---");

        TemplateStudyGroup original = new TemplateStudyGroup("Original", "CS101", 5);
        original.addTag("shared", "value");

        TemplateStudyGroup cloned = (TemplateStudyGroup) original.clone();
        cloned.addTag("clone-only", "data");

        System.out.println("Original tags count: " + original.getDefaultTags().size());
        System.out.println("Clone tags count: " + cloned.getDefaultTags().size());

        assertFalse(
                original.getDefaultTags().containsKey("clone-only"),
                "Adding a tag to the clone must not affect the original"
        );
        System.out.println("Assertion passed: original was not affected by clone change.");

        System.out.println("PASS");
    }

    @Test
    @DisplayName("Registry returns a new clone each call")
    void testRegistryReturnsClonedTemplates() {
        System.out.println("\n--- TEST: Registry Returns Independent Clones ---");

        GroupPrototype template1 = GroupTemplateRegistry.getTemplate("exam_prep");
        GroupPrototype template2 = GroupTemplateRegistry.getTemplate("exam_prep");

        System.out.println("Call 1 hash: " + System.identityHashCode(template1));
        System.out.println("Call 2 hash: " + System.identityHashCode(template2));

        assertNotSame(template1, template2, "Each getTemplate() call must return a new clone");
        System.out.println("Assertion passed: registry returned two different clone objects.");

        System.out.println("PASS");
    }

    @Test
    @DisplayName("Customizing a clone does not affect registry prototype")
    void testCustomizeCloneDoesNotAffectRegistry() {
        System.out.println("\n--- TEST: Customize Clone Independence ---");

        GroupPrototype clone1 = GroupTemplateRegistry.getTemplate("assignment");
        clone1.customize("Completely Different Name", "New description");

        System.out.println("After customize, clone1: " + clone1);

        GroupPrototype clone2 = GroupTemplateRegistry.getTemplate("assignment");
        System.out.println("Fresh clone from registry: " + clone2);

        assertNotEquals(
                clone1.toString(),
                clone2.toString(),
                "A customized clone must differ from a fresh clone"
        );
        System.out.println("Assertion passed: customized clone did not affect registry prototype.");

        System.out.println("PASS");
    }

    @Test
    @DisplayName("Unknown key throws IllegalArgumentException with correct message")
    void testUnknownTemplateThrowsException() {
        System.out.println("\n--- TEST: Unknown Template Key ---");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> GroupTemplateRegistry.getTemplate("non_existent"),
                "Non-existent key must throw IllegalArgumentException"
        );

        System.out.println("Exception message: " + ex.getMessage());

        assertTrue(
                ex.getMessage().contains("No template found"),
                "Exception message must contain 'No template found'"
        );
        System.out.println("Assertion passed: exception message contains 'No template found'.");

        assertTrue(
                ex.getMessage().contains("non_existent"),
                "Exception message must include the bad key"
        );
        System.out.println("Assertion passed: exception message contains bad key.");

        System.out.println("PASS");
    }
}