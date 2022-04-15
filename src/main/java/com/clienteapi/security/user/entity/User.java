package com.clienteapi.security.user.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduser")
    private Integer id;

    @Column(name = "user")
    private String user;

    @Column(name = "password")
    private String password;

}
