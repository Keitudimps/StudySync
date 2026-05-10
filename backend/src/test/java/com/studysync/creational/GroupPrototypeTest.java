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

        System.out.println("  Original object hash : " + System.identityHashCode(original));
        System.out.println("  Clone object hash    : " + System.identityHashCode(cloned));

        assertNotSame(original, cloned,
            "Clone must be a different object in memory — if this fails, clone() returned 'this'");
        assertEquals(original.getTemplateName(), cloned.getTemplateName(),
            "Template name must be copied to clone");
        assertEquals(original.getSuggestedCourse(), cloned.getSuggestedCourse(),
            "Suggested course must be copied to clone");
        assertEquals(original.getRecommendedCapacity(), cloned.getRecommendedCapacity(),
            "Recommended capacity must be copied to clone");
        assertEquals(original.isPrivate(), cloned.isPrivate(),
            "isPrivate flag must be copied to clone");

        System.out.println("  Name match        : " + original.getTemplateName().equals(cloned.getTemplateName()));
        System.out.println("  Course match      : " + original.getSuggestedCourse().equals(cloned.getSuggestedCourse()));
        System.out.println("  Capacity match    : " + (original.getRecommendedCapacity() == cloned.getRecommendedCapacity()));
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("Modifying clone does not affect original (deep copy verified)")
    void testCloningIsDeep() {
        System.out.println("\n--- TEST: Deep Copy Isolation ---");

        TemplateStudyGroup original = new TemplateStudyGroup("Original", "CS101", 5);
        original.addTag("shared", "value");

        TemplateStudyGroup cloned = (TemplateStudyGroup) original.clone();
        cloned.addTag("clone-only", "data");

        System.out.println("  Original tags count : " + original.getDefaultTags().size());
        System.out.println("  Clone tags count    : " + cloned.getDefaultTags().size());

        assertFalse(original.getDefaultTags().containsKey("clone-only"),
            "Adding a tag to the clone must NOT affect the original — " +
            "if this fails, clone() is doing a shallow copy of the tags map");

        System.out.println("  Original unaffected by clone modification: confirmed");
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("Registry returns a new clone each call — same key gives independent objects")
    void testRegistryReturnsClonedTemplates() {
        System.out.println("\n--- TEST: Registry Returns Independent Clones ---");

        GroupPrototype template1 = GroupTemplateRegistry.getTemplate("exam_prep");
        GroupPrototype template2 = GroupTemplateRegistry.getTemplate("exam_prep");

        System.out.println("  Call 1 hash : " + System.identityHashCode(template1));
        System.out.println("  Call 2 hash : " + System.identityHashCode(template2));

        assertNotSame(template1, template2,
            "Each getTemplate() call must return a NEW clone — " +
            "if this fails, the registry is returning the original prototype instead of cloning it");

        System.out.println("  Two calls produced two different objects: confirmed");
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("Customizing a clone does not affect registry's stored prototype")
    void testCustomizeCloneDoesNotAffectRegistry() {
        System.out.println("\n--- TEST: Customize Clone Independence ---");

        GroupPrototype clone1 = GroupTemplateRegistry.getTemplate("assignment");
        String originalName = clone1.toString();

        clone1.customize("Completely Different Name", "New description");
        System.out.println("  After customize, clone toString : " + clone1);

        GroupPrototype clone2 = GroupTemplateRegistry.getTemplate("assignment");
        System.out.println("  Fresh clone from registry       : " + clone2);

        assertNotEquals(clone1.toString(), clone2.toString(),
            "A customized clone must differ from a fresh clone — " +
            "if this fails, customize() is mutating the stored prototype");

        System.out.println("  Registry prototype was not affected by customization: confirmed");
        System.out.println("  PASS");
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

        System.out.println("  Exception message : " + ex.getMessage());

        assertTrue(ex.getMessage().contains("No template found"),
            "Exception message must contain 'No template found' — " +
            "if this fails, the error message in GroupTemplateRegistry was changed");
        assertTrue(ex.getMessage().contains("non_existent"),
            "Exception message must include the bad key 'non_existent'");

        System.out.println("  PASS");
    }
}
