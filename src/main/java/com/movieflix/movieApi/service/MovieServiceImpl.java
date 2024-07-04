package com.movieflix.movieApi.service;

import com.movieflix.movieApi.dto.MovieRequest;
import com.movieflix.movieApi.dto.MovieResponse;
import com.movieflix.movieApi.exception.MovieServiceException;
import com.movieflix.movieApi.model.Movie;
import com.movieflix.movieApi.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    @Override
    public Movie addMovie(MovieRequest movie) {
        try {
            Movie movie1 = new Movie();
            movie1.setTitle(movie.getTitle());
            movie1.setDirector(movie.getDirector());
            movie1.setMovieCast(movie.getMovieCast());
            movie1.setStudio(movie.getStudio());
            movie1.setPoster(movie.getPoster());
            movie1.setReleaseYear(movie.getReleaseYear());
            Movie savedMovie = this.movieRepository.save(movie1);
            log.info("Movie added successfully: {}", savedMovie.getTitle());
            return savedMovie;
        }catch (DataAccessException e){
            log.error("Unable to add movie due to data access issue: {}",e.getMessage());
            throw new MovieServiceException("Unable to add movie due to data access issue: {} "+e.getMessage());
        }catch (Exception e){
            log.error("Unexpected exception while adding movie: {}", e.getMessage(), e);
            throw new MovieServiceException("Unexpected exception while adding movie: "
                    +e.getMessage(),e);
        }
    }

    @Override
    public List<Movie> getAllMovies() {
        try{
            List<Movie> movies = this.movieRepository.findAll();
            log.info("Fetched {} movies from the database", movies.size());
            return movies;
        }catch (DataAccessException e){
            log.error("Unable to fetch movies due to data access issue: {}", e.getMessage());
            throw new MovieServiceException("Unable to fetch movies due to data access issue: {} "+e.getMessage());
        }catch (Exception e){
            log.error("Unexpected exception while fetching movies: {}", e.getMessage());
            throw new MovieServiceException("Unexpected exception while fetching movies: {}"
                    +e.getMessage(),e);
        }
    }

    @Override
    public Movie findMovieById(int id) {
        try {
            Optional<Movie> movie = this.movieRepository.findById(id);
            Movie movie1 = new Movie();
            if(movie.isPresent()){
                movie1 = movie.get();
                log.info("MovieServiceImpl.findMovieById(): Fetched {} movie form the database",movie1.getTitle());
            }
            return movie1;
        }catch (DataAccessException e){
            log.error("Unable to fetch movie due to data access issue: {}",e.getMessage());
            throw new MovieServiceException("Unable to fetch movie due to data access issue: {}"+e.getMessage());
        }catch (Exception e){
            log.error("Unexpected exception while fetching movie: {}",e.getMessage());
            throw new MovieServiceException("Unexpected exception while fetching movie: {}"+e.getMessage(),e);
        }
    }

    @Override
    public boolean deletMovieById(int id) {
        try{
            Optional<Movie> movie = this.movieRepository.findById(id);
            if (movie.isPresent()) {
                this.movieRepository.deleteById(id);
                return true;  // Deletion successful
            } else {
                log.warn("Movie with ID {} not found, unable to delete.", id);
                return false;  // Movie not found
            }
        }catch (DataAccessException e){
            log.error("Unable to delete movie due to data access issue: {}",e.getMessage());
            throw new MovieServiceException("Unable to delete movie due to data access issue: {}"+e.getMessage());
        }catch (Exception e){
            log.error("Unexpected exception occur while deleting movie: {}",e.getMessage());
            throw new MovieServiceException("Unexpected exception occur while deleting movie: {}"+e.getMessage(),e);
        }
    }
}
