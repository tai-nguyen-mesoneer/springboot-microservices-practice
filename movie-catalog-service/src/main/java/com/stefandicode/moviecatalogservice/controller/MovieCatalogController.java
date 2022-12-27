package com.stefandicode.moviecatalogservice.controller;

import com.stefandicode.moviecatalogservice.model.MovieCatalog;
import com.stefandicode.moviecatalogservice.service.MovieCatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalogs")
public class MovieCatalogController {
  private MovieCatalogService movieCatalogService;

  public MovieCatalogController(MovieCatalogService movieCatalogService) {
    this.movieCatalogService = movieCatalogService;
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<?> getCatalogItem(@PathVariable("userId") int userId) {
    List<MovieCatalog> movieCatalogs = this.movieCatalogService.getMovieCatalog(userId);
    return ResponseEntity.ok(movieCatalogs);
  }
}
