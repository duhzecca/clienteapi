package com.clienteapi.lista.service;

import com.clienteapi.cliente.exception.BadRequestException;
import com.clienteapi.lista.dto.ListaDTO;
import com.clienteapi.lista.dto.ProdutoDTO;
import com.clienteapi.lista.entity.Lista;
import com.clienteapi.lista.entity.ListaProduct;
import com.clienteapi.lista.entity.ListaProductKey;
import com.clienteapi.lista.integration.ProductApiClient;
import com.clienteapi.lista.mapper.ListaMapper;
import com.clienteapi.lista.repository.ListaProductRepository;
import com.clienteapi.lista.repository.ListaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ListaService {

    private final ProductApiClient productApiClient;
    private final ListaRepository listaRepository;
    private final ListaProductRepository listaProductRepository;
    private final ListaMapper listaMapper;

    public ListaDTO getLista(Integer idLista) throws BadRequestException, ExecutionException, InterruptedException {
        final var lista = hasList(idLista);
        final var listaProductList = listaProductRepository.findByList(lista.getId());
        var listaDTO = listaMapper.toListaDTO(lista);
        var productDTOList = new ArrayList<ProdutoDTO>();
        for (ListaProduct listaProduct : listaProductList) {
            final var productById = productApiClient.getProductByIdAsync(listaProduct.getKey().getProduct());
            productDTOList.add(productById.get());
        }
        listaDTO.setProdutoDTOList(productDTOList);
        return listaDTO;
    }

    public void addProduct(Integer idLista, String idProduto) throws BadRequestException {
        final var lista = hasList(idLista);
        final var productById = productApiClient.getProductById(idProduto);
        final var listaProduct = new ListaProduct();
        listaProduct.setKey(new ListaProductKey(lista, productById.getId()));
        listaProductRepository.save(listaProduct);
        log.info("Produto {} adicionado na lista {}", idProduto, idLista);
    }

    public void removeProduct(Integer idLista, String idProduto) throws BadRequestException {
        final var lista = hasList(idLista);
        final var productById = productApiClient.getProductById(idProduto);
        final var listaProduct = new ListaProduct();
        listaProduct.setKey(new ListaProductKey(lista, productById.getId()));
        listaProductRepository.delete(listaProduct);
        log.info("Produto {} removido da lista {}", idProduto, idLista);
    }

    private Lista hasList(Integer idLista) throws BadRequestException {
        final var lista = listaRepository.findById(idLista);
        if (lista.isEmpty()) {
            throw new BadRequestException("Lista inexistente");
        }
        return lista.get();
    }
}
