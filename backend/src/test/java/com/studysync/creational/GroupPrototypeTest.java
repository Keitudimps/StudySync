package com.studysync.creational;

import com.studysync.creational.prototype.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GroupPrototypeTest {
    
    @Test
    void testTemplateCloning() {
        TemplateStudyGroup original = new TemplateStudyGroup("Exam Prep", "CS301", 8);
        original.setPrivate(true);
        original.addTag("priority", "high");
        
        TemplateStudyGroup cloned = (TemplateStudyGroup) original.clone();
        
        assertNotSame(original, cloned);
        assertEquals(original.getTemplateName(), cloned.getTemplateName());
        assertEquals(original.getSuggestedCourse(), cloned.getSuggestedCourse());
        assertEquals(original.getRecommendedCapacity(), cloned.getRecommendedCapacity());
        assertEquals(original.isPrivate(), cloned.isPrivate());
    }
    
    @Test
    void testRegistryReturnsClonedTemplates() {
        GroupPrototype template1 = GroupTemplateRegistry.getTemplate("exam_prep");
        GroupPrototype template2 = GroupTemplateRegistry.getTemplate("exam_prep");
        
        assertNotSame(template1, template2);
    }
    
    @Test
    void testCustomizeClone() {
        GroupPrototype template = GroupTemplateRegistry.getTemplate("assignment");
        template.customize("Custom Assignment Group", "Custom description");
        
        GroupPrototype anotherTemplate = GroupTemplateRegistry.getTemplate("assignment");
        assertNotEquals(template.toString(), anotherTemplate.toString());
    }
    
    @Test
    void testUnknownTemplateThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            GroupTemplateRegistry.getTemplate("non_existent");
        });
        assertTrue(exception.getMessage().contains("No template found"));
    }
}
