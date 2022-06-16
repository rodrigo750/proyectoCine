package com.electiva.cine.services;

import com.electiva.cine.dto.ReservationRequestDto;
import com.electiva.cine.dto.ServiceResponseDto;

import java.util.List;

public interface ReservationService {
    List<ServiceResponseDto> makeReservation(ReservationRequestDto reservation);
}
