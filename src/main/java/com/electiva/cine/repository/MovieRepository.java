package com.electiva.cine.repository;

import com.electiva.cine.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    List<MovieEntity> findAllByIdRoomAndIdSchedule(Long roomId, Long scheduleId);
}
