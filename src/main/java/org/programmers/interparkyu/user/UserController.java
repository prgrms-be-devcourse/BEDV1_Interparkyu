package org.programmers.interparkyu.user;

import javax.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.ApiResponse;
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
@RequestMapping("/v1")
public class UserController {

    private UserService userService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> invalidRequestHandler(MethodArgumentNotValidException e) {
        return ApiResponse.fail(e.getMessage(), "someuri", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<String> invalidRequestHandler(ConstraintViolationException e) {
        return ApiResponse.fail(e.getMessage(), "someuri", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/users")
    public ApiResponse<UserIdResponse> createUser(
        @Validated @RequestBody
        CreateUserRequest request
    ) {
        Long userId = userService.saveUser(request);
        return ApiResponse.ok(
            "/v1/users",
            new UserIdResponse(userId)
        );
    }

}
