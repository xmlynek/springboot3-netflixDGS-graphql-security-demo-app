package com.example.graphqlcourse.datasource.repository;

import com.example.graphqlcourse.datasource.entity.Solution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SolutionRepository extends CrudRepository<Solution, UUID> {
}
