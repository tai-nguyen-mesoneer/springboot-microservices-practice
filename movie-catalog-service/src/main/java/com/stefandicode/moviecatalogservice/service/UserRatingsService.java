package com.stefandicode.moviecatalogservice.service;

import com.stefandicode.moviecatalogservice.model.MovieRating;
import com.stefandicode.moviecatalogservice.model.UserRatings;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@Service
public class UserRatingsService {
  private WebClient.Builder webClient;

  public UserRatingsService(WebClient.Builder webClient) {
    this.webClient = webClient;
  }

  @CircuitBreaker(name = "getUserRatingsCB", fallbackMethod = "getUserRatingsFallback")
  @Bulkhead(name = "getUserRatingsBH", fallbackMethod = "getUserRatingsFallback", type = Bulkhead.Type.THREADPOOL)
  public UserRatings getUserRatings(int userId) {
    return this.webClient
        .build()
        .get()
        .uri("http://movie-ratings-service/ratings/users/" + userId)
        .retrieve()
        .bodyToMono(UserRatings.class)
        .block();
  }

  public UserRatings getUserRatingsFallback(int userId, Exception e) {
    return UserRatings.builder()
        .userId(userId)
        .movieRatingList(Arrays.asList(MovieRating.builder().movieId(0).ratings(0).build()))
        .build();
  }
}
