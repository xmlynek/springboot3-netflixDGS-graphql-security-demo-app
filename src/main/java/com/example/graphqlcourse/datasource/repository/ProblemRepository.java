package com.example.graphqlcourse.datasource.repository;

import com.example.graphqlcourse.datasource.entity.Problem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProblemRepository extends CrudRepository<Problem, UUID> {

    List<Problem> findAllByOrderByCreationTimestampDesc();

}
