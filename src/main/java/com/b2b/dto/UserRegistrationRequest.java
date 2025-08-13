package com.b2b.dto;

import com.b2b.entity.User.Role;

import lombok.*;

@Data
public class UserRegistrationRequest {
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String businessName;
    private String address;
	public Role getRole() {
		// TODO Auto-generated method stub
		return null;
	}
}

