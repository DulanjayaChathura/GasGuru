package com.GasGuru.GasGuru.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="person")
@Getter @Setter 
public class Person{
	
	@Id
	@Column(name="username", unique = true, nullable = false)
	String username;
	@Column(name="fullname")
	String fullName;
	@Column(name="dateOfBirth")
	Date dateOfBirth;
	@Column(name="nic")
	String nic;
	@Column(name="address")
	String address;
	@Column(name="email")
	String email;
	@Column(name="telNo")
	String telNo;
	@Column(name="password")
	String password;
	@Lob
	@Column(name="photo")
	String photo;
	@Lob
	@Column(name="photoOfVehicle")
	String photoOfVehicle;
	@Column(name="typeOfVehicle")
	String typeOfVehicle;
	@Column(name="modleOfVehicle")
	String modleOfVehicle;
	@Column(name="colorOfVehicle")
	String colorOfVehicle;
	@Column(name ="userType")
	String userType;
	
	
	
	

}
