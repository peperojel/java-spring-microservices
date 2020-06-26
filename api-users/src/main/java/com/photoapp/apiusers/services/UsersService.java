package com.photoapp.apiusers.services;

import com.photoapp.apiusers.shared.UserDto;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
  UserDto createUser(UserDto userDetails);
  UserDto getUserDetailsByEmail(String email);
}