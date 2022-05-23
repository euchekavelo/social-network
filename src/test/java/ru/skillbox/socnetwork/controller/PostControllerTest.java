package ru.skillbox.socnetwork.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/properties/application-test-alexandr.properties")
@Sql(value = {"/sql/001-create-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/002-fill-tables.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/005-delete-tables.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void unauthorizedAccessDeniedTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithUserDetails("test@mail.ru")
    void getExistentPostByIdTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(Files.readString(Path.of("src/test/resources/json/post_platform_likes/post.json"))));
    }

    @Test
    @WithUserDetails("test@mail.ru")
    void getNonexistentPostByIdTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/-1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"error\":\"invalid_request\"," +
                        "\"error_description\":\"Incorrect post data, can't find this id -1\"}"));
    }

    @Test
    @WithUserDetails("test@mail.ru")
    void getWallTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1/wall"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(Files.readString(Path
                                .of("src/test/resources/json/post_platform_likes/wall.json"))));
    }

    @Test
    @WithUserDetails("test@mail.ru")
    void getFeedsTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/feeds"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(Files.readString(Path
                                .of("src/test/resources/json/post_platform_likes/feeds.json"))));
    }

    @Test
    @WithUserDetails("petrov@mail.ru")
    void addPostTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/3/wall")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"new post\"," +
                                "\"post_text\":\"new post text\"," +
                                "\"tags\":[\"java\",\"new post\"]}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json((Files.readString(Path
                                .of("src/test/resources/json/post_platform_likes/new_post.json")))));
    }

}