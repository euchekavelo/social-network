package ru.skillbox.socnetwork.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.web.servlet.function.ServerResponse.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DialogsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DialogsController dialogsController;

    @Autowired
    private AuthController authController;

    @Test
    public void test() throws Exception {
        //this.mockMvc.;
        //assertThat(dialogsController).isNotNull();
    }
}
