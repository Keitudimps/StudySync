package com.studysync.repository.inmemory;

import com.studysync.domain.Membership;
import com.studysync.domain.MembershipStatus;
import com.studysync.repository.MembershipRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryMembershipRepository implements MembershipRepository {

    private final Map<Long, Membership> storage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public void save(Membership membership) {
        if (membership.getMembershipId() == null) {
            membership.setMembershipId(idCounter.getAndIncrement());
        }
        storage.put(membership.getMembershipId(), membership);
    }

    @Override
    public Optional<Membership> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Membership> findAll() {
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
    public List<Membership> findByUserId(Long userId) {
        return storage.values().stream()
            .filter(m -> userId.equals(m.getUserId()))
            .collect(Collectors.toList());
    }

    @Override
    public List<Membership> findByGroupId(Long groupId) {
        return storage.values().stream()
            .filter(m -> groupId.equals(m.getGroupId()))
            .collect(Collectors.toList());
    }

    @Override
    public List<Membership> findByStatus(MembershipStatus status) {
        return storage.values().stream()
            .filter(m -> m.getStatus() == status)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Membership> findByUserIdAndGroupId(Long userId, Long groupId) {
        return storage.values().stream()
            .filter(m -> userId.equals(m.getUserId()) && groupId.equals(m.getGroupId()))
            .findFirst();
    }

    @Override
    public long countActiveByUserId(Long userId) {
        return storage.values().stream()
            .filter(m -> userId.equals(m.getUserId()) && m.getStatus() == MembershipStatus.ACTIVE)
            .count();
    }
}
