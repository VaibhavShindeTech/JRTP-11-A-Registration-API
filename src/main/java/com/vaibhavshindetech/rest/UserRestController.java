package com.vaibhavshindetech.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vaibhavshindetech.bindings.User;
import com.vaibhavshindetech.constans.AppConstants;
import com.vaibhavshindetech.service.UserService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserRestController {
	@Autowired
	private UserService userService;

	@GetMapping(value = "/email/{email}")
	public String isEmailUnique(@PathVariable String email) {
		boolean emailUnique = userService.isEmailUnique(email);
		if (emailUnique)
			return AppConstants.UNIQUE;
		else
			return AppConstants.DUPLICATE;
	}

	@GetMapping(value = "/countries")
	public Map<Integer, String> getCountries() {
		return userService.getCountries();
	}

	@GetMapping(value = "/states/{countryId}")
	public Map<Integer, String> getStates(@PathVariable Integer countryId) {
		return userService.getStates(countryId);
	}

	@GetMapping(value = "/cities/{stateId}")
	public Map<Integer, String> getCities(@PathVariable Integer stateId) {
		return userService.getCities(stateId);
	}

	@PostMapping(value = "/register")
	public String registerUser(@RequestBody User user) {
		boolean registerUser = userService.registerUser(user);
		if (registerUser) {
			return AppConstants.SUCCESS;
		} else {
			return AppConstants.FAIL;
		}
	}
}
