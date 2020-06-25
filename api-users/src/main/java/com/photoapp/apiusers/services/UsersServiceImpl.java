package com.photoapp.apiusers.services;

import java.util.UUID;

import com.photoapp.apiusers.data.UserEntity;
import com.photoapp.apiusers.data.UsersRepository;
import com.photoapp.apiusers.shared.UserDto;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
  
  UsersRepository userRepository;
  BCryptPasswordEncoder bCryptPasswordEnconder;

  @Autowired
  public UsersServiceImpl(UsersRepository userRepository,
    BCryptPasswordEncoder bCryptPasswordEnconder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEnconder = bCryptPasswordEnconder;
  }


  @Override
  public UserDto createUser(UserDto userDetails) {
    userDetails.setUserId(UUID.randomUUID().toString());
    userDetails.setEncryptedPassword(
      bCryptPasswordEnconder.encode(userDetails.getPassword())
    );
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
    userRepository.save(userEntity);

    UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
    return returnValue;
  }
}