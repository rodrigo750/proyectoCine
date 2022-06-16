package com.electiva.cine.services;

import com.electiva.cine.dto.ReservationRequestDto;
import com.electiva.cine.dto.ServiceResponseDto;
import com.electiva.cine.entity.MovieEntity;
import com.electiva.cine.entity.ReservationEntity;
import com.electiva.cine.entity.SeatEntity;
import com.electiva.cine.repository.MovieRepository;
import com.electiva.cine.repository.ReservationRepository;
import com.electiva.cine.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService{

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<ServiceResponseDto> makeReservation(ReservationRequestDto reservation) {
        List<ServiceResponseDto> serviceResponseDtoList = new ArrayList<>();
        ServiceResponseDto serviceResponseDto = new ServiceResponseDto();
        Optional<MovieEntity> movieEntityOptional = movieRepository.findById(reservation.getMovieId());
        if(movieEntityOptional.isEmpty()){
            serviceResponseDto.setMessage("Movie not found");
            serviceResponseDto.setCode("NOT_FOUND");
        }else{
            MovieEntity movie = movieEntityOptional.get();
            Long idRoom = movie.getIdRoom();
            Long idSchedule = movie.getIdSchedule();
            for(SeatEntity seat: reservation.getSeats()){
                serviceResponseDto = new ServiceResponseDto();
                SeatEntity seatEntity = seatRepository.findById(seat.getId()).get();
                if(seatEntity.getIdRoom() == idRoom && seatEntity.getIdSchedule() == idSchedule){
                    if(seatEntity.getStatus().equals("disponible")){
                        try{
                            seatEntity.setStatus("No disponible");
                            seatRepository.save(seatEntity);
                            ReservationEntity reservationEntity = new ReservationEntity();
                            reservationEntity.setIdSeat(seat.getId());
                            reservationEntity.setIdUser(reservation.getUserId());
                            reservationRepository.save(reservationEntity);
                            serviceResponseDto.setMessage("Reservacion exitosa del asiento id = "+seatEntity.getId());
                            serviceResponseDto.setCode("OK");
                        }catch(Exception e){
                            serviceResponseDto.setMessage("No se pudo reservar el asiento id = "+seatEntity.getId()+" "+e);
                            serviceResponseDto.setCode("ERROR");
                        }
                    }else{
                        serviceResponseDto.setMessage("El asiento no esta disponible id = "+seatEntity.getId());
                        serviceResponseDto.setCode("ERROR");
                    }
                }else{
                    serviceResponseDto.setMessage("El asiento con id = "+seatEntity.getId()+" no corresponde a la pelicula");
                    serviceResponseDto.setCode("ERROR");
                }
                serviceResponseDtoList.add(serviceResponseDto);
            }
        }
        return serviceResponseDtoList;
    }
}
