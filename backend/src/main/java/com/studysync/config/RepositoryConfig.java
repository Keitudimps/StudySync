package com.studysync.config;

import com.studysync.repository.CourseRepository;
import com.studysync.repository.MembershipRepository;
import com.studysync.repository.StudyGroupRepository;
import com.studysync.repository.StudySessionRepository;
import com.studysync.repository.UserRepository;
import com.studysync.repository.inmemory.InMemoryCourseRepository;
import com.studysync.repository.inmemory.InMemoryMembershipRepository;
import com.studysync.repository.inmemory.InMemoryStudyGroupRepository;
import com.studysync.repository.inmemory.InMemoryStudySessionRepository;
import com.studysync.repository.inmemory.InMemoryUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers repository implementations for the service layer.
 * Assignment 12 uses in-memory repositories from Assignment 11 so the REST API
 * can run without requiring a database setup.
 */
@Configuration
public class RepositoryConfig {

    @Bean
    public UserRepository userRepository() {
        return new InMemoryUserRepository();
    }

    @Bean
    public StudyGroupRepository studyGroupRepository() {
        return new InMemoryStudyGroupRepository();
    }

    @Bean
    public StudySessionRepository studySessionRepository() {
        return new InMemoryStudySessionRepository();
    }

    @Bean
    public CourseRepository courseRepository() {
        return new InMemoryCourseRepository();
    }

    @Bean
    public MembershipRepository membershipRepository() {
        return new InMemoryMembershipRepository();
    }
}
