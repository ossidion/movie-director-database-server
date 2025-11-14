package com.example.accessing_data_jpa.repositories;

import com.example.accessing_data_jpa.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String> {}
