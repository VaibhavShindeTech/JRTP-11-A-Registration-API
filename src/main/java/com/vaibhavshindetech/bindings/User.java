package com.vaibhavshindetech.bindings;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class User {

	private Integer userId;

	private String userFname;

	private String userLname;

	private String userEmail;

	private Long userPhno;
	
	private LocalDate userDob; //2000-05-21

	private String userGender;

	private Integer userCountry;

	private Integer userState;

	private Integer userCity;

	private String userPwd;

	private String userAccStatus;

	private LocalDate userCreatedDate;

	private LocalDate userUpdatedDate;
}
