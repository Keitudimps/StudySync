package com.studysync.repository;

import com.studysync.domain.Privacy;
import com.studysync.domain.StudyGroup;
import com.studysync.repository.inmemory.InMemoryStudyGroupRepository;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InMemoryStudyGroupRepository — CRUD and domain queries")
class InMemoryStudyGroupRepositoryTest {

    private InMemoryStudyGroupRepository repo;

    @BeforeEach
    void setUp() { repo = new InMemoryStudyGroupRepository(); }

    @Test
    @DisplayName("save() assigns ID and findById() retrieves the group")
    void testSaveAndFind() {
        System.out.println("\n--- TEST: save() + findById() ---");
        StudyGroup group = new StudyGroup("CS Study", "Group desc", Privacy.PUBLIC, 10, 1L, 101L);
        repo.save(group);

        assertNotNull(group.getGroupId(), "ID assigned after save");
        assertTrue(repo.findById(group.getGroupId()).isPresent(), "findById must find saved group");
        assertEquals("CS Study", repo.findById(group.getGroupId()).get().getName());
        System.out.println("  Saved group ID: " + group.getGroupId());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("deleteById() removes the group from storage")
    void testDeleteById() {
        System.out.println("\n--- TEST: deleteById() ---");
        StudyGroup group = new StudyGroup("Delete Me", "", Privacy.PUBLIC, 5, 1L, 101L);
        repo.save(group);
        Long id = group.getGroupId();

        repo.deleteById(id);

        assertFalse(repo.existsById(id), "Group must not exist after delete");
        System.out.println("  Group deleted successfully");
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("findPublicGroups() returns only PUBLIC groups")
    void testFindPublicGroups() {
        System.out.println("\n--- TEST: findPublicGroups() ---");
        repo.save(new StudyGroup("Public Group",  "", Privacy.PUBLIC,  10, 1L, 101L));
        repo.save(new StudyGroup("Private Group", "", Privacy.PRIVATE, 5,  2L, 102L));

        List<StudyGroup> publicGroups = repo.findPublicGroups();
        assertEquals(1, publicGroups.size(), "Only 1 PUBLIC group should be returned");
        assertEquals("Public Group", publicGroups.get(0).getName());
        System.out.println("  Public groups found: " + publicGroups.size());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("findByCourseId() returns only groups linked to that course")
    void testFindByCourseId() {
        System.out.println("\n--- TEST: findByCourseId() ---");
        repo.save(new StudyGroup("CS Group 1", "", Privacy.PUBLIC, 10, 1L, 101L));
        repo.save(new StudyGroup("CS Group 2", "", Privacy.PUBLIC, 10, 2L, 101L));
        repo.save(new StudyGroup("Math Group", "", Privacy.PUBLIC, 10, 3L, 202L));

        List<StudyGroup> csGroups = repo.findByCourseId(101L);
        assertEquals(2, csGroups.size(), "Two groups linked to course 101");

        List<StudyGroup> mathGroups = repo.findByCourseId(202L);
        assertEquals(1, mathGroups.size(), "One group linked to course 202");

        System.out.println("  Course 101 groups: " + csGroups.size());
        System.out.println("  Course 202 groups: " + mathGroups.size());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("searchByName() is case-insensitive and matches partial names")
    void testSearchByName() {
        System.out.println("\n--- TEST: searchByName() ---");
        repo.save(new StudyGroup("Advanced Algorithms", "", Privacy.PUBLIC, 10, 1L, 101L));
        repo.save(new StudyGroup("Data Structures",     "", Privacy.PUBLIC, 10, 2L, 101L));

        List<StudyGroup> results = repo.searchByName("algo");
        assertEquals(1, results.size(), "Case-insensitive partial match should return 1 result");
        assertEquals("Advanced Algorithms", results.get(0).getName());

        System.out.println("  Search 'algo' found: " + results.size() + " group(s)");
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("findByCreatorId() returns only groups by that creator")
    void testFindByCreatorId() {
        System.out.println("\n--- TEST: findByCreatorId() ---");
        repo.save(new StudyGroup("Creator1 Group A", "", Privacy.PUBLIC, 10, 1L, 101L));
        repo.save(new StudyGroup("Creator1 Group B", "", Privacy.PUBLIC, 10, 1L, 102L));
        repo.save(new StudyGroup("Creator2 Group",   "", Privacy.PUBLIC, 10, 2L, 101L));

        List<StudyGroup> creator1Groups = repo.findByCreatorId(1L);
        assertEquals(2, creator1Groups.size(), "Creator 1 owns 2 groups");

        List<StudyGroup> creator2Groups = repo.findByCreatorId(2L);
        assertEquals(1, creator2Groups.size(), "Creator 2 owns 1 group");

        System.out.println("  Creator 1 groups: " + creator1Groups.size());
        System.out.println("  PASS");
    }
}
