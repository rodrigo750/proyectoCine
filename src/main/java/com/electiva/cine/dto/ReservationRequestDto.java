package com.electiva.cine.dto;

import com.electiva.cine.entity.SeatEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReservationRequestDto {
    private Long userId;
    private Long movieId;
    private List<SeatEntity> seats;
}
