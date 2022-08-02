package com.accolite.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	@Order(8)
	public void getUserTest1() throws Exception {

		MvcResult result = mvc.perform(get("/getUser?userId=1")).andReturn();
		MockHttpServletResponse resp = result.getResponse();
		assertNotNull(resp.getContentAsString());
		assertEquals(302, resp.getStatus());
	}

	@Test
	@Order(9)
	public void getUserTest2() throws Exception {

		MvcResult result = mvc.perform(get("/getUser/0")).andReturn();
		MockHttpServletResponse resp = result.getResponse();
		assertNotNull(resp.getContentAsString());
		assertEquals(404, resp.getStatus());
	}

	@Test
	@Order(1)
	public void addUserTest1() throws Exception {

		MvcResult result = mvc.perform(post("/addUser").header("Content-Type", "application/json")
				.content("{\"userName\":\"prince\",\"userEmail\":\"pk22cs@gmail.com\"}")).andReturn();
		MockHttpServletResponse resp = result.getResponse();
		assertNotNull(resp.getContentAsString());
		assertEquals(HttpStatus.CREATED.value(), resp.getStatus());

	}

	@Test
	@Order(2)
	public void addUserTest2() throws Exception {

		MvcResult result = mvc.perform(post("/addUser").header("Content-Type", "application/json")
				.content("{\"userName\":\"pk\",\"userEmail\":\"pk@gmai\"}")).andReturn();
		MockHttpServletResponse resp = result.getResponse();
		assertNotNull(resp.getContentAsString());
		assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), resp.getStatus());

	}

	// User Exist we need to update user
	@Test
	@Order(3)
	public void updateUserTest1() throws Exception {
		MvcResult result = mvc.perform(put("/updateUser").contentType(MediaType.APPLICATION_JSON)
				.content("{\"userId\":1,\"userName\":\"sonu\",\"userEmail\":\"sonu@gmail.com\"}")).andReturn();
		MockHttpServletResponse resp = result.getResponse();
		assertNotNull(resp.getContentAsString());
		assertEquals(HttpStatus.OK.value(), resp.getStatus());

	}

	// This is for user Not exist in DB
	@Test
	@Order(4)
	public void updateUserTest2() throws Exception {
		MvcResult result = mvc
				.perform(put("/updateUser").contentType(MediaType.APPLICATION_JSON)
						.content("{\"userId\":10,\"userName\":\"Deepak\",\"userEmail\":\"deepak@gmail.com\"}"))
				.andReturn();
		MockHttpServletResponse resp = result.getResponse();
		assertNotNull(resp.getContentAsString());
		assertEquals(HttpStatus.CREATED.value(), resp.getStatus());

	}

	// Wrong Scenario If user name length is less then 3
	@Test
	@Order(5)
	public void updateUserTest3() throws Exception {
		MvcResult result = mvc.perform(put("/updateUser").contentType(MediaType.APPLICATION_JSON)
				.content("{\"userId\":1,\"userName\":\"pk\",\"userEmail\":\"pk22cs@gmail.com\"}")).andReturn();
		MockHttpServletResponse resp = result.getResponse();
		assertNotNull(resp.getContentAsString());
		assertEquals(406, resp.getStatus());

	}

	@Test
	@Order(6)
	public void deleteUserTest1() throws Exception {
		MvcResult result = mvc.perform(delete("/deleteUser?userId=0")).andReturn();
		MockHttpServletResponse resp = result.getResponse();
		assertNotNull(resp.getContentAsString());
		assertEquals(404, resp.getStatus());

	}

	@Test
	@Order(7)
	public void deleteUserTest2() throws Exception {
		MvcResult result = mvc.perform(delete("/deleteUser?userId=2")).andReturn();
		MockHttpServletResponse resp = result.getResponse();
		assertNotNull(resp.getContentAsString());
		assertEquals(200, resp.getStatus());

	}

}
