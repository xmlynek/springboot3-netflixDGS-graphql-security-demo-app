package com.example.graphqlcourse.component.problemz;

import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.Problem;
import com.course.graphql.generated.types.SearchItemFilter;
import com.course.graphql.generated.types.SearchableItem;
import com.course.graphql.generated.types.Solution;
import com.example.graphqlcourse.service.query.ProblemQueryService;
import com.example.graphqlcourse.service.query.SolutionQueryService;
import com.example.graphqlcourse.util.mapper.ProblemMapper;
import com.example.graphqlcourse.util.mapper.SolutionMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@DgsComponent
@AllArgsConstructor
public class ItemSearchDataResolver {

    private final ProblemQueryService problemQueryService;
    private final SolutionQueryService solutionQueryService;

    private final ProblemMapper problemMapper;
    private final SolutionMapper solutionMapper;

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.ItemSearch
    )
    public List<SearchableItem> getItemSearch(@InputArgument(name = "filter") Optional<SearchItemFilter> filter) {
        String keyword = filter.map(SearchItemFilter::getKeyword).orElse("");

        List<Problem> problemListByKeyword = problemQueryService.findProblemsByKeyword(keyword).stream()
                .map(problemMapper::problemToProblemQL)
                .toList();
        List<SearchableItem> resultList = new ArrayList<>(problemListByKeyword);

        List<Solution> solutionListByKeyword = solutionQueryService.findSolutionsByKeyword(keyword).stream()
                .map(solutionMapper::solutionToSolutionQL)
                .toList();
        resultList.addAll(solutionListByKeyword);
        resultList.sort(Comparator.comparing(SearchableItem::getCreateDateTime).reversed());

        return resultList;
    }
}
