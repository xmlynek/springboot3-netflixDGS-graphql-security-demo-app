package com.example.graphqlcourse.component.fake;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FakeHelloDataResolverTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    void allHellos() {
        @Language("GraphQL") String query = """
            {
                allHellos {
                    text
                    randomNumber
                }
            }
        """;

        List<String> texts = dgsQueryExecutor.executeAndExtractJsonPath(query, "data.allHellos[*].text");
        List<Integer> randomNumbers = dgsQueryExecutor.executeAndExtractJsonPath(query, "data.allHellos[*].randomNumber");

        assertNotNull(texts);
        assertFalse(texts.isEmpty());

        assertNotNull(randomNumbers);
        assertFalse(randomNumbers.isEmpty());
    }

    @Test
    void oneHello() {
        @Language("GraphQL") String graphqlQuery = """
                {
                    oneHello {
                        text
                        randomNumber
                    }
                }
                """;
        String text = dgsQueryExecutor.executeAndExtractJsonPath(graphqlQuery, "data.oneHello.text");
        Integer randomNumber = dgsQueryExecutor.executeAndExtractJsonPath(graphqlQuery, "data.oneHello.randomNumber");

        assertThat(text).isNotBlank();
        assertNotNull(randomNumber);
    }
}