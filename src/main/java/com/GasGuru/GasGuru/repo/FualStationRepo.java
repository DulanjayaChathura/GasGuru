package com.GasGuru.GasGuru.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.GasGuru.GasGuru.entity.FualStationDetails;



@Repository
public interface FualStationRepo extends JpaRepository<FualStationDetails, Integer>{
	
//	@Query("SELECT station FROM FualStationDetails station WHERE station.stationName like :stationName% ")
//	List<FualStationDetails> search(@Param("stationName") String stationName);
}
