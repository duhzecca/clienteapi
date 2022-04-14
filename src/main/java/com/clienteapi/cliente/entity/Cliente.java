package com.clienteapi.cliente.entity;

import com.clienteapi.lista.entity.Lista;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 5705373318813912889L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcliente")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "nome")
    private String name;

    @ManyToOne
    @JoinColumn(name = "idlista")
    private Lista list;

    public Cliente merge(Cliente newData) {
        if (newData.getEmail() != null) {
            this.setEmail(newData.getEmail());
        }
        if (newData.getName() != null) {
            this.setName(newData.getName());
        }
        return this;
    }

}
