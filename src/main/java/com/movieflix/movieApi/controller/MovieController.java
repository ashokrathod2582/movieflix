package com.movieflix.movieApi.controller;

import com.movieflix.movieApi.dto.MovieRequest;
import com.movieflix.movieApi.dto.MovieResponse;
import com.movieflix.movieApi.model.Movie;
import com.movieflix.movieApi.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @PostMapping()
    public ResponseEntity<MovieResponse> addMovie(@Valid @RequestBody MovieRequest movie){
        MovieResponse movieResponse = new MovieResponse();
        Movie movieAdded = this.movieService.addMovie(movie);
        if(movieAdded != null){
            movieResponse.setMessage("Movie Added Successfully");
            movieResponse.setResult(Collections.singletonList(movieAdded));
            return new ResponseEntity<>(movieResponse,HttpStatus.CREATED);
        }else{
            movieResponse.setErrorMessage("Unable to add the move");
            return new ResponseEntity<>(movieResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<MovieResponse> getAllMovies(){
        MovieResponse movieResponse = new MovieResponse();
        List<Movie> movies = this.movieService.getAllMovies();
        if (!movies.isEmpty()) {
            movieResponse.setResult(movies);
            movieResponse.setMessage("Movies fetched successfully");
        } else {
            movieResponse.setMessage("No movies found in the database");
        }
        return new ResponseEntity<>(movieResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findMovieById(@PathVariable("id") int id){
        MovieResponse movieResponse = new MovieResponse();
        Movie movie = this.movieService.findMovieById(id);
        if(movie != null && movie.getTitle() != null){
            movieResponse.setResult(Collections.singletonList(movie));
            movieResponse.setMessage("Movie fetched successfully");
            return new ResponseEntity<>(movieResponse,HttpStatus.OK);
        }else {
            movieResponse.setErrorMessage("No records found with id: "+id);
            return new ResponseEntity<>(movieResponse,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MovieResponse> deleteMovieById(@PathVariable("id") int id){
        MovieResponse movieResponse = new MovieResponse();
        boolean isDeleted = this.movieService.deletMovieById(id);
        if(isDeleted){
            movieResponse.setMessage("Movie deleted successfully.");
            return new ResponseEntity<>(movieResponse,HttpStatus.OK);
        }else{
            movieResponse.setErrorMessage("Movie with ID {"+id+"} not found, unable to delete.");
            return new ResponseEntity<>(movieResponse,HttpStatus.NOT_FOUND);
        }
    }
}
