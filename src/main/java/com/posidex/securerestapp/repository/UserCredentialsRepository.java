package com.posidex.securerestapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.posidex.securerestapp.entity.UserCredentials;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {}