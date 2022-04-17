package ru.skillbox.socnetwork.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

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
    public void getExistentPostByIdTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/9"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"data\":{\"id\":9,\"time\":1649367846506,\"author\":" +
                        "{\"id\":3,\"email\":\"petrov@mail.ru\",\"phone\":\"+7(999)9999999\"," +
                        "\"first_name\":\"Петр\",\"last_name\":\"Петров\",\"reg_date\":1649367846506,\"birth_date\":1649367846506," +
                        "\"messages_permission\":\"ALL\",\"last_online_time\":1649367846506,\"is_blocked\":false},\"title\":\"petr title example 2\"," +
                        "\"likes\":4,\"comments\":[],\"type\":\"POSTED\",\"post_text\":\"petr post text example 2\",\"is_blocked\":false,\"my_like\":false}}"))
        ;
    }

    @Test
    @WithUserDetails("test@mail.ru")
    public void getNonexistentPostByIdTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/-1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"error\":\"invalid_request\",\"error_description\":\"entity not found\"}"));
    }

    
}
