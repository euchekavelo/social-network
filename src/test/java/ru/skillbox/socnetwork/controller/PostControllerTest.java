package ru.skillbox.socnetwork.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/001-create-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/002-fill-tables.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/003-person-table-changes.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/004-dialog-and-message-table-changes.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/005-delete-tables.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PostControllerTest {

    private final String FEED = "{\"error\":\"string\"," +
            "\"total\":20," +
            "\"offset\":0," +
            "\"perPage\":20," +
            "\"data\":[{\"id\":5}, {\"id\":4}, {\"id\":3}, {\"id\":2}, {\"id\":1}]}";


    private final String WALL = "{\"error\":\"string\"," +
            "\"total\":20," +
            "\"offset\":0," +
            "\"perPage\":20," +
            "\"data\":[{" +
            "\"id\":1," +
            "\"time\":1649367846501," +
            "\"author\":{\"id\":1," +
            "\"email\":\"test@mail.ru\"," +
            "\"phone\":\"+7(111)1111111\"," +
            "\"city\":\"Saratov\"," +
            "\"country\":\"Russia\"," +
            "\"first_name\":\"Test\"," +
            "\"last_name\":\"Test\"," +
            "\"reg_date\":1649367846500," +
            "\"birth_date\":1649367846000," +
            "\"messages_permission\":\"ALL\"," +
            "\"last_online_time\":0," +
            "\"is_blocked\":false}," +
            "\"title\":\"test title example\"," +
            "\"likes\":2," +
            "\"tags\":[\"top15\",\"java\",\"code\",\"true_story\",\"spring\",\"core\",\"bootstrap\"]," +
            "\"comments\":[{" +
            "\"id\":1," +
            "\"likes\":0," +
            "\"time\":1649367846508," +
            "\"parent_id\":0," +
            "\"post_id\":1," +
            "\"comment_text\":\"test title example\"," +
            "\"my_like\":false," +
            "\"author\":{\"id\":5," +
            "\"email\":\"ilin@mail.ru\"," +
            "\"phone\":\"+7(999)9999999\"," +
            "\"city\":\"Tbilisi\"," +
            "\"country\":\"Georgia\"," +
            "\"first_name\":\"Ilya\"," +
            "\"last_name\":\"Ilin\"," +
            "\"reg_date\":1649367846500," +
            "\"birth_date\":1649367846000," +
            "\"messages_permission\":\"ALL\"," +
            "\"last_online_time\":0," +
            "\"is_blocked\":false}," +
            "\"is_blocked\":false}]," +
            "\"type\":\"POSTED\"," +
            "\"post_text\":\"test post text example\"," +
            "\"is_blocked\":false," +
            "\"my_like\":true}]}";

    private final String POST = "{\"error\":\"string\"," +
            "\"total\":20," +
            "\"offset\":0," +
            "\"perPage\":20," +
            "\"data\":{" +
            "\"id\":1," +
            "\"time\":1649367846501," +
            "\"author\":{\"id\":1," +
            "\"email\":\"test@mail.ru\"," +
            "\"phone\":\"+7(111)1111111\"," +
            "\"city\":\"Saratov\"," +
            "\"country\":\"Russia\"," +
            "\"first_name\":\"Test\"," +
            "\"last_name\":\"Test\"," +
            "\"reg_date\":1649367846500," +
            "\"birth_date\":1649367846000," +
            "\"messages_permission\":\"ALL\"," +
            "\"last_online_time\":0," +
            "\"is_blocked\":false}," +
            "\"title\":\"test title example\"," +
            "\"likes\":2," +
            "\"tags\":[\"top15\",\"java\",\"code\",\"true_story\",\"spring\",\"core\",\"bootstrap\"]," +
            "\"comments\":[{" +
            "\"id\":1," +
            "\"likes\":0," +
            "\"time\":1649367846508," +
            "\"parent_id\":0," +
            "\"post_id\":1," +
            "\"comment_text\":\"test title example\"," +
            "\"my_like\":false," +
            "\"author\":{\"id\":5," +
            "\"email\":\"ilin@mail.ru\"," +
            "\"phone\":\"+7(999)9999999\"," +
            "\"city\":\"Tbilisi\"," +
            "\"country\":\"Georgia\"," +
            "\"first_name\":\"Ilya\"," +
            "\"last_name\":\"Ilin\"," +
            "\"reg_date\":1649367846500," +
            "\"birth_date\":1649367846000," +
            "\"messages_permission\":\"ALL\"," +
            "\"last_online_time\":0," +
            "\"is_blocked\":false}," +
            "\"is_blocked\":false}]," +
            "\"type\":\"POSTED\"," +
            "\"post_text\":\"test post text example\"," +
            "\"is_blocked\":false," +
            "\"my_like\":true}}";


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
                .andExpect(MockMvcResultMatchers.content().json(POST));
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
                .andExpect(MockMvcResultMatchers.content().json(WALL));
    }

    @Test
    @WithUserDetails("test@mail.ru")
    void getFeedsTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/feeds"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(FEED));
    }
}