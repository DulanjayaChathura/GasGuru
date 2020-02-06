package com.GasGuru.GasGuru.model;

import javax.persistence.Column;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter @Getter
public class FualStationDetailsModel {
	
	private int stationId;
	private String stationName;
	private double latitude;
	private double longitude;
	private int vehicleCount;

}
