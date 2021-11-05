package org.programmers.interparkyu.user.dto.request;

import javax.validation.constraints.NotBlank;

public record CreateUserRequest(

    @NotBlank
    String name

) { }
