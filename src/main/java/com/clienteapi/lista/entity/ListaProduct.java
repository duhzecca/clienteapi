package com.clienteapi.lista.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "lista_product")
public class ListaProduct {

    private static final long serialVersionUID = -644668071630235231L;

    @EmbeddedId
    private ListaProductKey key;

}
