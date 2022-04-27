package ru.skillbox.socnetwork.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
@Sql(value = {"/003-delete-tables.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PlatformControllerTest {

    private final String CITIES_DATA = "{\"data\":[\"Perm\",\"Tbilisi\",\"Tver\",\"Saratov\",\"Alma-Ata\",\"Kaliningrad\"]}";

    private final String COUNTRIES_DATA = "{\"data\":[\"Russia\",\"Georgia\",\"Kazakhstan\"]}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCountries() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/platform/countries"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(COUNTRIES_DATA));
    }

    @Test
    void getCities() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/platform/cities"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(CITIES_DATA));
    }
}
