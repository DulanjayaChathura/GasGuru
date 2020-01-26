package com.GasGuru.GasGuru.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GasGuru.GasGuru.entity.Person;



@Repository
public interface PersonRepo extends JpaRepository<Person, String> {

}
