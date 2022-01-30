package com.vaibhavshindetech.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vaibhavshindetech.entities.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity, Serializable> {

}
