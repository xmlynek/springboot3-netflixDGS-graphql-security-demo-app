package com.example.graphqlcourse.component.problemz;

import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.*;
import com.example.graphqlcourse.exception.AuthenticationException;
import com.example.graphqlcourse.service.command.SolutionCommandService;
import com.example.graphqlcourse.service.query.ProblemQueryService;
import com.example.graphqlcourse.service.query.UserQueryService;
import com.example.graphqlcourse.util.mapper.SolutionMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.Optional;
import java.util.UUID;

@DgsComponent
@AllArgsConstructor
public class SolutionDataResolver {

    private final UserQueryService userQueryService;
    private final SolutionCommandService solutionCommandService;
    private final ProblemQueryService problemQueryService;

    private final SolutionMapper solutionMapper;

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.SolutionCreate
    )
    public SolutionResponse createSolution(
            @RequestHeader(name = "authToken") String authToken,
            @InputArgument(name = "solution") SolutionCreateInput solutionCreateInput) {
        var user = userQueryService.findUserByAuthToken(authToken).orElseThrow(AuthenticationException::new);
        UUID problemId = UUID.fromString(solutionCreateInput.getProblemId());
        var problem = problemQueryService.getProblemDetail(problemId);

        var solution = solutionMapper.solutionCreateInputToSolution(solutionCreateInput, user, problem);

        var createdSolution = solutionCommandService.createSolution(solution);

        return SolutionResponse.newBuilder()
                .solution(solutionMapper.solutionToSolutionQL(createdSolution))
                .build();
    }

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.SolutionVote
    )
    public SolutionResponse voteSolution(
            @RequestHeader(name = "authToken") String authToken,
            @InputArgument(name = "vote") SolutionVoteInput solutionVoteInput) {
        var user = userQueryService.findUserByAuthToken(authToken).orElseThrow(AuthenticationException::new);

        Optional<com.example.graphqlcourse.datasource.entity.Solution> updated;
        UUID solutionId = UUID.fromString(solutionVoteInput.getSolutionId());

        if (solutionVoteInput.getVoteAsGood()) {
            updated = solutionCommandService.voteGood(solutionId);
        } else {
            updated = solutionCommandService.voteBad(solutionId);
        }

        return SolutionResponse.newBuilder()
                .solution(solutionMapper.solutionToSolutionQL(updated.orElseThrow(DgsEntityNotFoundException::new)))
                .build();
    }

    @DgsData(
            parentType = DgsConstants.SUBSCRIPTION_TYPE,
            field = DgsConstants.SUBSCRIPTION.SolutionVoteChanged
    )
    public Flux<Solution> subscribeSolutionVoteChanged(@InputArgument("solutionId") String id) {
        return null;
    }
}
