package com.vaibhavshindetech.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaibhavshindetech.bindings.User;
import com.vaibhavshindetech.constans.AppConstants;
import com.vaibhavshindetech.entities.CityEntity;
import com.vaibhavshindetech.entities.CountryEntity;
import com.vaibhavshindetech.entities.StateEntity;
import com.vaibhavshindetech.entities.UserEntity;
import com.vaibhavshindetech.exception.RegAppException;
import com.vaibhavshindetech.props.AppProperties;
import com.vaibhavshindetech.repositories.CityRepository;
import com.vaibhavshindetech.repositories.CountryRepository;
import com.vaibhavshindetech.repositories.StateRepository;
import com.vaibhavshindetech.repositories.UserRepository;
import com.vaibhavshindetech.util.EmailUtils;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private AppProperties appProperties;

	@Override
	public boolean isEmailUnique(String email) {
		UserEntity user = userRepository.findByUserEmail(email);
		if (user != null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Map<Integer, String> getCountries() {
		List<CountryEntity> countries = countryRepository.findAll();
		Map<Integer, String> countryMap = new HashMap<>();
		for (CountryEntity country : countries) {
			countryMap.put(country.getCountryId(), country.getCountryName());
		}
		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		List<StateEntity> states = stateRepository.findByCountryId(countryId);
		Map<Integer, String> stateMap = new HashMap<>();
		for (StateEntity state : states) {
			stateMap.put(state.getStateId(), state.getStateName());
		}
		return stateMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		List<CityEntity> cities = cityRepository.findByStateId(stateId);
		Map<Integer, String> cityMap = new HashMap<>();
		for (CityEntity city : cities) {
			cityMap.put(city.getCityId(), city.getCityName());
		}
		return cityMap;
	}

	@Override
	public boolean registerUser(User user) {

		user.setUserAccStatus(AppConstants.LOCKED);
		user.setUserPwd(generateTempPwd());

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		UserEntity save = userRepository.save(userEntity);
		if (save.getUserId() != null) {
			return sendRegEmail(user);
		}

		return false;
	}

	private String generateTempPwd() {
		String tempPwd = null;
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 6;
		Random random = new Random();

		tempPwd = random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return tempPwd;
	}

	private boolean sendRegEmail(User user) {
		boolean emailSent = false;
		

		try {
			Map<String, String> messages = appProperties.getMessages();

			String subject = messages.get(AppConstants.REG_EMAIL_SUBJECT);

			String regEmailFileName = messages.get(AppConstants.REG_EMAIL_FILENAME);

			String body = this.readMailBody(regEmailFileName, user);

			emailSent = emailUtils.sendEmail(user.getUserEmail(), subject, body);
		} catch (Exception e) {
			throw new RegAppException(e.getMessage());
		}
		return emailSent;
	}

	public String readMailBody(String fileName, User user) {
		String finalMailBody = null;
		StringBuffer buffer = new StringBuffer();
		Path path = Paths.get(fileName);

		try (Stream<String> stream = Files.lines(path)) {
			stream.forEach(line -> {
				buffer.append(line);
			});
			String originalMailBody = buffer.toString();
			finalMailBody = originalMailBody.replace(AppConstants.FNAME, user.getUserFname());
			finalMailBody = finalMailBody.replace(AppConstants.EMAIL, user.getUserEmail());
			finalMailBody = finalMailBody.replace(AppConstants.TEMP_PWD, user.getUserPwd());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return finalMailBody;

	}
}
