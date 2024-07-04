package com.movieflix.movieApi.service;

import com.movieflix.movieApi.dto.MovieRequest;
import com.movieflix.movieApi.model.Movie;

import java.util.List;

public interface MovieService {
    Movie addMovie(MovieRequest movie);

    List<Movie> getAllMovies();

    Movie findMovieById(int id);

    boolean deletMovieById(int id);
}
