package com.GasGuru.GasGuru.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="fualStationDetails")
@Setter @Getter @NoArgsConstructor
public class FualStationDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="stationId")
	private int stationId;
	@Column(name="stationName")
	@NotNull
	private String stationName;
	@Column(name ="vehicleCount")
	@NotNull
	private int vehicleCount;
	@Column(name="colourIndicator")
	private String colourIndicator;
}
