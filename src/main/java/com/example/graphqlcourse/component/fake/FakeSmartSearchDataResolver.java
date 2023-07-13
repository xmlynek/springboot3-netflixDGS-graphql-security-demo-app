package com.example.graphqlcourse.component.fake;

import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.SmartSearchResult;
import com.example.graphqlcourse.datasource.fake.FakeBookDataSource;
import com.example.graphqlcourse.datasource.fake.FakeHelloDataSource;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DgsComponent
public class FakeSmartSearchDataResolver {

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.SmartSearch
    )
    public List<SmartSearchResult> getSmartSearch(@InputArgument(name = "keyword")Optional<String> keyword) {
        List<SmartSearchResult> smartSearchResultList = new ArrayList<>();

        if (keyword.isEmpty()) {
            smartSearchResultList.addAll(FakeHelloDataSource.HELLO_LIST);
            smartSearchResultList.addAll(FakeBookDataSource.BOOK_LIST);
        } else {
            String keywordString = keyword.get();
            FakeHelloDataSource.HELLO_LIST.stream()
                    .filter(hello -> StringUtils.containsIgnoreCase(hello.getText(), keywordString))
                    .forEach(smartSearchResultList::add);
            FakeBookDataSource.BOOK_LIST.stream()
                    .filter(book -> StringUtils.containsIgnoreCase(book.getTitle(), keywordString))
                    .forEach(smartSearchResultList::add);
        }

        return smartSearchResultList;
    }
}
