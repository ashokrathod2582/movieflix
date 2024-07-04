package com.movieflix.movieApi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

    @NotNull
    private String title;
    @NotNull
    private String director;
    @NotNull
    private String studio;
    private Set<String> movieCast;
    private Integer releaseYear;
    private String poster;
}
