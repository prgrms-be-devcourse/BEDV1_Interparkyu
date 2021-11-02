package org.programmers.interparkyu.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.programmers.interparkyu.user.domain.User;

public record UserResponse(

    Long id,

    @JsonProperty("username")
    String name

) {

    @Builder
    public UserResponse {}

    public static UserResponse from(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .build();
    }

}
