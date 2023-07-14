package com.example.graphqlcourse.service.command;

import com.example.graphqlcourse.datasource.entity.UserToken;
import com.example.graphqlcourse.datasource.repository.UserRepository;
import com.example.graphqlcourse.datasource.repository.UserTokenRepository;
import com.example.graphqlcourse.exception.AuthenticationException;
import com.example.graphqlcourse.util.HashUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;

    public UserToken login(String username, String password) {
        var userOptional = userRepository.findByUsernameIgnoreCase(username);

        if (userOptional.isEmpty() || !HashUtils.isBcryptMatch(password, userOptional.get().getHashedPassword())) {
            throw new AuthenticationException();
        }

        String randomAuthToken = RandomStringUtils.randomAlphanumeric(40);
        UserToken userToken = createRefreshToken(userOptional.get().getId(), randomAuthToken);
        return userTokenRepository.save(userToken);
    }

    private UserToken createRefreshToken(UUID userId, String authToken) {
        UserToken userToken = new UserToken();
        userToken.setUserId(userId);
        userToken.setAuthToken(authToken);

        LocalDateTime now = LocalDateTime.now();
        userToken.setCreationTimestamp(now);
        userToken.setExpiryTimestamp(now.plusHours(2));

        return userToken;
    }
}
