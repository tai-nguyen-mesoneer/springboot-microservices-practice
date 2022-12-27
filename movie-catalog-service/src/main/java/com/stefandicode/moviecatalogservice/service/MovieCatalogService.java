package com.stefandicode.moviecatalogservice.service;

import com.stefandicode.moviecatalogservice.model.Movie;
import com.stefandicode.moviecatalogservice.model.MovieCatalog;
import com.stefandicode.moviecatalogservice.model.UserRatings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieCatalogService {
  @Autowired private WebClient.Builder webClient;

  public MovieCatalogService(WebClient.Builder webClient) {
    this.webClient = webClient;
  }

  public List<MovieCatalog> getMovieCatalog(int userId) {
    UserRatings userRatings =
        this.webClient.build()
            .get()
            .uri("http://movie-ratings-service/ratings/users/" + userId)
            .retrieve()
            .bodyToMono(UserRatings.class)
            .block();
    return userRatings.getMovieRatingList().stream()
        .map(
            movieRating -> {
              Movie movie =
                  this.webClient.build()
                      .get()
                      .uri("http://movie-info-service/movies/" + movieRating.getMovieId())
                      .retrieve()
                      .bodyToMono(Movie.class)
                      .block();
              return MovieCatalog.builder()
                  .name(movie.getName())
                  .desc(movie.getDesc())
                  .ratings(movieRating.getRatings())
                  .build();
            })
        .collect(Collectors.toList());
  }
}
