package com.electiva.cine.services;

import com.electiva.cine.dto.MovieRequestDto;
import com.electiva.cine.dto.ServiceResponseDto;
import com.electiva.cine.entity.MovieEntity;
import com.electiva.cine.entity.SeatEntity;

import java.util.List;

public interface MovieService {
    ServiceResponseDto saveMovie(MovieRequestDto movie);
    ServiceResponseDto updateMovie(MovieRequestDto movie, Long id);
    ServiceResponseDto deleteMovie(Long id);
    List<SeatEntity> getSeats(Long idMovie);
    List<MovieEntity> getMovies();
}
