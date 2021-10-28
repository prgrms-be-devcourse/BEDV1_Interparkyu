package org.programmers.interparkyu.user;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.ApiResponse;
import org.programmers.interparkyu.user.dto.response.UserResponse;
import org.programmers.interparkyu.user.dto.request.CreateUserRequest;
import org.programmers.interparkyu.user.dto.response.UserIdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ApiResponse<UserIdResponse> createUser(
        @Valid @RequestBody
        CreateUserRequest request
    ) {
        Long userId = userService.saveUser(request);
        return ApiResponse.ok(
            "/v1/users",
            new UserIdResponse(userId)
        );
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long userId) {
        return ApiResponse.ok(
            "/v1/users/" + userId,
            userService.getUserById(userId));
    }

}
