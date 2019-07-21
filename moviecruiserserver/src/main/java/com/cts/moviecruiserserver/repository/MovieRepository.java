package com.cts.moviecruiserserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.moviecruiserserver.domain.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer>{
	
	List<Movie> findByUserId(String userId);
}
