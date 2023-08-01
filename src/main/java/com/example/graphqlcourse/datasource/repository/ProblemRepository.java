package com.example.graphqlcourse.datasource.repository;

import com.example.graphqlcourse.datasource.entity.Problem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProblemRepository extends CrudRepository<Problem, UUID> {

    List<Problem> findAllByOrderByCreationTimestampDesc();

    @Query(value = "SELECT p from Problem p " +
            "WHERE UPPER(p.content) LIKE UPPER(:keyword) " +
            "OR UPPER(p.title) LIKE UPPER(:keyword) " +
            "OR UPPER(p.tags) LIKE UPPER(:keyword) " +
            "ORDER BY p.creationTimestamp DESC ")
    List<Problem> findByKeyword(@Param("keyword") String keyword);

}
