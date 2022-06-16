package com.electiva.cine.services;

import com.electiva.cine.dto.MovieRequestDto;
import com.electiva.cine.dto.ServiceResponseDto;
import com.electiva.cine.entity.MovieEntity;
import com.electiva.cine.entity.SeatEntity;
import com.electiva.cine.repository.MovieRepository;
import com.electiva.cine.repository.RoomRepository;
import com.electiva.cine.repository.ScheduleRepository;
import com.electiva.cine.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService{
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public ServiceResponseDto saveMovie(MovieRequestDto movieRequestDto) {
        ServiceResponseDto serviceResponseDto = new ServiceResponseDto();
        List<MovieEntity> movie =
                movieRepository.findAllByIdRoomAndIdSchedule(movieRequestDto.getRoom().getId(),movieRequestDto.getSchedule().getId());
        if(movie.size()==0){
            MovieEntity movieEntity = new MovieEntity();
            movieEntity.setDescription(movieRequestDto.getDescription());
            movieEntity.setIdRoom(movieRequestDto.getRoom().getId());
            movieEntity.setIdSchedule(movieRequestDto.getSchedule().getId());
            try{
                movieRepository.save(movieEntity);
                serviceResponseDto.setMessage("Pelicula guardada con exito!");
                serviceResponseDto.setCode("OK");
            }catch(Exception E){
                System.err.println("Cannot save: "+E);
                serviceResponseDto.setMessage("No pudo guardarse la pelicula");
                serviceResponseDto.setCode("ERROR_SAVED");
            }
        }else{
            serviceResponseDto.setMessage("El horario y sala ya estan ocupados");
            serviceResponseDto.setCode("ROOM_AND_SCHEDULE_ALREADY_EXISTS");
        }
        return serviceResponseDto;
    }

    @Override
    public ServiceResponseDto updateMovie(MovieRequestDto movie, Long id) {
        ServiceResponseDto serviceResponseDto = new ServiceResponseDto();
        Optional<MovieEntity> movieEntityOptional = movieRepository.findById(id);
        if(movieEntityOptional.isEmpty()){
            serviceResponseDto.setMessage("No se encontro la pelicula");
            serviceResponseDto.setCode("NOT_FOUND");
        }else{
            if(!checkRoomAndSchedule(movie)){
                MovieEntity movieEntity = new MovieEntity();
                movieEntity.setId(id);
                movieEntity.setDescription(movie.getDescription());
                movieEntity.setIdRoom(movie.getRoom().getId());
                movieEntity.setIdSchedule(movie.getSchedule().getId());
                movieRepository.save(movieEntity);
                serviceResponseDto.setMessage("Pelicula actualizada con exito!");
                serviceResponseDto.setCode("OK");
            }else{
                serviceResponseDto.setMessage("El horario y sala ya estan ocupados");
                serviceResponseDto.setCode("ROOM_AND_SCHEDULE_ALREADY_EXISTS");
            }
        }
        return serviceResponseDto;
    }

    @Override
    public ServiceResponseDto deleteMovie(Long id) {
        ServiceResponseDto serviceResponseDto = new ServiceResponseDto();
        Optional<MovieEntity> movieEntityOptional = movieRepository.findById(id);
        if(movieEntityOptional.isEmpty()){
            serviceResponseDto.setMessage("No se encontro la pelicula");
            serviceResponseDto.setCode("NOT_FOUND");
        }else{
            movieRepository.deleteById(id);
            serviceResponseDto.setMessage("Se elimino la pelicula con exito");
            serviceResponseDto.setCode("NOT_CONTENT");
        }
        return serviceResponseDto;
    }

    @Override
    public List<SeatEntity> getSeats(Long idMovie) {
        List<SeatEntity> seatList = new ArrayList<>();
        Optional<MovieEntity> movieEntity = movieRepository.findById(idMovie);
        if(movieEntity.isEmpty()){
            return seatList;
        }else{
            seatList = seatRepository.findAllByIdRoomAndIdSchedule(
                    movieEntity.get().getIdRoom(), movieEntity.get().getIdSchedule());
        }
        return seatList;
    }

    @Override
    public List<MovieEntity> getMovies() {
        List<MovieEntity> movieList = new ArrayList<>();
        List<MovieEntity> movies = movieRepository.findAll();
        if(!movies.isEmpty()){
            movieList = movies;
        }
        return movieList;
    }

    private boolean checkRoomAndSchedule(MovieRequestDto movie){
        boolean isExists = false;
        List<MovieEntity> movieList =
                movieRepository.findAllByIdRoomAndIdSchedule(movie.getRoom().getId(),movie.getSchedule().getId());
        if(movieList.size()>0){
            isExists = true;
        }
        return isExists;
    }
}
