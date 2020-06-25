package com.photoapp.apiusers.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoapp.apiusers.users.models.LoginRequestModel;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthFilter extends UsernamePasswordAuthenticationFilter {

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, 
    HttpServletResponse res) throws AuthenticationException {
      try {
        LoginRequestModel creds = new ObjectMapper()
          .readValue(req.getInputStream(), LoginRequestModel.class);

        return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(
            creds.getEmail(),
            creds.getPassword(),
            new ArrayList<>())
        );
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
  }
  
  @Override
  protected void successfulAuthentication(
    HttpServletRequest req,
    HttpServletResponse res,
    FilterChain chain,
    Authentication auth
  ) throws IOException, ServletException {
    
  }

}