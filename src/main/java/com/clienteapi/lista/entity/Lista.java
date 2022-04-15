package com.clienteapi.lista.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "lista")
public class Lista implements Serializable {

    private static final long serialVersionUID = 3605819615531735604L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlista")
    private Integer id;

    @Column(name = "nomelista")
    private String name;

}
