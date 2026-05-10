package com.studysync.repository;

import com.studysync.domain.Privacy;
import com.studysync.domain.StudyGroup;
import java.util.List;

/**
 * Entity-specific repository for StudyGroup.
 */
public interface StudyGroupRepository extends Repository<StudyGroup, Long> {

    /** Return all groups linked to the given course ID. */
    List<StudyGroup> findByCourseId(Long courseId);

    /** Return all PUBLIC groups (visible in search results). */
    List<StudyGroup> findPublicGroups();

    /** Return all groups created by the given user ID. */
    List<StudyGroup> findByCreatorId(Long creatorId);

    /** Return all groups matching the name keyword (case-insensitive). */
    List<StudyGroup> searchByName(String keyword);

    /** Return all groups that still have available capacity. */
    List<StudyGroup> findGroupsWithAvailableSpace();
}
