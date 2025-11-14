package com.example.accessing_data_jpa.repositories;

import com.example.accessing_data_jpa.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository, an interface which has methods for database access. Spring generates methods such as findById at runtime using proxies, Reflection!

// I inject this repository in @Serivces and able to call the required methods (CRUD operations). JPA & Hibernate translate these method calls (findById, save, findAll) into SQL queries!

// <Movie, String> tells Spring Data:
//  - This works with the Movie @Entity
//  - String, the type of primary key of the @Entity.

public interface MovieRepository extends JpaRepository<Movie, String> {}
