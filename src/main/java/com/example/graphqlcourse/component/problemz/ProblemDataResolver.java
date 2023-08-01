package com.example.graphqlcourse.component.problemz;

import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.Problem;
import com.course.graphql.generated.types.ProblemCreateInput;
import com.course.graphql.generated.types.ProblemResponse;
import com.example.graphqlcourse.exception.AuthenticationException;
import com.example.graphqlcourse.service.command.ProblemCommandService;
import com.example.graphqlcourse.service.query.ProblemQueryService;
import com.example.graphqlcourse.service.query.UserQueryService;
import com.example.graphqlcourse.util.mapper.ProblemMapper;
import com.example.graphqlcourse.util.mapper.ProblemMapperImpl;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DgsComponent
@AllArgsConstructor
public class ProblemDataResolver {

    private final ProblemQueryService problemQueryService;
    private final ProblemCommandService problemCommandService;
    private final UserQueryService userQueryService;

    private final ProblemMapper problemMapper;


    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.ProblemLatestList
    )
    public List<Problem> getProblemLatestList() {
        return problemQueryService.getProblemLatestList().stream()
                .map(problemMapper::problemToProblemQL)
                .collect(Collectors.toList());
    }

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.ProblemDetail
    )
    public Problem getProblemDetail(@InputArgument(name = "id") String id) {
        return problemMapper.problemToProblemQL(problemQueryService.getProblemDetail(UUID.fromString(id)));
    }

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.ProblemCreate
    )
    public ProblemResponse createProblem(
            @RequestHeader(name = "authToken") String authToken,
            @InputArgument(name = "problem") ProblemCreateInput createInput) {
        var user = userQueryService.findUserByAuthToken(authToken).orElseThrow(AuthenticationException::new);

        var problem = problemCommandService.createProblem(problemMapper.problemCreateInputToProblem(createInput, user));
        return ProblemResponse.newBuilder()
                .problem(problemMapper.problemToProblemQL(problem))
                .build();
    }

    @DgsData(
            parentType = DgsConstants.SUBSCRIPTION_TYPE,
            field = DgsConstants.SUBSCRIPTION.ProblemAdded
    )
    public Flux<Problem> subscribeProblemAdded() {
        return problemCommandService.problemFlux().map(problemMapper::problemToProblemQL);
    }
}
