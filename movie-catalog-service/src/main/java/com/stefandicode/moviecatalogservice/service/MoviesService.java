package com.stefandicode.moviecatalogservice.service;

import com.stefandicode.moviecatalogservice.model.Movie;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MoviesService {
  private WebClient.Builder webClient;

  public MoviesService(WebClient.Builder webClient) {
    this.webClient = webClient;
  }

  @CircuitBreaker(name = "getMovieCB", fallbackMethod = "getMovieFallback")
  @Bulkhead(name = "getMovieBH", fallbackMethod = "getMovieFallback", type = Bulkhead.Type.THREADPOOL)
  public Movie getMovie(int movieId) {
    return this.webClient
        .build()
        .get()
        .uri("http://movie-info-service/movies/" + movieId)
        .retrieve()
        .bodyToMono(Movie.class)
        .block();
  }

  public Movie getMovieFallback(int movieId, Exception e) {
    return Movie.builder().movieId(movieId).name("Not found.").desc("Not found.").build();
  }
}
