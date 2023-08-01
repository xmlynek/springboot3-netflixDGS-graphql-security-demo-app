package com.example.graphqlcourse.datasource.repository;

import com.example.graphqlcourse.datasource.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findByUsernameIgnoreCase(String username);

    @Query(value = "SELECT u FROM User u " +
            "INNER JOIN UserToken ut " +
            "ON ut.userId = u.id " +
            "WHERE ut.authToken = :authToken " +
            "AND ut.expiryTimestamp > CURRENT TIMESTAMP ")
    Optional<User> findUserByAuthToken(@Param("authToken") String authToken);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u " +
            "SET u.isActive = :isActive " +
            "WHERE u.id = :userId")
    void activateUser(@Param("userId") UUID userId, @Param("isActive") Boolean isActive);
}
