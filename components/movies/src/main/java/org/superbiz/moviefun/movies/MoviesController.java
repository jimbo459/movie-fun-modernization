package org.superbiz.moviefun.movies;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MoviesController {

	MoviesRepository moviesRepository = new MoviesRepository();

	@PostMapping("/")
	public void addMovie(@RequestBody Movie movie) {
		moviesRepository.addMovie(movie);
	}

	@DeleteMapping("/{id}")
	public void deleteMovieId(@PathVariable long id) {
		moviesRepository.deleteMovieId(id);
	}

	@GetMapping("/count")
	public List<Movie> find(
			@RequestParam(name="field", required = false) String field,
			@RequestParam(name="searchTerm", required = false) String searchTerm,
			@RequestParam(name="firstResult", required = false) int firstResult,
			@RequestParam(name="maxResults", required = false) int maxResults) {
		if (field != null) {
			return moviesRepository.findRange(field, searchTerm, firstResult, maxResults);
		} else if (maxResults > 0){
			return moviesRepository.findAll(firstResult, maxResults);
		} else {
			return moviesRepository.getMovies();
		}
	}

	@GetMapping("/")
	public int count(
			@RequestParam(name="field", required = false) String field,
			@RequestParam(name="searchTerm", required = false) String searchTerm) {
			if (field != null && searchTerm != null) {
				return moviesRepository.count(field, searchTerm);
			} else {
				return moviesRepository.countAll();
			}
	}



}
