package org.programmers.interparkyu.user;

import lombok.AllArgsConstructor;
import org.programmers.interparkyu.user.dto.request.CreateUserRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    UserRepository repository;

    public Long saveUser(CreateUserRequest request) {
        return repository
            .save(new User(request.name()))
            .getId();
    }

}
