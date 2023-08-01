package com.example.graphqlcourse.service.command;

import com.example.graphqlcourse.datasource.entity.Problem;
import com.example.graphqlcourse.datasource.repository.ProblemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
@AllArgsConstructor
public class ProblemCommandService {

    private final Sinks.Many<Problem> problemSink = Sinks.many().multicast().onBackpressureBuffer();

    private final ProblemRepository problemRepository;

    public Problem createProblem(Problem problem) {
        var created = problemRepository.save(problem);

        problemSink.tryEmitNext(created);

        return created;
    }

    public Flux<Problem> problemFlux() {
        return problemSink.asFlux();
    }
}
