package org.programmers.interparkyu.user.controller;

import static org.programmers.interparkyu.user.controller.UserController.userRequestUri;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.common.dto.ApiResponse;
import org.programmers.interparkyu.user.dto.request.CreateUserRequest;
import org.programmers.interparkyu.user.dto.response.UserIdResponse;
import org.programmers.interparkyu.user.dto.response.UserResponse;
import org.programmers.interparkyu.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(userRequestUri)
public class UserController {

    public static final String userRequestUri = "/v1/users";

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserIdResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        Long userId = userService.saveUser(request);
        return ApiResponse.ok(userRequestUri, new UserIdResponse(userId));
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUser(final @PathVariable Long userId) {
        return ApiResponse.ok(userRequestUri + userId, userService.getUserResponse(userId));
    }

}
