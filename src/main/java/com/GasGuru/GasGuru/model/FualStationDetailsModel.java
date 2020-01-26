package com.GasGuru.GasGuru.model;

import javax.persistence.Column;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter @Getter
public class FualStationDetailsModel {
	
	private String stationName;
	private int vehicleCount;

}
