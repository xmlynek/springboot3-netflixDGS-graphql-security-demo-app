package com.example.graphqlcourse.component.fake;

import com.course.graphql.generated.DgsConstants;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class FakeAdditionalOnRequestDataResolver {

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.AdditionalOnRequest
    )
    public String additionalOnRequest(
            @RequestHeader(name = "optionalHeader", required = false) String optionalHeader,
            @RequestHeader(name = "mandatoryHeader", required = true) String mandatoryHeader,
            @RequestParam(name = "optionalParam", required = false) String optionalParam,
            @RequestParam(name = "mandatoryParam", required = true) String mandatoryParam
    ) {
        List<String> headers = List.of(
                "Optional header:", StringUtils.defaultIfBlank(optionalHeader, StringUtils.EMPTY),
                "Mandatory header:", mandatoryHeader,
                "Optional param:", StringUtils.defaultIfBlank(optionalParam, StringUtils.EMPTY),
                "Mandatory param:", mandatoryParam
        );

        return String.join(" ", headers);
    }
}
