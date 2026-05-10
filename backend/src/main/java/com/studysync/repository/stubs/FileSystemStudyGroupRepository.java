package com.studysync.repository.stubs;

import com.studysync.domain.Privacy;
import com.studysync.domain.StudyGroup;
import com.studysync.repository.StudyGroupRepository;
import java.util.*;

/**
 * STUB — Future JSON filesystem implementation of StudyGroupRepository.
 *
 * When implemented, groups will be serialized to ./data/groups.json.
 * Each group will be stored as a JSON object with all fields.
 * On load, all groups will be deserialized back into StudyGroup objects.
 */
public class FileSystemStudyGroupRepository implements StudyGroupRepository {
    private final String filePath;
    public FileSystemStudyGroupRepository() { this("./data/groups.json"); }
    public FileSystemStudyGroupRepository(String filePath) { this.filePath = filePath; }

    @Override public void save(StudyGroup g) { throw new UnsupportedOperationException("Not yet implemented. Will write to " + filePath); }
    @Override public Optional<StudyGroup> findById(Long id) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public List<StudyGroup> findAll() { throw new UnsupportedOperationException("Not implemented"); }
    @Override public void deleteById(Long id) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public boolean existsById(Long id) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public long count() { throw new UnsupportedOperationException("Not implemented"); }
    @Override public List<StudyGroup> findByCourseId(Long courseId) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public List<StudyGroup> findPublicGroups() { throw new UnsupportedOperationException("Not implemented"); }
    @Override public List<StudyGroup> findByCreatorId(Long creatorId) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public List<StudyGroup> searchByName(String keyword) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public List<StudyGroup> findGroupsWithAvailableSpace() { throw new UnsupportedOperationException("Not implemented"); }
}
