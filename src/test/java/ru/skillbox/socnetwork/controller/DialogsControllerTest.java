package ru.skillbox.socnetwork.controller;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rsdto.DialogDto;
import ru.skillbox.socnetwork.model.rsdto.DialogsResponse;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.PersonForDialogsDto;
import ru.skillbox.socnetwork.repository.DialogRepository;
import ru.skillbox.socnetwork.repository.MessageRepository;
import ru.skillbox.socnetwork.security.SecurityUser;
import ru.skillbox.socnetwork.service.DialogsService;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.OPENTABLE,
                refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_CLASS)
@Sql(value = {"/001-create-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/002-fill-tables.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/003-person-table-changes.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/004-dialog-and-message-table-changes.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/005-delete-tables.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DialogsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("test@mail.ru")
    public void createDialogTest() throws Exception {

        List<Integer> id = List.of(11);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/dialogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Files.readAllBytes(Paths.get("src/test/resources/json/create_dialog_rq.json")))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("string"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(11));
    }
    @Test
    @WithUserDetails("test@mail.ru")
    public void getDialogsTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/dialogs"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(Files.readString(Paths.get("src/test/resources/json/dialog_rs.json"))));

    }

    @Test
    @WithUserDetails("test@mail.ru")
    public void sendMessageTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/dialogs/1/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Files.readAllBytes(Paths.get("src/test/resources/json/send_message_rq.json")))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(Files.readString(Paths.get("src/test/resources/json/send_message_rs.json"))));
    }

    @Test
    @WithUserDetails("test@mail.ru")
    public void getMessagesByDialogIdTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/dialogs/1/messages"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(Files.readString(Paths.get("src/test/resources/json/messages_by_dialog_id_rs.json"))));
    }

}

