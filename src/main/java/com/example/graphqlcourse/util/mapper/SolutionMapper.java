package com.example.graphqlcourse.util.mapper;

import com.course.graphql.generated.types.Solution;
import com.course.graphql.generated.types.SolutionCreateInput;
import com.example.graphqlcourse.datasource.entity.Problem;
import com.example.graphqlcourse.datasource.entity.User;
import com.example.graphqlcourse.datasource.repository.HelperFieldMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(
        uses = {ProblemMapper.class, UserMapper.class, HelperFieldMapper.class}
)
public interface SolutionMapper {

    @Mapping(target = "prettyCreateDateTime", source = "creationTimestamp")
    @Mapping(target = "createDateTime", source = "creationTimestamp")
    @Mapping(target = "voteAsGoodCount", source = "voteGoodCount")
    @Mapping(target = "voteAsBadCount", source = "voteBadCount")
    @Mapping(target = "author", source = "createdBy")
    Solution solutionToSolutionQL(com.example.graphqlcourse.datasource.entity.Solution solution);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "creationTimestamp", ignore = true)
    @Mapping(target = "content", expression = "java(createInput.getContent())")
    @Mapping(target = "problem", source = "problem")
    @Mapping(target = "createdBy", source = "author")
    @Mapping(target = "voteGoodCount", ignore = true)
    @Mapping(target = "voteBadCount", ignore = true)
    com.example.graphqlcourse.datasource.entity.Solution solutionCreateInputToSolution(
            SolutionCreateInput createInput, User author, Problem problem);
}
