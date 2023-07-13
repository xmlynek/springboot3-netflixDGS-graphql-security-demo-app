package com.example.graphqlcourse.component.problemz;

import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.*;
import com.example.graphqlcourse.datasource.repository.UserRepository;
import com.example.graphqlcourse.util.mapper.UserMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@DgsComponent
@AllArgsConstructor
public class UserDataResolver {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.Me
    )
    public User accountInfo(@RequestHeader(name = "authToken") String authToken) {
        return userRepository.findById(UUID.fromString("17fceead-5938-493b-b4b9-14726f2ddd9f"))
                .map(userMapper::userToUserQL).get();
    }

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.UserCreate
    )
    public UserResponse createUser(@InputArgument(name = "user") UserCreateInput createInput) {
        return null;
    }

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.UserLogin
    )
    public UserResponse userLogin(@InputArgument(name = "user") UserLoginInput loginInput) {
        return null;
    }

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.UserActivation
    )
    public UserActivationResponse userActivation(@InputArgument(name = "user") UserActivationInput userActivationInput) {
        return null;
    }
}
