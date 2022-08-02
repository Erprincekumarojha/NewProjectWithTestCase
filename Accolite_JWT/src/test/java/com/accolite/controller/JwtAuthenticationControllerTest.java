package com.accolite.controller;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(OrderAnnotation.class)
public class JwtAuthenticationControllerTest {

	
	@Autowired
	private MockMvc mvc;

	@Test
	public void generateAuthenticationTokenTest() throws Exception {

		MvcResult result = mvc.perform(post("/authenticate").header("Content-Type", "application/json")
				.content("{\"username\":\"prince\",\"password\":\"12345\"}")).andReturn();
		MockHttpServletResponse resp = result.getResponse();
		assertNotNull(resp.getContentAsString());
		assertEquals(HttpStatus.UNAUTHORIZED.value(), resp.getStatus());

	}
	
	@Test
	public void generateAuthenticationTokenTest2() throws Exception {
		MvcResult result = mvc.perform(post("/authenticate").header("Content-Type", "application/json")
				.content("{\"username\":\"admin\",\"password\":\"password\"}")).andReturn();
		MockHttpServletResponse resp = result.getResponse();
		assertNotNull(resp.getContentAsString());
		assertEquals(HttpStatus.OK.value(), resp.getStatus());

	}

	

}
