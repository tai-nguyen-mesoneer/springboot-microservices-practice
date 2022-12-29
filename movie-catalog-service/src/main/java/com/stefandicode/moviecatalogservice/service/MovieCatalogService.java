package com.stefandicode.moviecatalogservice.service;

import com.stefandicode.moviecatalogservice.model.Movie;
import com.stefandicode.moviecatalogservice.model.MovieCatalog;
import com.stefandicode.moviecatalogservice.model.UserRatings;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieCatalogService {
  private WebClient.Builder webClient;
  private UserRatingsService userRatingsService;
  private MoviesService moviesService;

  public MovieCatalogService(
      WebClient.Builder webClient,
      UserRatingsService userRatingsService,
      MoviesService moviesService) {
    this.webClient = webClient;
    this.userRatingsService = userRatingsService;
    this.moviesService = moviesService;
  }

  public List<MovieCatalog> getMovieCatalog(int userId) {
    UserRatings userRatings = this.userRatingsService.getUserRatings(userId);
    return userRatings.getMovieRatingList().stream()
        .map(
            movieRating -> {
              Movie movie = this.moviesService.getMovie(movieRating.getMovieId());
              return MovieCatalog.builder()
                  .name(movie.getName())
                  .desc(movie.getDesc())
                  .ratings(movieRating.getRatings())
                  .build();
            })
        .collect(Collectors.toList());
  }
}
