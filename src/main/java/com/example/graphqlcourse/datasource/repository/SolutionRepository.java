package com.example.graphqlcourse.datasource.repository;

import com.example.graphqlcourse.datasource.entity.Solution;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SolutionRepository extends CrudRepository<Solution, UUID> {

    @Query(
            value = "SELECT s FROM Solution s " +
                    "WHERE UPPER(s.content) LIKE UPPER(:keyword) " +
                    "ORDER BY s.creationTimestamp DESC"
    )
    public List<Solution> findByKeyword(@Param("keyword") String keyword);
}
