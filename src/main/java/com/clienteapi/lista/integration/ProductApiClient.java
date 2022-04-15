package com.clienteapi.lista.integration;

import com.clienteapi.cliente.exception.BadRequestException;
import com.clienteapi.lista.dto.ProdutoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class ProductApiClient {

    private static final String URL_API_PRODUCT ="https://challenge-api.luizalabs.com/api/product";

    private final RestTemplate restTemplate;

    @Autowired
    public ProductApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<ProdutoDTO> getProductByIdAsync(String idProduto) throws BadRequestException {
        try {
            log.info("Buscando produto com id {} na api de produtos", idProduto);
            final var forEntity = restTemplate.getForEntity(String.format("%s/%s/", URL_API_PRODUCT, idProduto), ProdutoDTO.class);
            log.info("Produto {} encontrado na api de produtos", idProduto);
            return CompletableFuture.completedFuture(forEntity.getBody());
        } catch (HttpClientErrorException exception) {
            log.error(exception.getMessage());
            throw new BadRequestException(exception.getMessage());
        }
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
