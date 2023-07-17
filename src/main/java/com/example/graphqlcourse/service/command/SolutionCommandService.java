package com.example.graphqlcourse.service.command;

import com.example.graphqlcourse.datasource.entity.Solution;
import com.example.graphqlcourse.datasource.repository.SolutionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SolutionCommandService {

    private final SolutionRepository solutionRepository;
    private final Sinks.Many<Solution> solutionSinks = Sinks.many().multicast().onBackpressureBuffer();

    public Solution createSolution(Solution solution) {
        return solutionRepository.save(solution);
    }

    public Optional<Solution> voteBad(UUID solutionId) {
        solutionRepository.addVoteBadCount(solutionId);
        var updated = solutionRepository.findById(solutionId);

        updated.ifPresent(solutionSinks::tryEmitNext);

        return updated;
    }

    public Optional<Solution> voteGood(UUID solutionId) {
        solutionRepository.addVoteGoodCount(solutionId);
        var updated = solutionRepository.findById(solutionId);

        updated.ifPresent(solutionSinks::tryEmitNext);

        return updated;
    }

    public Flux<Solution> solutionFlux() {
        return solutionSinks.asFlux();
    }
}
