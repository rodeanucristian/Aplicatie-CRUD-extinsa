package com.proiect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.proiect.model.Cladire;
import com.proiect.model.Usa;

public interface UsaRepository extends JpaRepository<Usa, Integer>{
	
	

}
