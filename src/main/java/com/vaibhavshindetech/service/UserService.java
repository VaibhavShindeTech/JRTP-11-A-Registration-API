package com.vaibhavshindetech.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vaibhavshindetech.bindings.User;

@Service
public interface UserService {
	
	public boolean isEmailUnique(String email);

	public Map<Integer, String> getCountries();

	public Map<Integer, String> getStates(Integer countryId);

	public Map<Integer, String> getCities(Integer stateId);

	public boolean registerUser(User user);

}
