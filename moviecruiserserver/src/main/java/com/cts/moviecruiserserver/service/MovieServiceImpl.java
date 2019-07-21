package com.cts.moviecruiserserver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.moviecruiserserver.domain.Movie;
import com.cts.moviecruiserserver.exception.MovieAlreadyExistsException;
import com.cts.moviecruiserserver.exception.MovieNotFoundException;
import com.cts.moviecruiserserver.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService{

	private final MovieRepository movieRepo;
	
	@Autowired
	public MovieServiceImpl(MovieRepository movieRepo) {
		super();
		this.movieRepo = movieRepo;
	}
	
	// <-- Save Movie Method -->
	@Override
	public boolean saveMovie(final Movie movie) throws MovieAlreadyExistsException {
		final Optional<Movie> object = movieRepo.findById(movie.getId());
		if(object.isPresent()) {
			throw new MovieAlreadyExistsException("Could Not save Movie, Movie already exists");
		}
		movieRepo.save(movie);
		return true;
	}

	// <-- Update Movie Method -->
	@Override
	public Movie updateMovie(final Movie movie) throws MovieNotFoundException {
		
		final Movie updateMovie = movieRepo.findById(movie.getId()).orElse(null);
		if(updateMovie == null) {
			throw new MovieNotFoundException("Couldn't update movie. Movie not found!");
		}
		updateMovie.setComments(movie.getComments());
		movieRepo.save(updateMovie);
		return updateMovie;
	}
	
	// <-- Delete Movie Method -->
	@Override
	public boolean deleteMovieById(final int id) throws MovieNotFoundException {
		
		final Movie movie = movieRepo.findById(id).orElse(null);
		if(movie == null) {
			throw new MovieNotFoundException("Could Not delete, Movie not found!");
		}
		movieRepo.delete(movie);
		return true;
	}

	// this return the movie having the passed id.
	@Override
	public Movie getMovieById(final int id) throws MovieNotFoundException {
		final Movie movie = movieRepo.findById(id).get();
		if(movie == null) {
			throw new MovieNotFoundException("Movie not found!");
		}
		return movie;
	}

	// returns user watchlist
	@Override
	public List<Movie> getMyMovies(String userId) {
		return movieRepo.findByUserId(userId);
	}

}
