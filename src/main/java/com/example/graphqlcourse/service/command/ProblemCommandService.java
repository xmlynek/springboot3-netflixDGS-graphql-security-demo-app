package com.example.graphqlcourse.service.command;

import com.example.graphqlcourse.datasource.entity.Problem;
import com.example.graphqlcourse.datasource.repository.ProblemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProblemCommandService {

    private final ProblemRepository problemRepository;

    public Problem createProblem(Problem problem) {
        return problemRepository.save(problem);
    }
}
