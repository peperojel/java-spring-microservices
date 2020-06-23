package com.photoapp.apiusers.services;

import java.util.UUID;

import com.photoapp.apiusers.shared.UserDto;

public class UsersServiceImpl implements UsersService {

  @Override
  public UserDto createUser(UserDto userDetails) {
    //TODO
    userDetails.setUserId(UUID.randomUUID().toString());
    return null;
  }
}