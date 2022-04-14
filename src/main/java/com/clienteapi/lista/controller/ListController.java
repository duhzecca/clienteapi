package com.clienteapi.lista.controller;

import com.clienteapi.cliente.exception.BadRequestException;
import com.clienteapi.lista.dto.ListaDTO;
import com.clienteapi.lista.service.ListaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/listas")
public class ListController {

    private final ListaService listaService;

    @GetMapping("/{idLista}/")
    public ResponseEntity<ListaDTO> getList(@PathVariable("idLista") Integer idLista) throws BadRequestException {
        return ResponseEntity.ok(listaService.getLista(idLista));
    }

    @PutMapping("/{idLista}/produto/{idProduto}/")
    public ResponseEntity<Void> addProduct(@PathVariable("idLista") Integer idLista, @PathVariable("idProduto") String idProduto) throws BadRequestException {
        listaService.addProduct(idLista, idProduto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idLista}/produto/{idProduto}/")
    public ResponseEntity<Void> removeProduct(@PathVariable("idLista") Integer idLista, @PathVariable("idProduto") String idProduto) throws BadRequestException {
        listaService.removeProduct(idLista, idProduto);
        return ResponseEntity.ok().build();
    }

}
