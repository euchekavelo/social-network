package ru.skillbox.socnetwork.controller;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
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

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@AutoConfigureEmbeddedDatabase
        (provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.OPENTABLE,
                refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_CLASS)
@Sql(value = {"/sql/001-create-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/002-fill-tables.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/003-delete-tables.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("ilin@mail.ru")
    //@Sql(value = {}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void searchByPeopleBySurname() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(
                        "/api/v1/users/search?last_name=Test"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(Files.readString(Path.of(
                                "src/test/resources/json/profile_test/people_search_by_surname.json"))));
    }

    @Test
    @WithUserDetails("ilin@mail.ru")
    void searchByPeopleByName() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(
                        "/api/v1/users/search?first_name=Onufriy"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(Files.readString(Path.of(
                                "src/test/resources/json/profile_test/people_search_by_name.json"))));
    }

}