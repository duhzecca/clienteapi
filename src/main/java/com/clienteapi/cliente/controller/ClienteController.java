package com.clienteapi.cliente.controller;

import com.clienteapi.cliente.dto.ClienteDTO;
import com.clienteapi.cliente.exception.BadRequestException;
import com.clienteapi.cliente.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/")
    public ResponseEntity<List<ClienteDTO>> listClientes() {
        return ResponseEntity.ok(clienteService.listClientes());
    }

    @PostMapping("/")
    public ResponseEntity<ClienteDTO> insertCliente(@RequestBody ClienteDTO clienteDTO) throws BadRequestException {
        return ResponseEntity.ok(clienteService.insertCliente(clienteDTO));
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable("idCliente") Integer idCliente, @RequestBody ClienteDTO clienteDTO) throws BadRequestException {
        return ResponseEntity.ok(clienteService.updateCliente(idCliente, clienteDTO));
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> deleteCliente(@PathVariable("idCliente") Integer idCliente) throws BadRequestException {
        clienteService.deleteCliente(idCliente);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idCliente}/lista")
    public ResponseEntity<ClienteDTO> createList(@PathVariable("idCliente") Integer idCliente) throws BadRequestException {
        return ResponseEntity.ok(clienteService.createList(idCliente));
    }
}
