package com.electiva.cine.repository;

import com.electiva.cine.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity,Long> {
    List<SeatEntity> findAllByIdRoomAndIdSchedule(Long roomId, Long scheduleId);
}
