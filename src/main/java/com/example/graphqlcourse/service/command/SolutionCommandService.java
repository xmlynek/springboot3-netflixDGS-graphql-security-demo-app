package com.example.graphqlcourse.service.command;

import com.example.graphqlcourse.datasource.entity.Solution;
import com.example.graphqlcourse.datasource.repository.SolutionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SolutionCommandService {

    private final SolutionRepository solutionRepository;

    public Solution createSolution(Solution solution) {
        return solutionRepository.save(solution);
    }

    public Optional<Solution> voteBad(UUID solutionId) {
        solutionRepository.addVoteBadCount(solutionId);
        return solutionRepository.findById(solutionId);
    }

    public Optional<Solution> voteGood(UUID solutionId) {
        solutionRepository.addVoteGoodCount(solutionId);
        return solutionRepository.findById(solutionId);
    }
}
