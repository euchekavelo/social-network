package ru.skillbox.socnetwork;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SocialNetworkApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void unauthorizedAccessDeniedTest() throws Exception {
		this.mockMvc.perform(get("/api/v1/post/1"))
				.andDo(print())
				.andExpect(status().isForbidden());
	}

}