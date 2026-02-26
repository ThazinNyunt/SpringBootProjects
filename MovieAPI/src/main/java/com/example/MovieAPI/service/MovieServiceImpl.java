package com.example.MovieAPI.service;

import com.example.MovieAPI.dto.MovieDto;
import com.example.MovieAPI.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final FileService fileService;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) {
        // 1. upload the file

        // 2. set the value of field 'poster' as filename
        // 3. map dto to Movie objet
        // 4. save the movie object -> saved Movie object
        // 5. generate the posterUrl
        // 6. map Movie object to DTO object and return it

        return null;
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        return null;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        return List.of();
    }
}
