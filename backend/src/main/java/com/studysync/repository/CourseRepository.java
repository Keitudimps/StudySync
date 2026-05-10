package com.studysync.repository;

import com.studysync.domain.Course;
import java.util.List;
import java.util.Optional;

/**
 * Entity-specific repository for Course.
 */
public interface CourseRepository extends Repository<Course, Long> {

    /** Find a course by its unique course code (e.g., "CS301"). */
    Optional<Course> findByCourseCode(String courseCode);

    /** Return true if a course with this code already exists. */
    boolean existsByCourseCode(String courseCode);

    /** Search courses whose code or name contains the keyword (case-insensitive). */
    List<Course> searchByKeyword(String keyword);
}
