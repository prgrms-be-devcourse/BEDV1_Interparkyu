package org.programmers.interparkyu.user.service;

import lombok.AllArgsConstructor;
import org.programmers.interparkyu.user.domain.User;
import org.programmers.interparkyu.user.dto.request.CreateUserRequest;
import org.programmers.interparkyu.user.dto.response.UserResponse;
import org.programmers.interparkyu.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Long saveUser(CreateUserRequest request) {
        return repository
            .save(new User(request.name()))
            .getId();
    }

    @Transactional(readOnly = true)
    public UserResponse getUserResponseById(Long userId) {
        return UserResponse.from(repository.getById(userId));
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return repository.getById(userId);
    }

}
