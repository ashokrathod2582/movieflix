package com.movieflix.movieApi.dto;

import com.movieflix.movieApi.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {
    public String message;
    public String errorMessage;
    public List<Movie> result;
}
