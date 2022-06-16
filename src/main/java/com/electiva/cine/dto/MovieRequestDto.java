package com.electiva.cine.dto;

import com.electiva.cine.entity.RoomEntity;
import com.electiva.cine.entity.ScheduleEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieRequestDto {
    private String description;
    private RoomEntity room;
    private ScheduleEntity schedule;
}
