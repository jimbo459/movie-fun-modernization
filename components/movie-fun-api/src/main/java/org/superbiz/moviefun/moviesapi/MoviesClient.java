package org.superbiz.moviefun.moviesapi;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;


public class MoviesClient {

	private RestOperations restOperations;
	private String moviesUrl;

	ParameterizedTypeReference<List<MovieInfo>> typeRef = new ParameterizedTypeReference<List<MovieInfo>>() {};

	public MoviesClient(String moviesUrl,RestOperations restOperations) {
		this.restOperations = restOperations;
		this.moviesUrl = moviesUrl;
	}

	public String url(String path) {
		return moviesUrl + path;
	}

	public void addMovie(MovieInfo movie) {
		restOperations.postForEntity(url("/movies"), movie, MovieInfo.class);
	}

	public void deleteMovieId(long id) {
		restOperations.delete("/movies/" + Long.toString(id));
	}

	public List<MovieInfo> findAll(int firstResult, int maxResults) {
		UriComponentsBuilder url = UriComponentsBuilder.fromHttpUrl(url("/movies"))
				.queryParam("firstResult",firstResult)
				.queryParam("maxResults",maxResults);
			return restOperations.exchange(url.toUriString(), GET,null, typeRef).getBody();
	}

	public List<MovieInfo> findRange(String field, String searchTerm, int firstResult, int maxResults) {
		UriComponentsBuilder url = UriComponentsBuilder.fromHttpUrl(url("/movies"))
				.queryParam("field",field)
				.queryParam("searchTerm",searchTerm)
				.queryParam("firstResult", firstResult)
				.queryParam("maxResults", maxResults);
		return restOperations.exchange(url.toUriString(), GET,null, typeRef).getBody();
	}

	public int count(String field, String searchTerm) {
		UriComponentsBuilder url = UriComponentsBuilder.fromHttpUrl(url("/count"))
				.queryParam("field",field)
				.queryParam("searchTerm",searchTerm);
		return restOperations.getForObject(url.toUriString(), Integer.class);
	}

	public int countAll() {
		return restOperations.getForObject(url("/count"), Integer.class);
	}

	public List<MovieInfo> getMovies() {
		return restOperations.exchange(url("/movies"), GET,null, typeRef).getBody();
	}
}

