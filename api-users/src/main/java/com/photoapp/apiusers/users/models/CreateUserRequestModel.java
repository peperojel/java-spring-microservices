package com.photoapp.apiusers.users.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {

  @NotNull(message="First name must be provided.")
  @Size(min=2, message="First name must be greater than 1 character")
  private String firstName;

  @NotNull(message="Last name must be provided.")
  @Size(min=2, message="Last name must be greater than 1 character")
  private String lastName;

  @NotNull(message="Email must be provided.")
  @Email
  private String email;

  @NotNull(message="Password name must be provided.")
  @Size(min=2, max=16, message="Password must be greater than 1 character and less than 16 characters")
  private String password;

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }
}