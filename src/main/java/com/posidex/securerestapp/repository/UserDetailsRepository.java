package com.posidex.securerestapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.posidex.securerestapp.entity.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {}
