package com.vaibhavshindetech.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vaibhavshindetech.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Serializable> {

	public UserEntity findByUserEmail(String email);
	
}
