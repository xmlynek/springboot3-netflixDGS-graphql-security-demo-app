package com.example.graphqlcourse.util.mapper;

import com.course.graphql.generated.types.User;
import com.course.graphql.generated.types.UserCreateInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper (
        uses = {HelperFieldMapper.class}
)
public interface UserMapper {

    @Mapping(target = "createDateTime", source = "creationTimestamp")
    @Mapping(target = "problems", ignore = true)
    User userToUserQL(com.example.graphqlcourse.datasource.entity.User user);

    @Mapping(target = "hashedPassword", expression = "java(com.example.graphqlcourse.util.HashUtils.hashBcrypt(userCreateInput.getPassword()))")
    @Mapping(target = "creationTimestamp", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    com.example.graphqlcourse.datasource.entity.User userCreateInputToUser(UserCreateInput userCreateInput);
}
