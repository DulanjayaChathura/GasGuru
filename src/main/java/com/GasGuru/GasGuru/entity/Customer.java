package com.GasGuru.GasGuru.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "customer")
@Getter @Setter
public class Customer {
	 
	@Id
	@Column(name="username", unique = true, nullable = false)
	private String username;
	@Column(name="fullName")
	private String fullName;
	@Column(name="dateOfBirth")
    private String dateOfBirth;
	@Column(name="nic")
    private String nic;
	@Column(name="address")
    private String address;
	@Column(name="email")
    private String email;
	@Column(name="telNo")
    private String telNo;
    @Column(name="password")
    private String password;
    @Lob
    @Column(name="photo")
    private String photo;
    @Lob
    @Column(name="photoOfVehicle")
	private String photoOfVehicle;
    @Column(name="typeOfVehicle")
	private String typeOfVehicle;
    @Column(name="modleOfVehicle")
	private String modleOfVehicle;
    @Column(name="colorOfVehicle")
	private String colorOfVehicle;
}
