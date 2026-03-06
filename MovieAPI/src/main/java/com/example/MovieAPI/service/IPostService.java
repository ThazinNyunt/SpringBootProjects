package com.example.MovieAPI.service;

import com.example.MovieAPI.output.PostOfficeResponseBean;

public interface IPostService {
    public PostOfficeResponseBean fetchPostOfficeDetailsByCity(String city);
}
