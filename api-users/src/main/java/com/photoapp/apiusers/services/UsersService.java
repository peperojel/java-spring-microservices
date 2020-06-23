package com.photoapp.apiusers.services;

import com.photoapp.apiusers.shared.UserDto;

public interface UsersService {
  UserDto createUser(UserDto userDetails);
}