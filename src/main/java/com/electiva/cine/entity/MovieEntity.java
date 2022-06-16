package com.electiva.cine.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "peliculas")
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="descripcion")
    private String description;

    @Column(name="id_sala")
    private Long idRoom;

    @Column(name="id_horario")
    private Long idSchedule;
}
