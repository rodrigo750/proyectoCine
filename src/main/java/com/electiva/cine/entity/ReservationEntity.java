package com.electiva.cine.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "usuarios_reservas")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="id_usuario")
    private Long idUser;

    @Column(name="id_asiento")
    private Long idSeat;
}
