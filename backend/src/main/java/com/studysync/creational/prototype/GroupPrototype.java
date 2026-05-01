package com.studysync.creational.prototype;

public interface GroupPrototype extends Cloneable {
    GroupPrototype clone();
    void customize(String name, String description);
}
