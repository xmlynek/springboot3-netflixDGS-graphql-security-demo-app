package com.example.graphqlcourse.service.query;

import com.example.graphqlcourse.datasource.entity.Solution;
import com.example.graphqlcourse.datasource.repository.SolutionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SolutionQueryService {

    private final SolutionRepository solutionRepository;

    public List<Solution> findSolutionsByKeyword(String keyword) {
        return solutionRepository.findByKeyword("%" + keyword + "%");
    }
}
