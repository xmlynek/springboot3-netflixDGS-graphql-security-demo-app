package com.example.graphqlcourse.service.query;

import com.example.graphqlcourse.datasource.entity.User;
import com.example.graphqlcourse.datasource.repository.UserRepository;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    public User findUserByAuthToken(String authToken) {
        return userRepository.findUserByAuthToken(authToken)
                .orElseThrow(() -> new DgsEntityNotFoundException("User with given authToken not found"));
    }
}
