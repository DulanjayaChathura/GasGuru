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
import com.GasGuru.GasGuru.repo.PersonRepo;

@Service
public class PersonServices {
	
	@Autowired
	private PersonRepo repo;
	
	public ResponseEntity login(Login login) {
		Optional<Person> person=repo.findById(login.getUsername());
		if(person.get().getPassword().equals(login.getPassword())){
			throw new BadRequestException("Username and password are not match");
		}
		return new ResponseEntity(person, HttpStatus.OK);
	}
	
	public ResponseEntity editDetails(String username, EditDetails editDetails){
		Person newPerson= new Person();
		Person person = repo.findById(username).get();
		if(StringUtils.isEmpty(person)) {throw new BadRequestException("username dosen't exists");}
		newPerson.setUsername(username);
		newPerson.setAddress(editDetails.getAddress());
		newPerson.setAdminFlag(person.isAdminFlag());
		newPerson.setColorOfVehicle(editDetails.getColorOfVehicle());
		newPerson.setDateOfBirth(editDetails.getDateOfBirth());
		newPerson.setEmail(editDetails.getEmail());
		newPerson.setFullName(editDetails.getFullName());
		newPerson.setModleOfVehicle(editDetails.getModleOfVehicle());
		newPerson.setNic(editDetails.getNic());
		newPerson.setPassword(editDetails.getPassword());
		newPerson.setPhoto(editDetails.getPhoto());
		newPerson.setPhotoOfVehicle(editDetails.getPhotoOfVehicle());
		newPerson.setTelNo(editDetails.getTelNo());
		newPerson.setTypeOfVehicle(editDetails.getTypeOfVehicle());
		
		repo.save(newPerson);
		
		return new ResponseEntity(new CommonResponse("updating is successful"),HttpStatus.OK);
	}

}
