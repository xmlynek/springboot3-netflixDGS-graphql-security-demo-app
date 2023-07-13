package com.example.graphqlcourse.component.problemz;

import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.*;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

@DgsComponent
public class SolutionDataResolver {

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.SolutionCreate
    )
    public SolutionResponse createSolution(
            @RequestHeader(name = "authToken") String authToken,
            @InputArgument(name = "solution") SolutionCreateInput solutionCreateInput) {
        return null;
    }

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.SolutionVote
    )
    public SolutionResponse voteSolution(
            @RequestHeader(name = "authToken") String authToken,
            @InputArgument(name = "vote") SolutionVoteInput solutionVoteInput) {
        return null;
    }

    @DgsData(
            parentType = DgsConstants.SUBSCRIPTION_TYPE,
            field = DgsConstants.SUBSCRIPTION.SolutionVoteChanged
    )
    public Flux<Solution> subscribeSolutionVoteChanged(@InputArgument("solutionId") String id) {
        return null;
    }
}
