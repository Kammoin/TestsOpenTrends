package com.opentrends.repository.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.opentrends.repository.service.RepositoryService;

@RunWith(SpringRunner.class)
@WebMvcTest(RepositoryController.class)
@AutoConfigureMockMvc
public class RepositoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	RepositoryService repositoryService;

	@Test
	public void whenPostRequestToUsersAndValidUser_thenCorrectResponse() throws Exception {
		String repositorySpecifications = "{\"name\": \"someName\", \"description\" : \"desc\", \"tagName\" : \"someTag\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/repository/GITLAB").content(repositorySpecifications)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}

	@Test
	public void whenPostRequestToRepositoriesAndInvalidName_thenCorrectResponse() throws Exception {
		String repositorySpecifications = "{\"name\": \"\", \"description\" : \"desc\", \"tagName\" : \"someTag\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/repository/GITLAB").content(repositorySpecifications)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void whenPostRequestToRepositoriesAndInvalidDescription_thenCorrectResponse() throws Exception {
		String repositorySpecifications = "{\"name\": \"someName\", \"description\" : \"\", \"tagName\" : \"someTag\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/repository/GITLAB").content(repositorySpecifications)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void whenPostRequestToRepositoriesAndInvalidTagName_thenCorrectResponse() throws Exception {
		String repositorySpecifications = "{\"name\": \"someName\", \"description\" : \"desc\", \"tagName\" : \"\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/repository/GITLAB").content(repositorySpecifications)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

}
