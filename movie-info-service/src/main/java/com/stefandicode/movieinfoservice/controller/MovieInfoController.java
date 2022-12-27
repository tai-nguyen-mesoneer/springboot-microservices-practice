package com.stefandicode.movieinfoservice.controller;

import com.stefandicode.movieinfoservice.model.Movie;
import com.stefandicode.movieinfoservice.model.MovieSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/movies")
public class MovieInfoController {
  @Value("${thedbmovie.url}")
  private String dbMovieUrl;

  @Value("${thedbmovie.api.key}")
  private String apiKey;

  private WebClient webClient;

  public MovieInfoController(WebClient webClient) {
    this.webClient = webClient;
  }

  @GetMapping("/{movieId}")
  public ResponseEntity<?> getMovieInfo(@PathVariable(name = "movieId") int movieId) {
    MovieSummary movieSummary =
        this.webClient
            .get()
            .uri(dbMovieUrl + "/movie/" + movieId + "?api_key=" + apiKey)
            .retrieve()
            .bodyToMono(MovieSummary.class)
            .block();
    return ResponseEntity.ok(
        Movie.builder()
            .movieId(movieId)
            .name(movieSummary.getTitle())
            .desc(movieSummary.getOverview())
            .build());
  }
}
