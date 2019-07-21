package com.cts.moviecruiserserver.controllers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cts.moviecruiserserver.controller.MovieController;
import com.cts.moviecruiserserver.domain.Movie;
import com.cts.moviecruiserserver.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTest {
	
	@Autowired
	private transient MockMvc mvc; 

	@MockBean
	private transient MovieService service;
	
	@InjectMocks
	private MovieController movieController;
	
	private transient Movie movie;
	
	String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXNoNDkiLCJpYXQiOjE1NTYxMDU1Nzh9.q-g3xvPUt7KdikiQrUZReIagJ8TfrnzRM3GB7HqHrho";
	
	static List<Movie> movies;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		movie = new Movie(1, "superman", "good movie", "www.abc.com", "2015-03-23", "vish49");
		mvc = MockMvcBuilders.standaloneSetup(movieController).build(); 		
		
		movies = new ArrayList<Movie>();
		movie = new Movie(1, "superman", "good movie", "www.abc.com", "2015-03-23", "vish49");
		movies.add(movie);
		movie = new Movie(2, "batman", "good movie", "www.abc.com", "2015-03-23", "vish49");
		movies.add(movie);
	}
	
	@Test
	public void testSaveNewMovieSuccess() throws Exception {

		when(service.saveMovie(movie)).thenReturn(true);
		mvc.perform(post("/api/movieservice/movie").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(movie))).andExpect(status().isCreated());
		verify(service, times(1)).saveMovie(Mockito.any(Movie.class));
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void testUpdateMovieSuccess() throws Exception {
		movie.setComments("not so good movie");
		when(service.updateMovie(movie)).thenReturn(movies.get(0));
		mvc.perform(put("/api/movieservice/movie/{id}", 1).contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(movie))).andExpect(status().isOk());
		verify(service, times(1)).updateMovie(Mockito.any(Movie.class));
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void testDeleteMovieById() throws Exception {
		when(service.deleteMovieById(1)).thenReturn(true);
		mvc.perform(delete("/api/movieservice/movie/{id}", 1)).andExpect(status().isOk());
		verify(service, times(1)).deleteMovieById(1);
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void testFetchMovieById() throws Exception {
		when(service.getMovieById(1)).thenReturn(movies.get(0));
		mvc.perform(get("/api/movieservice/movie/{id}", 1)).andExpect(status().isOk());
		verify(service, times(1)).getMovieById(1);
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void testGetMyMovies() throws Exception {
		
		when(service.getMyMovies("vish49")).thenReturn(movies);
		mvc.perform(get("/api/movieservice/movies").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		verify(service, times(1)).getMyMovies("vish49");
		verifyNoMoreInteractions(service);
	}
	
	private static String jsonToString(final Object obj) throws JsonProcessingException {
		String result;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			result = jsonContent;
		} catch (JsonProcessingException e) {
			result = "Json processing error";
		}
		return result;
	}
	
}
