package com.GasGuru.GasGuru.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter @Getter @NoArgsConstructor
public class RegisterDetails {
	

	private String username;
	private String fullName;
	private String dateOfBirth;
	private String nic;
	private String address;
	private String email;
	private String telNo;
	private String password;
	private String photo;
	private String photoOfVehicle;
	private String typeOfVehicle;
	private String modleOfVehicle;
	private String colorOfVehicle;
	private boolean adminFlag;

}
