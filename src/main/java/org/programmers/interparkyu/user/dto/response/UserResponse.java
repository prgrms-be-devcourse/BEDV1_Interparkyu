package org.programmers.interparkyu.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.programmers.interparkyu.user.domain.User;

public record UserResponse(

    Long id,

    @JsonProperty("username")
    String name

) {

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName());
    }

}
