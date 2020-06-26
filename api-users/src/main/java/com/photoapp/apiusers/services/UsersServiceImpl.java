package com.photoapp.apiusers.services;

import java.util.ArrayList;
import java.util.UUID;

import com.photoapp.apiusers.data.UserEntity;
import com.photoapp.apiusers.data.UsersRepository;
import com.photoapp.apiusers.shared.UserDto;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

  UsersRepository userRepository;
  BCryptPasswordEncoder bCryptPasswordEnconder;

  @Autowired
  public UsersServiceImpl(UsersRepository userRepository, BCryptPasswordEncoder bCryptPasswordEnconder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEnconder = bCryptPasswordEnconder;
  }

  @Override
  public UserDto createUser(UserDto userDetails) {
    userDetails.setUserId(UUID.randomUUID().toString());
    userDetails.setEncryptedPassword(bCryptPasswordEnconder.encode(userDetails.getPassword()));
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
    userRepository.save(userEntity);

    UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
    return returnValue;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByEmail(email);
    
    if(userEntity == null) throw new UsernameNotFoundException(email);
    
    return new User(
      userEntity.getEmail(),
      userEntity.getEncryptedPassword(),
      true, true, true, true, new ArrayList<>()
    );
  }

  public UserDto getUserDetailsByEmail(String email) {
    UserEntity userEntity = userRepository.findByEmail(email);
    if(userEntity == null) throw new UsernameNotFoundException(email);

    return new ModelMapper().map(userEntity, UserDto.class);
  }
}