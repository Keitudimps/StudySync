package com.studysync.repository.inmemory;

import com.studysync.domain.StudySession;
import com.studysync.repository.StudySessionRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryStudySessionRepository implements StudySessionRepository {

    private final Map<Long, StudySession> storage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public void save(StudySession session) {
        if (session.getSessionId() == null) {
            session.setSessionId(idCounter.getAndIncrement());
        }
        storage.put(session.getSessionId(), session);
    }

    @Override
    public Optional<StudySession> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<StudySession> findAll() {
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
    public List<StudySession> findByGroupId(Long groupId) {
        return storage.values().stream()
            .filter(s -> groupId.equals(s.getGroupId()))
            .collect(Collectors.toList());
    }

    @Override
    public List<StudySession> findUpcomingSessions() {
        LocalDateTime now = LocalDateTime.now();
        return storage.values().stream()
            .filter(s -> s.getScheduledAt().isAfter(now))
            .sorted(Comparator.comparing(StudySession::getScheduledAt))
            .collect(Collectors.toList());
    }

    @Override
    public List<StudySession> findPastSessions() {
        LocalDateTime now = LocalDateTime.now();
        return storage.values().stream()
            .filter(s -> s.getScheduledAt().isBefore(now))
            .sorted(Comparator.comparing(StudySession::getScheduledAt).reversed())
            .collect(Collectors.toList());
    }

    @Override
    public List<StudySession> findByCreatedBy(Long userId) {
        return storage.values().stream()
            .filter(s -> userId.equals(s.getCreatedBy()))
            .collect(Collectors.toList());
    }
}
