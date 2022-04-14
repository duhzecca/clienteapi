package com.clienteapi.lista.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutoDTO {

    private String id;
    private String brand;
    private String title;
    private String image;
    private Long price;
    private Double reviewScore;

}
