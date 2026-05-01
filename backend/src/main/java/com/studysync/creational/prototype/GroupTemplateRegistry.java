package com.studysync.creational.prototype;

import java.util.HashMap;
import java.util.Map;

public class GroupTemplateRegistry {
    private static Map<String, GroupPrototype> templates = new HashMap<>();
    
    static {
        templates.put("exam_prep", new TemplateStudyGroup("Exam Prep Group", "CS301", 8));
        templates.put("assignment", new TemplateStudyGroup("Assignment Group", "CS201", 5));
        templates.put("weekly_review", new TemplateStudyGroup("Weekly Review", "CS101", 12));
    }
    
    public static void addTemplate(String key, GroupPrototype template) {
        templates.put(key, template);
    }
    
    public static GroupPrototype getTemplate(String key) {
        GroupPrototype template = templates.get(key);
        if (template == null) {
            throw new IllegalArgumentException("No template found for key: " + key);
        }
        return template.clone();
    }
}
