package com.example.graphqlcourse.component.problemz;

import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.*;
import com.example.graphqlcourse.exception.AuthenticationException;
import com.example.graphqlcourse.exception.UnauthorizedException;
import com.example.graphqlcourse.service.command.UserCommandService;
import com.example.graphqlcourse.service.query.UserQueryService;
import com.example.graphqlcourse.util.mapper.UserMapper;
import com.example.graphqlcourse.util.mapper.UserTokenMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@DgsComponent
@AllArgsConstructor
public class UserDataResolver {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;
    private final UserMapper userMapper;
    private final UserTokenMapper userTokenMapper;

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.Me
    )
    public User accountInfo(@RequestHeader(name = "authToken") String authToken) {
        return userMapper.userToUserQL(userQueryService.findUserByAuthToken(authToken)
                .orElseThrow(DgsEntityNotFoundException::new));
    }

    @Secured("ROLE_ADMIN")
    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.UserCreate
    )
    public UserResponse createUser(@InputArgument(name = "user") UserCreateInput createInput) {
//        var userAuth = userQueryService.findUserByAuthToken(authToken).orElseThrow(AuthenticationException::new);
//
//        if (!StringUtils.equals(userAuth.getUserRole(), "ROLE_ADMIN")) {
//            throw new UnauthorizedException();
//        }

        var user = userMapper.userCreateInputToUser(createInput);
        var createdUser = userCommandService.createUser(user);

        return UserResponse.newBuilder()
                .user(userMapper.userToUserQL(createdUser))
                .build();
    }

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.UserLogin
    )
    public UserResponse userLogin(@InputArgument(name = "user") UserLoginInput loginInput) {
        UserAuthToken userAuthToken = userTokenMapper.userTokenToUserAuthTokenQL(
                userCommandService.login(loginInput.getUsername(), loginInput.getPassword())
        );
        var userInfo = userQueryService.findUserByAuthToken(userAuthToken.getAuthToken())
                .orElseThrow(AuthenticationException::new);
        return UserResponse.newBuilder()
                .authToken(userAuthToken)
                .user(userMapper.userToUserQL(userInfo))
                .build();
    }

    @Secured("ROLE_ADMIN")
    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.UserActivation
    )
    public UserActivationResponse userActivation(
            @InputArgument(name = "user") UserActivationInput userActivationInput) {
//        var userAuth = userQueryService.findUserByAuthToken(authToken).orElseThrow(AuthenticationException::new);
//
//        if (!StringUtils.equals(userAuth.getUserRole(), "ROLE_ADMIN")) {
//            throw new UnauthorizedException();
//        }

        return userCommandService.activateUser(
                UUID.fromString(userActivationInput.getUserId()),
                userActivationInput.getActive()
                )
                .map(user -> UserActivationResponse.newBuilder()
                        .isActive(user.isActive())
                        .build())
                .orElseThrow(DgsEntityNotFoundException::new);
    }
}
