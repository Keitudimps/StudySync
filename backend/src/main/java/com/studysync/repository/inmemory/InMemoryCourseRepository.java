package com.studysync.repository.inmemory;

import com.studysync.domain.Course;
import com.studysync.repository.CourseRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryCourseRepository implements CourseRepository {

    private final Map<Long, Course> storage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public void save(Course course) {
        if (course.getCourseId() == null) {
            course.setCourseId(idCounter.getAndIncrement());
        }
        storage.put(course.getCourseId(), course);
    }

    @Override
    public Optional<Course> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Course> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }

    @Override
    public long count() {
        return storage.size();
    }

    @Override
    public Optional<Course> findByCourseCode(String courseCode) {
        return storage.values().stream()
            .filter(c -> c.getCourseCode().equalsIgnoreCase(courseCode))
            .findFirst();
    }

    @Override
    public boolean existsByCourseCode(String courseCode) {
        return storage.values().stream()
            .anyMatch(c -> c.getCourseCode().equalsIgnoreCase(courseCode));
    }

    @Override
    public List<Course> searchByKeyword(String keyword) {
        String lower = keyword.toLowerCase();
        return storage.values().stream()
            .filter(c -> c.getCourseCode().toLowerCase().contains(lower)
                      || c.getCourseName().toLowerCase().contains(lower))
            .collect(Collectors.toList());
    }
}
