package com.GasGuru.GasGuru.model;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CommonResponse {
	public CommonResponse(String status) {
		super();
		this.status = status;
	}

	String status;


}
