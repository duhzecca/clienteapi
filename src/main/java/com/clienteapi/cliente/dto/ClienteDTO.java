package com.clienteapi.cliente.dto;

import com.clienteapi.lista.dto.ListaDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteDTO {

    private Long id;
    private String email;
    private String name;
    private ListaDTO list;

}
