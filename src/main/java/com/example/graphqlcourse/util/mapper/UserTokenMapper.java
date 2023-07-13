package com.example.graphqlcourse.util.mapper;

import com.course.graphql.generated.types.UserAuthToken;
import com.example.graphqlcourse.datasource.entity.UserToken;
import com.example.graphqlcourse.datasource.repository.HelperFieldMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZoneOffset;

@Mapper(uses = {HelperFieldMapper.class})
public interface UserTokenMapper {

    ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(7);

    @Mapping(target = "expiryTime", source = "expiryTimestamp")
    UserAuthToken userTokenToUserAuthTokenQL(UserToken userToken);
}
