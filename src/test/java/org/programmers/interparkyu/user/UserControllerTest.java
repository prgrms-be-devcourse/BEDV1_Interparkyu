package org.programmers.interparkyu.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원을 생성할 수 있다")
    @Transactional
    public void save() throws Exception {
        // Given
        String name = "sangmin lee";
        CreateUserRequest request = new CreateUserRequest(name);

        // When
        MvcResult result = mockMvc
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
            .andReturn();

        // Then
        Long userId = JsonPath
            .parse(result.getResponse().getContentAsString())
            .read("$.data[0].userId", Long.class);

        String maybeName = userRepository.getById(userId).getName();

        assertThat(maybeName, equalTo(name));
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

    @Test
    @DisplayName("회원을 ID로 조회할 수 있다")
    @Transactional
    public void getUserById() throws Exception {
        // Given
        String username = "sangmin lee";
        User user = new User(username);
        userRepository.save(user);

        // When Then
        mockMvc
            .perform(get("/v1/users/" + user.getId()))
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.common.internalHttpStatusCode")
                    .value(200)
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.data[0].username")
                    .value(username)
            );
    }

}
