package org.programmers.interparkyu.user;

import lombok.AllArgsConstructor;
import org.programmers.interparkyu.ApiResponse;
import org.programmers.interparkyu.user.dto.request.CreateUserRequest;
import org.programmers.interparkyu.user.dto.response.UserIdResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class UserController {

    private UserService userService;

    @PostMapping("/users")
    public ApiResponse<UserIdResponse> createUser(@RequestBody CreateUserRequest request) {
        Long userId = userService.saveUser(request);
        return ApiResponse.ok(
            "/v1/users",
            new UserIdResponse(userId)
        );
    }

}
