package com.photoapp.apiusers.users.controllers;

import javax.validation.Valid;

import com.photoapp.apiusers.services.UsersService;
import com.photoapp.apiusers.shared.UserDto;
import com.photoapp.apiusers.users.models.CreateUserRequestModel;
import com.photoapp.apiusers.users.models.CreateUserResponseModel;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/users")
public class UsersController {
  
  @Autowired
  private Environment env;

  @Autowired
  UsersService userService;

  @Value("${message:Hello default}")
  private String message;

  @GetMapping("/status/check")
  public String status() {
    return "Working on port " + env.getProperty("local.server.port");
  }

  @GetMapping("/status/message")
  public String getMessage() {
    return message;
  }

  @PostMapping(
    consumes= {
      MediaType.APPLICATION_XML_VALUE,
      MediaType.APPLICATION_JSON_VALUE
    },
    produces = {
      MediaType.APPLICATION_XML_VALUE,
      MediaType.APPLICATION_JSON_VALUE
    }
  )
  public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    UserDto userDto = modelMapper.map(userDetails, UserDto.class);
    
    userService.createUser(userDto);
    
    CreateUserResponseModel returnValue = modelMapper.map(userDto, CreateUserResponseModel.class);

    return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
  }

}