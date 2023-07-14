package com.example.graphqlcourse.service.query;

import com.example.graphqlcourse.datasource.entity.Problem;
import com.example.graphqlcourse.datasource.repository.ProblemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProblemQueryService {

    private final ProblemRepository problemRepository;

    public List<Problem> getProblemLatestList() {
        return problemRepository.findAllByOrderByCreationTimestampDesc();
    }

    public Problem getProblemDetail(UUID problemId) {
        return problemRepository.findById(problemId)
                .orElseThrow(
                        () -> new IllegalArgumentException(String.format("Problem with id %s not found", problemId))
                );
    }

    public List<Problem> findProblemsByKeyword(String keyword) {
        return problemRepository.findByKeyword("%" + keyword + "%");
    }
}
