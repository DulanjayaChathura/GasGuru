package com.GasGuru.GasGuru.services;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private static final Logger logger = LogManager.getLogger(FualStationServices.class);

	public ResponseEntity addFualStation(FualStationDetailsModel fualStation) {
		try {
			FualStationDetails details = new FualStationDetails();
			int vehicleCount = fualStation.getVehicleCount();
			details.setStationName(fualStation.getStationName());
			details.setVehicleCount(vehicleCount);
			details.setLatitude(fualStation.getLatitude());
			details.setLongitude(fualStation.getLongitude());
			if (vehicleCount < 0) {
				throw new BadRequestException("vehicle count is invalid");
			} else if (vehicleCount <= 4) {
				details.setColourIndicator("Law");
			} else if (vehicleCount <= 7) {
				details.setColourIndicator("Medium");
			} else {
				details.setColourIndicator("High");
			}

			repo.save(details);
			return new ResponseEntity(new CommonResponse("Fual station details are saved"), HttpStatus.OK);

		} catch (BadRequestException e) {
			logger.error("error occured {} {} ", e.getMessage(), e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			logger.error("internal server error :: {}", e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity editFualStation(int stationId, FualStationDetailsModel fualStation) {
		try {
			FualStationDetails oldDetails = repo.findById(stationId).get();
			if (StringUtils.isEmpty(oldDetails)) {
				throw new BadRequestException("fual station dosen't exist");
			}
			FualStationDetails details = new FualStationDetails();
			details.setStationId(oldDetails.getStationId());
			int vehicleCount = fualStation.getVehicleCount();
			details.setStationName(fualStation.getStationName());
			details.setVehicleCount(vehicleCount);
			details.setLatitude(fualStation.getLatitude());
			details.setLongitude(fualStation.getLongitude());

			if (vehicleCount < 0) {
				throw new BadRequestException("vehicle count is invalid");
			} else if (vehicleCount <= 4) {
				details.setColourIndicator("Law");
			} else if (vehicleCount <= 7) {
				details.setColourIndicator("Medium");
			} else {
				details.setColourIndicator("High");
			}

			repo.save(details);
			return new ResponseEntity(new CommonResponse("Fual station details are updated"), HttpStatus.OK);

		} catch (BadRequestException e) {
			logger.error("error occured {} {} ", e.getMessage(), e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.BAD_REQUEST);

		}catch (NoSuchElementException e) {
			logger.error("station ID is invalid :: {}", e);
			return new ResponseEntity(new CommonResponse("station ID is invalid"), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			logger.error("internal server error :: {}", e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity searchFualStation(String stationName) {
		try {
			return new ResponseEntity(repo.search(stationName), HttpStatus.OK);

		} catch (BadRequestException e) {
			logger.error("error occured {} {} ", e.getMessage(), e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.BAD_REQUEST);

		}catch (NoSuchElementException e) {
			logger.error("station name  is invalid :: {}", e);
			return new ResponseEntity(new CommonResponse("station name is invalid"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("internal server error :: {}", e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity getAllFualStation() {
		try {
		return new ResponseEntity(repo.findAll(), HttpStatus.OK);
		}catch (Exception e) {
			logger.error("internal server error :: {}", e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
