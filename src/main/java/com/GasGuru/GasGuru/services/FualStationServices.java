package com.GasGuru.GasGuru.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.GasGuru.GasGuru.Exception.BadRequestException;
import com.GasGuru.GasGuru.entity.FualStationDetails;
import com.GasGuru.GasGuru.model.CommonResponse;
import com.GasGuru.GasGuru.model.FualStationDetailsModel;
import com.GasGuru.GasGuru.repo.FualStationRepo;


@Service
public class FualStationServices {
	
	@Autowired
	private FualStationRepo repo;
	
	
	public ResponseEntity addFualStation(FualStationDetailsModel fualStation) {
		FualStationDetails details= new FualStationDetails();
		int vehicleCount= fualStation.getVehicleCount();
		details.setStationName(fualStation.getStationName());
		details.setVehicleCount(vehicleCount);
		
		if(vehicleCount<0) {throw new BadRequestException("vehicle count is invalid");}
		else if(vehicleCount<=4) {details.setColourIndicator("Law");}
		else if(vehicleCount<=7) {details.setColourIndicator("Medium");}
		else {details.setColourIndicator("High");}
		
		repo.save(details);
		return new ResponseEntity(new CommonResponse("Fual station details are saved"), HttpStatus.OK);
		
	}
	
	public ResponseEntity editFualStation(int stationId, FualStationDetailsModel fualStation) {
		FualStationDetails oldDetails= repo.findById(stationId).get();
		if(StringUtils.isEmpty(oldDetails)) {throw new BadRequestException("fual station dosen't exist");}
		FualStationDetails details= new FualStationDetails();
		details.setStationId(oldDetails.getStationId());
		int vehicleCount= fualStation.getVehicleCount();
		details.setStationName(fualStation.getStationName());
		details.setVehicleCount(vehicleCount);
		
		if(vehicleCount<0) {throw new BadRequestException("vehicle count is invalid");}
		else if(vehicleCount<=4) {details.setColourIndicator("Law");}
		else if(vehicleCount<=7) {details.setColourIndicator("Medium");}
		else {details.setColourIndicator("High");}
		
		repo.save(details);
		return new ResponseEntity(new CommonResponse("Fual station details are updated"), HttpStatus.OK);
		
		
	}
	
	public ResponseEntity searchFualStation(String stationName) {
		return new ResponseEntity(repo.search(stationName),HttpStatus.OK);
	}

}
