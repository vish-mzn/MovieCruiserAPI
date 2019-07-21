package com.cts.moviecruiserserver.service;

import java.util.List;

import com.cts.moviecruiserserver.domain.Movie;
import com.cts.moviecruiserserver.exception.MovieAlreadyExistsException;
import com.cts.moviecruiserserver.exception.MovieNotFoundException;

public interface MovieService {
	
	boolean saveMovie(Movie movie) throws MovieAlreadyExistsException;
	
	Movie updateMovie(Movie movie) throws MovieNotFoundException;
	
	boolean deleteMovieById(int id) throws MovieNotFoundException;
	
	Movie getMovieById(int id) throws MovieNotFoundException;
	
	List<Movie> getMyMovies(String userId);	

}
