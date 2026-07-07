package com.sparkCoder.raktbhet.repository;

import com.sparkCoder.raktbhet.entity.AllDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface AllDataRepository extends JpaRepository<AllDataEntity, Integer> {
    
    /**
     * Find a single user by username (should return at most one result)
     * @param username the username to search for
     * @return Optional containing the user if found, empty if not
     */
    Optional<AllDataEntity> findByUserName(String username);
    
    /**
     * Find all users with the given username (used for duplicate detection)
     * @param username the username to search for
     * @return List of all matching users
     */
    List<AllDataEntity> findAllByUserName(String username);
    
    /**
     * Check if a username already exists
     * @param username the username to check
     * @return true if username exists, false otherwise
     */
    boolean existsByUserName(String username);
}
