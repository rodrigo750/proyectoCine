package com.electiva.cine.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "asientos")
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fila")
    private int row;

    @Column(name = "numero")
    private int number;

    @Column(name = "id_sala")
    private Long idRoom;

    @Column(name = "id_horario")
    private Long idSchedule;

    @Column(name = "status")
    private String status;

}
