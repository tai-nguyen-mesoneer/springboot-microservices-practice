package com.stefandicode.movieratingsservice.controller;

import com.stefandicode.movieratingsservice.model.MovieRating;
import com.stefandicode.movieratingsservice.model.UserRatings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/ratings")
public class MovieRatingsController {

  @GetMapping("/movies/{movieId}")
  public ResponseEntity<?> getMovieRating(@PathVariable(name = "movieId") int movieId) {
    return ResponseEntity.ok(MovieRating.builder().movieId(movieId).ratings(7.9).build());
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<?> getUserRatings(@PathVariable(name = "userId") int userId) {
    return ResponseEntity.ok(
        UserRatings.builder()
            .userId(userId)
            .movieRatingList(
                Arrays.asList(
                    MovieRating.builder().movieId(1).ratings(8.7).build(),
                    MovieRating.builder().movieId(2).ratings(5.5).build()))
            .build());
  }
}
