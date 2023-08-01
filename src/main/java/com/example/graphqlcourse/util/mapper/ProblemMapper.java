package com.example.graphqlcourse.util.mapper;

import com.course.graphql.generated.types.Problem;
import com.course.graphql.generated.types.ProblemCreateInput;
import com.example.graphqlcourse.datasource.entity.Solution;
import com.example.graphqlcourse.datasource.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        uses = {SolutionMapper.class, UserMapper.class, HelperFieldMapper.class}
)
public interface ProblemMapper {

    @Mapping(target = "prettyCreateDateTime", source = "creationTimestamp")
    @Mapping(target = "createDateTime", source = "creationTimestamp")
    @Mapping(target = "solutionCount", expression = "java(problem.getSolutions().size())")
    @Mapping(target = "author", source = "createdBy")
    Problem problemToProblemQL(com.example.graphqlcourse.datasource.entity.Problem problem);

    default List<String> mapTagsToList(String tags) {
        return List.of(tags.split(","));
    }

    default String mapListOfTagsToTagsString(List<String> tags) {
        return String.join(",", tags);
    }

    @Mapping(target = "creationTimestamp", ignore = true)
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "createdBy", source = "author")
    @Mapping(target = "tags", source = "createInput.tag")
    @Mapping(target = "solutions", source = "createInput")
    com.example.graphqlcourse.datasource.entity.Problem problemCreateInputToProblem(ProblemCreateInput createInput, User author);

    default List<Solution> mapSolutions(ProblemCreateInput value) {
        return new ArrayList<>();
    }

}
