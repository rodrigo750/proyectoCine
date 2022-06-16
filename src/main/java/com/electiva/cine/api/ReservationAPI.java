package com.electiva.cine.api;

import com.electiva.cine.dto.MovieRequestDto;
import com.electiva.cine.dto.ReservationRequestDto;
import com.electiva.cine.dto.ServiceResponseDto;
import com.electiva.cine.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ReservationAPI {
    @Autowired
    private ReservationService reservationService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/reservation")
    public ResponseEntity<?> saveMovie(@RequestBody ReservationRequestDto reservationRequestDto){
        List<ServiceResponseDto> reservation = reservationService.makeReservation(reservationRequestDto);
        return ResponseEntity.of(Optional.of(reservation));
    }
}
