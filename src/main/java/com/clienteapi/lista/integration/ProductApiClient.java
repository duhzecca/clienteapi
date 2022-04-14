package com.clienteapi.lista.integration;

import com.clienteapi.cliente.exception.BadRequestException;
import com.clienteapi.lista.dto.ProdutoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ProductApiClient {

    private final String URL_API_PRODUCT ="https://challenge-api.luizalabs.com/api/product";

    private final RestTemplate restTemplate;

    @Autowired
    public ProductApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProdutoDTO getProductById(String idProduto) throws BadRequestException {
        try {
            final var forEntity = restTemplate.getForEntity(String.format("%s/%s/", URL_API_PRODUCT, idProduto), ProdutoDTO.class);
            return forEntity.getBody();
        } catch (HttpClientErrorException exception) {
            log.error(exception.getMessage());
            throw new BadRequestException(exception.getMessage());
        }
    }

    //a81e138c-a288-41fa-ab94-8f42d6add281
}
