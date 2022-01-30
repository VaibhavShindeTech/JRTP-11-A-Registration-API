package com.vaibhavshindetech.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.vaibhavshindetech.bindings.User;
import com.vaibhavshindetech.constans.AppConstants;
import com.vaibhavshindetech.entities.CityEntity;
import com.vaibhavshindetech.entities.CountryEntity;
import com.vaibhavshindetech.entities.StateEntity;
import com.vaibhavshindetech.entities.UserEntity;
import com.vaibhavshindetech.props.AppProperties;
import com.vaibhavshindetech.repositories.CityRepository;
import com.vaibhavshindetech.repositories.CountryRepository;
import com.vaibhavshindetech.repositories.StateRepository;
import com.vaibhavshindetech.repositories.UserRepository;
import com.vaibhavshindetech.service.UserServiceImpl;
import com.vaibhavshindetech.util.EmailUtils;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(UserServiceImpl.class)
public class UserServiceImplTest {


	@MockBean
	private UserRepository userRepositoryMock;

	@MockBean
	private CountryRepository countryRepositoryMock;

	@MockBean
	private StateRepository stateRepositoryMock;

	@MockBean
	private CityRepository cityRepositoryMock;

	@MockBean
	private EmailUtils emailUtilsMock;

	@MockBean
	private AppProperties appPropertiesMock;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Test
	public void isEmailUniqueTest1() {
		UserEntity user = new UserEntity();
		when(userRepositoryMock.findByUserEmail("vaibhav@gmail.com")).thenReturn(user);
		boolean actual = userServiceImpl.isEmailUnique("vaibahv@gmail.com");
		assertTrue(actual);
	}

	@Test
	public void isEmailUniqueTest2() {
		UserEntity user = new UserEntity();
		user.setUserId(101);
		user.setUserAccStatus(AppConstants.LOCKED);
		user.setUserEmail("shinde@gmail.com");
		user.setUserFname("vaibhav");
		user.setUserLname("Shinde");
		user.setUserGender("M");
		when(userRepositoryMock.findByUserEmail("shinde@gmail.com")).thenReturn(user);
		boolean actual = userServiceImpl.isEmailUnique("shinde@gmail.com");
		assertFalse(actual);
	}

	@Test
	public void getCountriesTest() {
		CountryEntity countryEntity_1 = new CountryEntity();
		countryEntity_1.setCountryId(1);
		countryEntity_1.setCountryName("India");
		List<CountryEntity> countriesList = new ArrayList<>();
		countriesList.add(countryEntity_1);

		when(countryRepositoryMock.findAll()).thenReturn(countriesList);
		Map<Integer, String> countriesMapActual = userServiceImpl.getCountries();

		Map<Integer, String> countriesMapExpected = new HashMap<>();
		countriesMapExpected.put(countryEntity_1.getCountryId(), countryEntity_1.getCountryName());

		assertEquals(countriesMapExpected, countriesMapActual);
	}

	@Test
	public void getStatesTest() {
		List<StateEntity> statesList = new ArrayList<>();
		StateEntity stateEntity_1 = new StateEntity();
		stateEntity_1.setStateId(1);
		stateEntity_1.setStateName("Maharashtra");
		stateEntity_1.setCountryId(1);
		statesList.add(stateEntity_1);

		when(stateRepositoryMock.findByCountryId(1)).thenReturn(statesList);

		Map<Integer, String> statesMapActual = userServiceImpl.getStates(1);

		Map<Integer, String> statesMapExpected = new HashMap<>();
		statesMapExpected.put(stateEntity_1.getStateId(), stateEntity_1.getStateName());

		assertEquals(statesMapExpected, statesMapActual);
	}

	@Test
	public void getCitiesTest() {
		List<CityEntity> citiesList = new ArrayList<>();
		CityEntity cityEntity_1 = new CityEntity();
		cityEntity_1.setCityId(1);
		cityEntity_1.setCityName("Kolhapur");
		cityEntity_1.setStateId(1);

		citiesList.add(cityEntity_1);

		when(cityRepositoryMock.findByStateId(1)).thenReturn(citiesList);

		Map<Integer, String> citiesMapActual = userServiceImpl.getCities(1);

		Map<Integer, String> citiesMapExpected = new HashMap<>();
		citiesMapExpected.put(cityEntity_1.getCityId(), cityEntity_1.getCityName());

		assertEquals(citiesMapExpected, citiesMapActual);
	}
//	@Test
//	public void registerUserTest() throws Exception {
//		User user = new User();
//		user.setUserFname("Vaibhav");
//		user.setUserLname("Shinde");
//		user.setUserEmail("vaibhav@gmail.com");
//		user.setUserDob(LocalDate.of(1998, 04, 28));
//		user.setUserGender("M");
//		user.setUserCountry(1);
//		user.setUserState(1);
//		user.setUserCity(1);
//		user.setUserId(101);
//
//		when(userRepositoryMock.save(Mockito.any(UserEntity.class))).thenAnswer(i -> i.getArguments()[0]);
//		UserServiceImpl spy = PowerMockito.spy(userServiceImpl);
//        PowerMockito.when(spy, "sendRegEmail",user).thenReturn(true);
//		boolean registerUser = userServiceImpl.registerUser(user);
//		
//		assertTrue(registerUser);
//		
//	}
	@Test
	public void readMailBodyTest1() {
		User user = new User();
		user.setUserFname("Vaibhav");
		user.setUserEmail("vaibhav@gmail.com");
		user.setUserPwd("abcdefg");
		String readMailBody = userServiceImpl.readMailBody("UNLOCK-ACC-EMAIL-BODY-TEMPLATE.txt",user);
		assertNotNull(readMailBody);
	}
//	@Test
//	public void readMailBodyTest2() {
//		User user = new User();
//		user.setUserFname("Vaibhav");
//		user.setUserEmail("vaibhav@gmail.com");
//		user.setUserPwd("abcdefg");
//		assertThrows(IOException.class, () -> userServiceImpl.readMailBody(AppConstants.REG_EMAIL_FILENAME, user));
//	}
	
}
