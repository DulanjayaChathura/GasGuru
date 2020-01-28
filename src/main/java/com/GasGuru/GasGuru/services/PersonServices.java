package com.GasGuru.GasGuru.services;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
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
	private String dateOfBirthUpdate;
	private Date dateOfBirth;
	private String address;
	private String colourOfVehicle;
	private String fullName;
	private String modelOfVehicle;
	private String password;
	private String photo;
	private String photoOfVehicle;
	private String typeOfVehicle;
	private Person newPerson;
	private Person person;

	private static final Logger logger = LogManager.getLogger(PersonServices.class);

	public ResponseEntity login(Login login) {
		Person person = null;
		try {
			person = repo.findById(login.getUsername()).get();
			if (!person.getPassword().equals(login.getPassword())) {
				throw new BadRequestException("Username and password are not match");
			}
			return new ResponseEntity(new CommonResponse("login is successful"), HttpStatus.OK);

		} catch (BadRequestException e) {
			logger.error("error occured {} {} ", e.getMessage(), e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (NoSuchElementException e) {
			logger.error("username is invalid :: {}", e);
			return new ResponseEntity(new CommonResponse("username is invalid"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("internal server error :: {}", e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity editDetails(String username, EditDetails editDetails) {
		try {
			person = repo.findById(username).get();

			newPerson = new Person();

			email = editDetails.getEmail();
			phoneNo = editDetails.getTelNo();
			nic = editDetails.getNic();
			dateOfBirthUpdate = editDetails.getDateOfBirth();

			address = editDetails.getAddress();
			colourOfVehicle = editDetails.getColorOfVehicle();
			fullName = editDetails.getFullName();
			modelOfVehicle = editDetails.getModleOfVehicle();
			password = editDetails.getPassword();
			photo = editDetails.getPhoto();
			photoOfVehicle = editDetails.getPhotoOfVehicle();
			typeOfVehicle = editDetails.getTypeOfVehicle();

			if (!StringUtils.isEmpty(nic)) {
				validator.isNicValid(nic);
				newPerson.setNic(nic);
			} else {
				newPerson.setNic(person.getNic());
			}
			if (!StringUtils.isEmpty(email)) {
				validator.isValidEmail(email);
				newPerson.setEmail(email);
			} else {
				newPerson.setEmail(person.getEmail());
			}
			if (!StringUtils.isEmpty(phoneNo)) {
				validator.isValidPhoneNo(phoneNo);
				newPerson.setTelNo(phoneNo);
			} else {
				newPerson.setTelNo(person.getTelNo());
			}
			if (!StringUtils.isEmpty(dateOfBirthUpdate)) {
				dateOfBirth = converter.convert(dateOfBirthUpdate);
				validator.isValidDate(dateOfBirth);
				newPerson.setDateOfBirth(dateOfBirth);
			} else {
				newPerson.setDateOfBirth(person.getDateOfBirth());
			}

			newPerson.setUsername(username);
			newPerson.setAdminFlag(person.isAdminFlag());

			if (!StringUtils.isEmpty(address)) {
				newPerson.setAddress(address);
			} else {
				newPerson.setAddress(person.getAddress());
			}
			if (!StringUtils.isEmpty(colourOfVehicle)) {
				newPerson.setColorOfVehicle(colourOfVehicle);
			} else {
				newPerson.setColorOfVehicle(person.getColorOfVehicle());
			}
			if (!StringUtils.isEmpty(fullName)) {
				newPerson.setFullName(fullName);
			} else {
				newPerson.setFullName(person.getFullName());
			}
			if (!StringUtils.isEmpty(modelOfVehicle)) {
				newPerson.setModleOfVehicle(modelOfVehicle);
			} else {
				newPerson.setModleOfVehicle(person.getModleOfVehicle());
			}
			if (!StringUtils.isEmpty(password)) {
				newPerson.setPassword(password);
			} else {
				newPerson.setPassword(person.getPassword());
			}
			if (!StringUtils.isEmpty(photo)) {
				newPerson.setPhoto(photo);
			} else {
				newPerson.setPhoto(person.getPhoto());
			}
			if (!StringUtils.isEmpty(photoOfVehicle)) {
				newPerson.setPhotoOfVehicle(photoOfVehicle);
			} else {
				newPerson.setPhotoOfVehicle(person.getPhotoOfVehicle());
			}
			if (!StringUtils.isEmpty(typeOfVehicle)) {
				newPerson.setTypeOfVehicle(typeOfVehicle);
			} else {
				newPerson.setTypeOfVehicle(person.getTypeOfVehicle());
			}

			repo.save(newPerson);

			return new ResponseEntity(new CommonResponse("updating is successful"), HttpStatus.OK);

		} catch (BadRequestException e) {
			logger.error("error occured {} {} ", e.getMessage(), e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (NoSuchElementException e) {
			logger.error("username is invalid :: {}", e);
			return new ResponseEntity(new CommonResponse("username is invalid"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("internal server error :: {}", e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity register(RegisterDetails register) {

		try {
			boolean personExists = repo.findById(register.getUsername()).isPresent();
			if (personExists) {
				throw new BadRequestException("username already exists");
			}
			email = register.getEmail();
			phoneNo = register.getTelNo();
			nic = register.getNic();
			dateOfBirth = converter.convert(register.getDateOfBirth());
			validator.isNicValid(nic);
			validator.isValidEmail(email);
			validator.isValidPhoneNo(phoneNo);
			validator.isValidDate(dateOfBirth);

			Person newPerson = new Person();
			newPerson.setUsername(register.getUsername());
			newPerson.setAddress(register.getAddress());
			newPerson.setAdminFlag(register.isAdminFlag());
			newPerson.setColorOfVehicle(register.getColorOfVehicle());
			newPerson.setDateOfBirth(dateOfBirth);
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

		} catch (BadRequestException e) {
			logger.error("error occured {} {} ", e.getMessage(), e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			logger.error("internal server error :: {}", e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
