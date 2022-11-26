package bdm.soap.repository;

import bdm.soap.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO roles VALUES " +
            "(1, 'Team Lead'),\n" +
            "(2, 'Admin'),\n" +
            "(3, 'Developer'),\n" +
            "(4, 'Project Manager'),\n" +
            "(5, 'QA engineer'), \n" +
            "(10, 'Cleaner')")
    void initializeDatabase();
}
