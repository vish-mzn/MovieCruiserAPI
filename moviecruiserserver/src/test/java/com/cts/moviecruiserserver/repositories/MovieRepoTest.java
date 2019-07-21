package com.cts.moviecruiserserver.repositories;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.moviecruiserserver.domain.Movie;
import com.cts.moviecruiserserver.repository.MovieRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class MovieRepoTest {
	
	@Autowired
	private transient MovieRepository repo;
	
	public void setRepo(final MovieRepository repo) {
		this.repo = repo;
	}
	
	@Test
	public void testSaveMovie() throws Exception {
		repo.save(new Movie(1, "superman", "good movie", "www.abc.com", "2015-03-23", "vish49"));
		final Movie movie = repo.getOne(1);
		assertThat(movie.getId()).isEqualTo(1);		
	}
	
	@Test
	public void testUpdateMovie() throws Exception {
		repo.save(new Movie(1, "superman", "good movie", "www.abc.com", "2015-03-23", "vish49"));
		final Movie movie = repo.getOne(1);
		assertEquals(movie.getTitle(), "superman");
		movie.setComments("hi");
		repo.save(movie);
		final Movie tempMovie = repo.getOne(1);
		assertEquals("hi", tempMovie.getComments());
		//repo.delete(tempMovie);
	}
	
	@Test
	public void testGetMovie() throws Exception {
		repo.save(new Movie(1, "superman", "good movie", "www.abc.com", "2015-03-23", "vish49"));
		final Movie movie = repo.getOne(1);
		assertEquals(movie.getTitle(), "superman");
	}
	
	@Test
	public void testGetMyMovies() throws Exception {
		repo.save(new Movie(1, "superman", "good movie", "www.abc.com", "2015-03-23", "vish49"));
		repo.save(new Movie(2, "batman", "good movie", "www.abc.com", "2015-03-23", "vish49"));
		final List<Movie> myMovies = repo.findByUserId("vish49");
		assertEquals(myMovies.size(), 2);
	}
	
	@Test
	public void testDeleteMovie() throws Exception {
		repo.save(new Movie(1, "superman", "good movie", "www.abc.com", "2015-03-23", "vish49"));
		final Movie movie = repo.getOne(1);
		assertEquals(movie.getTitle(), "superman");
		repo.delete(movie);
		assertEquals(Optional.empty(), repo.findById(1));
	}
	
}
