package com.studysync.repository.inmemory;

import com.studysync.domain.Privacy;
import com.studysync.domain.StudyGroup;
import com.studysync.repository.StudyGroupRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryStudyGroupRepository implements StudyGroupRepository {

    private final Map<Long, StudyGroup> storage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public void save(StudyGroup group) {
        if (group.getGroupId() == null) {
            group.setGroupId(idCounter.getAndIncrement());
        }
        storage.put(group.getGroupId(), group);
    }

    @Override
    public Optional<StudyGroup> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<StudyGroup> findAll() {
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
    public List<StudyGroup> findByCourseId(Long courseId) {
        return storage.values().stream()
            .filter(g -> courseId.equals(g.getCourseId()))
            .collect(Collectors.toList());
    }

    @Override
    public List<StudyGroup> findPublicGroups() {
        return storage.values().stream()
            .filter(g -> g.getPrivacy() == Privacy.PUBLIC)
            .collect(Collectors.toList());
    }

    @Override
    public List<StudyGroup> findByCreatorId(Long creatorId) {
        return storage.values().stream()
            .filter(g -> creatorId.equals(g.getCreatorId()))
            .collect(Collectors.toList());
    }

    @Override
    public List<StudyGroup> searchByName(String keyword) {
        String lower = keyword.toLowerCase();
        return storage.values().stream()
            .filter(g -> g.getName().toLowerCase().contains(lower))
            .collect(Collectors.toList());
    }

    @Override
    public List<StudyGroup> findGroupsWithAvailableSpace() {
        return storage.values().stream()
            .filter(g -> !g.isFull())
            .collect(Collectors.toList());
    }
}
