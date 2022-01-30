package com.vaibhavshindetech.rest.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaibhavshindetech.bindings.User;
import com.vaibhavshindetech.constans.AppConstants;
import com.vaibhavshindetech.rest.UserRestController;
import com.vaibhavshindetech.service.UserService;

@WebMvcTest(value = UserRestController.class)
public class UserRestControllerTest {
	@MockBean
	private UserService userServiceMock;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void isEmailUniqueTest1() throws Exception {
		// behavior of mock object
		when(userServiceMock.isEmailUnique("vaibhav@gmail.com")).thenReturn(true);

		// creating get request
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/email/vaibhav@gmail.com");

		// sending request to controller and getting response
		MvcResult result = mockMvc.perform(builder).andReturn();

		String actual = result.getResponse().getContentAsString();

		assertEquals(AppConstants.UNIQUE, actual);
	}

	@Test
	public void isEmailUniqueTest2() throws Exception {
		// defining behavior of mock object
		when(userServiceMock.isEmailUnique("vaibhav@gmail.com")).thenReturn(false);

		// creating get request
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/email/vaibhav@gmail.com");

		// sending request to controller and getting response
		MvcResult result = mockMvc.perform(builder).andReturn();

		String actual = result.getResponse().getContentAsString();
		assertEquals(AppConstants.DUPLICATE, actual);
	}

	@Test
	public void getCountriesTest() throws Exception {
		// defining behavior of mock object
		Map<Integer, String> countries = new HashMap<>();
		countries.put(1, "India");
		countries.put(2, "Dubai");
		when(userServiceMock.getCountries()).thenReturn(countries);

		// creating get request
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/countries");
		// sending request to controller and getting response
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();

		assertEquals(200, status);
	}

	@Test
	public void getStatesTest() throws Exception {
//		defining behavior of mock object
		Map<Integer, String> states = new HashMap<>();
		states.put(1, "MH");
		states.put(2, "goa");
		when(userServiceMock.getStates(1)).thenReturn(states);

//		creating get request
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/states/1");

//		sending request to controller
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		int status = result.getResponse().getStatus();

		assertEquals(200, status);

	}

	@Test
	public void getCitiesTest() throws Exception {
//		defining behavior of mock object
		Map<Integer, String> cities = new HashMap<>();
		cities.put(1, "Mumbai");
		cities.put(2, "Pune");
		when(userServiceMock.getCities(1)).thenReturn(cities);

//		creating get request
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cities/1");

//		sending request to controller
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	public void registerUserTest1() throws Exception {
		User user = new User();
		user.setUserId(101);
		user.setUserFname("vaibhav");
		user.setUserLname("shinde");
		user.setUserEmail("vaibhav@gmail.com");
		user.setUserCity(1);
		user.setUserCountry(1);
		user.setUserState(1);
		user.setUserAccStatus(AppConstants.LOCKED);

		when(userServiceMock.registerUser(user)).thenReturn(true);

		ObjectMapper objectMapper = new ObjectMapper();
		String userJson = objectMapper.writeValueAsString(user);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register")
				.contentType("application/json").content(userJson);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String actual = result.getResponse().getContentAsString();

		assertEquals(AppConstants.SUCCESS, actual);
	}

	@Test
	public void registerUserTest2() throws Exception {
		User user = new User();
		user.setUserId(101);
		user.setUserFname("vaibhav");
		user.setUserLname("shinde");
		user.setUserEmail("vaibhav@gmail.com");
		user.setUserCity(1);
		user.setUserCountry(1);
		user.setUserState(1);
		user.setUserAccStatus(AppConstants.LOCKED);

		when(userServiceMock.registerUser(user)).thenReturn(false);

		ObjectMapper objectMapper = new ObjectMapper();
		String userJson = objectMapper.writeValueAsString(user);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register")
				.contentType("application/json").content(userJson);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String actual = result.getResponse().getContentAsString();

		assertEquals(AppConstants.FAIL, actual);
	}
}
