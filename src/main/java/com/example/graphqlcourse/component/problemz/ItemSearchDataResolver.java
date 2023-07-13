package com.example.graphqlcourse.component.problemz;

import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.SearchItemFilter;
import com.course.graphql.generated.types.SearchableItem;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class ItemSearchDataResolver {

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.ItemSearch
    )
    public List<SearchableItem> getItemSearch(@InputArgument(name = "filter") Optional<SearchItemFilter> filter) {
        return null;
    }
}
