package com.GasGuru.GasGuru.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.GasGuru.GasGuru.Exception.BadRequestException;
import com.GasGuru.GasGuru.entity.Person;
import com.GasGuru.GasGuru.model.CommonResponse;
import com.GasGuru.GasGuru.model.EditDetails;
import com.GasGuru.GasGuru.model.Login;
import com.GasGuru.GasGuru.model.RegisterDetails;
import com.GasGuru.GasGuru.repo.PersonRepo;
import com.GasGuru.GasGuru.util.StringToDateConverter;
import com.GasGuru.GasGuru.util.ValidatePersonDetails;

@Service
public class PersonServices {

	@Autowired
	private PersonRepo repo;

	@Autowired
	private StringToDateConverter converter;

	@Autowired
	private ValidatePersonDetails validator;

	private String email;
	private String phoneNo;
	private String nic;
	private String dateOfBirth;

	public ResponseEntity login(Login login) {
		Optional<Person> person = repo.findById(login.getUsername());
		if (person.get().getPassword().equals(login.getPassword())) {
			throw new BadRequestException("Username and password are not match");
		}
		return new ResponseEntity(person, HttpStatus.OK);
	}

	public ResponseEntity editDetails(String username, EditDetails editDetails) {
		email = editDetails.getEmail();
		phoneNo = editDetails.getTelNo();
		nic = editDetails.getNic();
		validator.isNicValid(nic);
		validator.isValidEmail(email);
		validator.isValidPhoneNo(phoneNo);

		Person newPerson = new Person();

		Person person = repo.findById(username).get();
		if (StringUtils.isEmpty(person)) {
			throw new BadRequestException("username dosen't exists");
		}
		newPerson.setUsername(username);
		newPerson.setAddress(editDetails.getAddress());
		newPerson.setAdminFlag(person.isAdminFlag());
		newPerson.setColorOfVehicle(editDetails.getColorOfVehicle());
		newPerson.setDateOfBirth(converter.convert(editDetails.getDateOfBirth()));
		newPerson.setEmail(email);
		newPerson.setFullName(editDetails.getFullName());
		newPerson.setModleOfVehicle(editDetails.getModleOfVehicle());
		newPerson.setNic(nic);
		newPerson.setPassword(editDetails.getPassword());
		newPerson.setPhoto(editDetails.getPhoto());
		newPerson.setPhotoOfVehicle(editDetails.getPhotoOfVehicle());
		newPerson.setTelNo(phoneNo);
		newPerson.setTypeOfVehicle(editDetails.getTypeOfVehicle());

		repo.save(newPerson);

		return new ResponseEntity(new CommonResponse("updating is successful"), HttpStatus.OK);
	}

	public ResponseEntity register(RegisterDetails register) {

		email = register.getEmail();
		phoneNo = register.getTelNo();
		nic = register.getNic();
		validator.isNicValid(nic);
		validator.isValidEmail(email);
		validator.isValidPhoneNo(phoneNo);

		Person newPerson = new Person();
		newPerson.setUsername(register.getUsername());
		newPerson.setAddress(register.getAddress());
		newPerson.setAdminFlag(register.isAdminFlag());
		newPerson.setColorOfVehicle(register.getColorOfVehicle());
		newPerson.setDateOfBirth(converter.convert(register.getDateOfBirth()));
		newPerson.setEmail(register.getEmail());
		newPerson.setFullName(register.getFullName());
		newPerson.setModleOfVehicle(register.getModleOfVehicle());
		newPerson.setNic(register.getNic());
		newPerson.setPassword(register.getPassword());
		newPerson.setPhoto(register.getPhoto());
		newPerson.setPhotoOfVehicle(register.getPhotoOfVehicle());
		newPerson.setTelNo(register.getTelNo());
		newPerson.setTypeOfVehicle(register.getTypeOfVehicle());

		repo.save(newPerson);

		return new ResponseEntity(new CommonResponse("Registration is successful"), HttpStatus.OK);
	}

}
