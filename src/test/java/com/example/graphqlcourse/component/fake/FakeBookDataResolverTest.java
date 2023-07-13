package com.example.graphqlcourse.component.fake;

import com.course.graphql.generated.client.BooksByReleasedGraphQLQuery;
import com.course.graphql.generated.client.BooksByReleasedProjectionRoot;
import com.course.graphql.generated.client.BooksGraphQLQuery;
import com.course.graphql.generated.client.BooksProjectionRoot;
import com.course.graphql.generated.types.Author;
import com.course.graphql.generated.types.Book;
import com.course.graphql.generated.types.ReleaseHistoryInput;
import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import net.datafaker.Faker;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FakeBookDataResolverTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Autowired
    Faker faker;

    @Test
    void shouldGetAllBooks() {
        var query = new BooksGraphQLQuery.Builder().build();
        var projectionRoot = new BooksProjectionRoot().title()
                .author().name().originCountry()
                .getRoot().released().year()
                .getRoot().publisher();
        @Language("GraphQL") var queryRequest = new GraphQLQueryRequest(query, projectionRoot).serialize();

        System.out.println(queryRequest);

        List<String> titles = dgsQueryExecutor.executeAndExtractJsonPath(queryRequest, "data.books[*].title");
        assertNotNull(titles);
        assertFalse(titles.isEmpty());

        List<String> publishers = dgsQueryExecutor.executeAndExtractJsonPath(queryRequest, "data.books[*].publisher");
        assertNotNull(publishers);
        assertFalse(publishers.isEmpty());

        List<Author> authors = dgsQueryExecutor.executeAndExtractJsonPathAsObject(queryRequest,
                "data.books[*].author",
                new TypeRef<>() {}
        );
        assertNotNull(authors);
        assertFalse(authors.isEmpty());

        List<Integer> releaseYears = dgsQueryExecutor.executeAndExtractJsonPathAsObject(queryRequest,
                "data.books[*].released.year", new TypeRef<List<Integer>>() {}
        );
        assertNotNull(releaseYears);
        assertFalse(releaseYears.isEmpty());
    }

    @Test
    void getBooksByReleased() {
        int expectedYear = faker.number().numberBetween(2019, 2021);
        boolean expectedPrintedEdition = faker.bool().bool();

        var releaseHistoryInput = ReleaseHistoryInput.newBuilder()
                .year(expectedYear)
                .printedEdition(expectedPrintedEdition)
                .build();

        var query = BooksByReleasedGraphQLQuery.newRequest()
                .releasedInput(releaseHistoryInput)
                .build();
        var projectionRoot = new BooksByReleasedProjectionRoot()
                .released().year()
                .printedEdition()
                .getRoot().author().name().originCountry();
        @Language("GraphQL") var graphQLQueryRequest = new GraphQLQueryRequest(query, projectionRoot).serialize();

        System.out.println(graphQLQueryRequest);

        List<Integer> releaseYears = dgsQueryExecutor.executeAndExtractJsonPath(graphQLQueryRequest,
                "data.booksByReleased[*].released.year");
        Set<Integer> uniqueReleaseYears = new HashSet<>(releaseYears);

        assertNotNull(uniqueReleaseYears);
        assertTrue(uniqueReleaseYears.size() <= 1);

        if (!uniqueReleaseYears.isEmpty()) {
            assertTrue(uniqueReleaseYears.contains(expectedYear));
        }

        List<Boolean> releasePrintedEditions = dgsQueryExecutor.executeAndExtractJsonPath(graphQLQueryRequest,
                "data.booksByReleased[*].released.printedEdition");
        Set<Boolean> uniqueReleasePrintedEditions = new HashSet<>(releasePrintedEditions);

        assertNotNull(uniqueReleasePrintedEditions);
        assertTrue(uniqueReleasePrintedEditions.size() <= 1);

        if (!uniqueReleasePrintedEditions.isEmpty()) {
            assertTrue(uniqueReleasePrintedEditions.contains(expectedPrintedEdition));
        }

        List<Author> authors = dgsQueryExecutor.executeAndExtractJsonPathAsObject(graphQLQueryRequest,
                "data.booksByReleased[*].author",
                new TypeRef<>() {}
        );
        assertNotNull(authors);
        assertFalse(authors.isEmpty());
    }
}