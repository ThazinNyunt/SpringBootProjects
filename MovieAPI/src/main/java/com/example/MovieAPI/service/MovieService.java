package com.example.MovieAPI.service;

import com.example.MovieAPI.dto.MovieDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MovieService {

    MovieDto addMovie(MovieDto movieDto, MultipartFile file);

    MovieDto getMovie(Integer movieId);

    List<MovieDto> getAllMovies();
}
