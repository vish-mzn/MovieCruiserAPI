package com.cts.moviecruiserserver.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.moviecruiserserver.domain.Movie;
import com.cts.moviecruiserserver.exception.MovieAlreadyExistsException;
import com.cts.moviecruiserserver.exception.MovieNotFoundException;
import com.cts.moviecruiserserver.service.MovieService;

import io.jsonwebtoken.Jwts;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/movieservice")
public class MovieController {
	
	private MovieService movieService;
	
	@Autowired
	private MovieController(final MovieService movieService) {
		this.movieService = movieService;
	}
	
	// <-- Save Movie Method -->
	@PostMapping("/movie")
	public ResponseEntity<?> saveNewMovie(@RequestBody final Movie movie, HttpServletRequest request) 
	{
		final String authHeader = request.getHeader("Authorization");
		final String token = authHeader.substring(7);
		String userId = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody().getSubject();

		System.out.println(userId);
		ResponseEntity<?> responseEntity;
		try {
			movie.setUserId(userId);
			movieService.saveMovie(movie);
			responseEntity = new ResponseEntity<Movie>(movie, HttpStatus.CREATED);
		} catch (MovieAlreadyExistsException e) {
			responseEntity = new ResponseEntity<String>("{ \"message\": \"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
	
	// <-- Update Movie Method -->
	@PutMapping("/movie/{id}")
	public ResponseEntity<?> updateMovie(@PathVariable("id") final Integer id, @RequestBody Movie movie) {
		ResponseEntity<?> responseEntity;
		try {
			final Movie fetchedMovie = movieService.updateMovie(movie);
			responseEntity = new ResponseEntity<Movie>(fetchedMovie, HttpStatus.OK);
		} catch (MovieNotFoundException e) {
			responseEntity = new ResponseEntity<String>("{ \"message\": \"" + e.getMessage() + "\"}", HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	
	// <-- Delete Movie Method -->
	@DeleteMapping("/movie/{id}")
	public ResponseEntity<?> deleteMovieById(@PathVariable("id") final int id) {
		ResponseEntity<?> responseEntity;
		try {
			movieService.deleteMovieById(id);
			responseEntity = new ResponseEntity<String>("movie deleted successfully", HttpStatus.OK);
		} catch (MovieNotFoundException e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	
	@GetMapping("/movie/{id}")
	public ResponseEntity<?> fetchMovieById(@PathVariable("id") final int id) {
		ResponseEntity<?> responseEntity;
		Movie thisMovie = null;
		try {
			thisMovie = movieService.getMovieById(id);
			responseEntity = new ResponseEntity<Movie>(thisMovie, HttpStatus.OK);
		} catch (MovieNotFoundException e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	
	// fetching user watchlist
	@GetMapping("/movies")
	public ResponseEntity<?> getMyMovies(HttpServletRequest request) {
		final String authHeader = request.getHeader("Authorization");
		final String token = authHeader.substring(7);
		String userId = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody().getSubject();

		System.out.println(userId);
		List<Movie> movieList = movieService.getMyMovies(userId);
		if(movieList.isEmpty()) {
			return new ResponseEntity<String>("No movies existed!", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
	}
	
}
