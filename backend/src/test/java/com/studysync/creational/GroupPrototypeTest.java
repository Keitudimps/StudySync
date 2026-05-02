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
        
        assertNotSame(original, cloned);
        assertEquals(original.getTemplateName(), cloned.getTemplateName());
        assertEquals(original.getSuggestedCourse(), cloned.getSuggestedCourse());
        assertEquals(original.getRecommendedCapacity(), cloned.getRecommendedCapacity());
        assertEquals(original.isPrivate(), cloned.isPrivate());
        
        System.out.println("Original: " + original.getTemplateName());
        System.out.println("Clone: " + cloned.getTemplateName());
        System.out.println("✓ Template cloning successful: objects are independent");
    }
    
    @Test
    void testRegistryReturnsClonedTemplates() {
        System.out.println("\n=== TEST: Registry Returns Cloned Templates ===");
        System.out.println("Retrieving same template twice from registry...");
        
        GroupPrototype template1 = GroupTemplateRegistry.getTemplate("exam_prep");
        GroupPrototype template2 = GroupTemplateRegistry.getTemplate("exam_prep");
        
        System.out.println("Template 1 hash: " + template1.hashCode());
        System.out.println("Template 2 hash: " + template2.hashCode());
        
        assertNotSame(template1, template2);
        
        System.out.println("✓ Registry correctly returns independent clones");
    }
    
    @Test
    void testCustomizeClone() {
        System.out.println("\n=== TEST: Customize Clone ===");
        System.out.println("Getting template from registry...");
        
        GroupPrototype template = GroupTemplateRegistry.getTemplate("assignment");
        System.out.println("Customizing clone...");
        template.customize("Custom Assignment Group", "Custom description");
        
        System.out.println("Getting another copy of same template...");
        GroupPrototype anotherTemplate = GroupTemplateRegistry.getTemplate("assignment");
        
        assertNotEquals(template.toString(), anotherTemplate.toString());
        
        System.out.println("✓ Customization did not affect original template");
    }
    
    @Test
    void testUnknownTemplateThrowsException() {
        System.out.println("\n=== TEST: Unknown Template Throws Exception ===");
        System.out.println("Attempting to get non-existent template 'non_existent'...");
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            GroupTemplateRegistry.getTemplate("non_existent");
        });
        
        System.out.println("Exception caught: " + exception.getMessage());
        assertTrue(exception.getMessage().contains("No template found"));
        
        System.out.println("✓ Exception handling working correctly");
    }
}
