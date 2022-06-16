package com.electiva.cine.api;

import com.electiva.cine.dto.MovieRequestDto;
import com.electiva.cine.dto.ServiceResponseDto;
import com.electiva.cine.entity.MovieEntity;
import com.electiva.cine.entity.SeatEntity;
import com.electiva.cine.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MovieAPI {

    @Autowired
    private MovieService movieService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/movies")
    public ResponseEntity<?> getMovies(){
        List<MovieEntity> movies = movieService.getMovies();
        return ResponseEntity.of(Optional.of(movies));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/movies")
    public ResponseEntity<?> saveMovie(@RequestBody MovieRequestDto movieRequest){
        ServiceResponseDto movie = movieService.saveMovie(movieRequest);
        if(movie.getCode().equals("OK")){
            return ResponseEntity.of(Optional.of(movie));
        }else if(movie.getCode().equals("ERROR_SAVED")){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(movie);
        }else if(movie.getCode().equals("ROOM_AND_SCHEDULE_ALREADY_EXISTS")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(movie);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(movie);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/movies/{id}")
    public ResponseEntity<?> updateMovie(@RequestBody MovieRequestDto movieRequestDto, @PathVariable Long id){
        ServiceResponseDto movie = movieService.updateMovie(movieRequestDto,id);
        if(movie.getCode().equals("OK")){
            return ResponseEntity.of(Optional.of(movie));
        }else if(movie.getCode().equals("ROOM_AND_SCHEDULE_ALREADY_EXISTS")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(movie);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(movie);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id){
        ServiceResponseDto movie = movieService.deleteMovie(id);
        if(movie.getCode().equals("NOT_CONTENT")){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(movie);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(movie);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/movies/{id}/seats")
    public ResponseEntity<?> getMovieSeats(@PathVariable Long id){
        List<SeatEntity> seats = movieService.getSeats(id);
        if(seats.size()>0){
            return ResponseEntity.of(Optional.of(seats));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(seats);
    }
}
