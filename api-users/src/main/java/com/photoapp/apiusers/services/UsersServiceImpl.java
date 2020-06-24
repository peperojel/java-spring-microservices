package com.photoapp.apiusers.services;

import java.util.UUID;

import com.photoapp.apiusers.data.UserEntity;
import com.photoapp.apiusers.data.UsersRepository;
import com.photoapp.apiusers.shared.UserDto;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
  
  UsersRepository userRepository;

  @Autowired
  public UsersServiceImpl(UsersRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  public UserDto createUser(UserDto userDetails) {
    userDetails.setUserId(UUID.randomUUID().toString());
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
    userEntity.setEncryptedPassword("test");
    userRepository.save(userEntity);

    UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
    return returnValue;
  }
}