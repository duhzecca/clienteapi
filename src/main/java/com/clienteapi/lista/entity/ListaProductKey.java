package com.clienteapi.lista.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListaProductKey implements Serializable {

    private static final long serialVersionUID = 6868522003742368207L;

    @ManyToOne()
    @JoinColumn(name = "idlista")
    private Lista list;

    @Column(name = "idproduct")
    private String product;

}
