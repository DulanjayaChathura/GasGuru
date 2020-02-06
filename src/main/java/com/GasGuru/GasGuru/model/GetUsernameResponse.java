package com.GasGuru.GasGuru.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
public class GetUsernameResponse {
	String username;
	String fullname;
	public GetUsernameResponse(String username, String fullname) {
		super();
		this.username = username;
		this.fullname = fullname;
	}


}
