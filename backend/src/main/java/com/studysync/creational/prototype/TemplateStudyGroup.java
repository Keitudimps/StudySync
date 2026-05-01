package com.studysync.creational.prototype;

import java.util.HashMap;
import java.util.Map;

public class TemplateStudyGroup implements GroupPrototype {
    private String templateName;
    private String defaultDescription;
    private String suggestedCourse;
    private int recommendedCapacity;
    private boolean isPrivate;
    private Map<String, String> defaultTags;
    
    public TemplateStudyGroup(String templateName, String suggestedCourse, int recommendedCapacity) {
        this.templateName = templateName;
        this.suggestedCourse = suggestedCourse;
        this.recommendedCapacity = recommendedCapacity;
        this.defaultDescription = "Study group for " + suggestedCourse;
        this.isPrivate = false;
        this.defaultTags = new HashMap<>();
        defaultTags.put("type", "study");
        defaultTags.put("level", "all");
    }
    
    private TemplateStudyGroup(TemplateStudyGroup source) {
        this.templateName = source.templateName;
        this.defaultDescription = source.defaultDescription;
        this.suggestedCourse = source.suggestedCourse;
        this.recommendedCapacity = source.recommendedCapacity;
        this.isPrivate = source.isPrivate;
        this.defaultTags = new HashMap<>(source.defaultTags);
    }
    
    @Override
    public GroupPrototype clone() {
        return new TemplateStudyGroup(this);
    }
    
    @Override
    public void customize(String name, String description) {
        this.templateName = name;
        if (description != null) {
            this.defaultDescription = description;
        }
    }
    
    public void setPrivate(boolean isPrivate) { this.isPrivate = isPrivate; }
    public void addTag(String key, String value) { defaultTags.put(key, value); }
    
    public String getTemplateName() { return templateName; }
    public String getDefaultDescription() { return defaultDescription; }
    public String getSuggestedCourse() { return suggestedCourse; }
    public int getRecommendedCapacity() { return recommendedCapacity; }
    public boolean isPrivate() { return isPrivate; }
    public Map<String, String> getDefaultTags() { return new HashMap<>(defaultTags); }
    
    @Override
    public String toString() {
        return "TemplateStudyGroup{templateName='" + templateName + "', course='" + suggestedCourse + "'}";
    }
}
