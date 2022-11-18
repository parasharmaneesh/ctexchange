package com.ct.ctexchange.user.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreateUserRequest {

	@NotBlank(message = "Please provide valid name")
	private String name;
	
	@Email(message = "Please provide valid email")
	private String email;
}
