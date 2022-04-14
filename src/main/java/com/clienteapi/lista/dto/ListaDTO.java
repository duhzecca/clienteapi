package com.clienteapi.lista.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListaDTO {

    private Integer id;
    private String name;
    private List<ProdutoDTO> produtoDTOList;
}
