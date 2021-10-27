package org.programmers.interparkyu.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.interparkyu.user.domain.User;
import org.programmers.interparkyu.user.dto.request.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원을 생성할 수 있다")
    @Transactional
    public void save() throws Exception {
        // Given
        CreateUserRequest request = new CreateUserRequest("sangmin lee");

        // When Then
        mockMvc
            .perform(
                post("/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.common.internalHttpStatusCode")
                    .value(200)
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.data[0].userId")
                    .value(1L)
            );
    }

    @Test
    @DisplayName("이름이 빈 회원은 생성할 수 없다")
    @Transactional
    public void saveUserWithBlankName() throws Exception{
        // Given
        CreateUserRequest request = new CreateUserRequest(" ");

        // When Then
        mockMvc
            .perform(
                post("/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.common.internalHttpStatusCode")
                    .value(400)
            );
    }

    @Test
    @DisplayName("이름이 제한 길이를 초과인 회원은 생성할 수 없다")
    @Transactional
    public void saveUserWithInvalidLengthName() throws Exception{
        // Given
        String name = new String(new char[User.getMAX_NAME_LENGTH()+1]).replace('\0', 'a');
        CreateUserRequest request = new CreateUserRequest(name);

        // When Then
        mockMvc
            .perform(
                post("/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.common.internalHttpStatusCode")
                    .value(400)
            );
    }

}
