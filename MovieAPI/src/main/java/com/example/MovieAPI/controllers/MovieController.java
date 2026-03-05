package com.example.MovieAPI.controllers;

import com.example.MovieAPI.dto.MovieDto;
import com.example.MovieAPI.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add-moive")
    public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file,
                                                    @RequestPart String moiveDto) throws IOException {
        MovieDto dto = convertToMoiveDto(moiveDto);
        return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
    }

    private MovieDto convertToMoiveDto(String movieDtoObj) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoObj, MovieDto.class);
    }
}
