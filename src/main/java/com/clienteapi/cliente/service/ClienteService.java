package com.clienteapi.cliente.service;

import com.clienteapi.cliente.dto.ClienteDTO;
import com.clienteapi.cliente.exception.BadRequestException;
import com.clienteapi.cliente.mapper.ClienteMapper;
import com.clienteapi.cliente.repository.ClienteRepository;
import com.clienteapi.lista.entity.Lista;
import com.clienteapi.lista.repository.ListaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ListaRepository listaRepository;
    private final ClienteMapper clienteMapper;

    public List<ClienteDTO> listClientes() {
        log.info("Listando clientes");
        return clienteRepository.findAll().stream().map(clienteMapper::toClienteDTO).collect(Collectors.toList());
    }

    public ClienteDTO insertCliente(ClienteDTO clienteDTO) throws BadRequestException {
        final var clienteEmailExistent = clienteRepository.findAll().stream().filter(f -> f.getEmail().equals(clienteDTO.getEmail())).findAny();
        if (clienteEmailExistent.isEmpty()) {
            final var insertedCliente = clienteRepository.save(clienteMapper.toCliente(clienteDTO));
            log.info("Cliente inserido com sucesso");
            return clienteMapper.toClienteDTO(insertedCliente);
        } else {
            log.error("Erro ao inserir o cliente. Email {} ja cadastrado", clienteDTO.getEmail());
            throw new BadRequestException(String.format("Email %s ja cadastrado", clienteDTO.getEmail()));
        }
    }

    public ClienteDTO updateCliente(Integer idCliente, ClienteDTO clienteDTO) throws BadRequestException {
        final var clienteById = clienteRepository.findById(idCliente);
        if (clienteById.isEmpty()) {
            log.error("Cliente com id {} não encontrado", idCliente);
            throw new BadRequestException(String.format("Cliente com id %d não encontrado", idCliente));
        } else {
            var cliente = clienteById.get();
            if (clienteRepository.findByEmail(clienteDTO.getEmail()).isPresent()) {
                log.error("Erro ao atualizar o cliente. Email {} ja cadastrado", clienteDTO.getEmail());
                throw new BadRequestException(String.format("Email %s ja cadastrado", clienteDTO.getEmail()));
            }
            var clienteUpdated = clienteRepository.save(cliente.merge(clienteMapper.toCliente(clienteDTO)));
            log.info("Cliente {} atualizado com sucesso", idCliente);
            return clienteMapper.toClienteDTO(clienteUpdated);
        }
    }

    public void deleteCliente(Integer idCliente) throws BadRequestException {
        if (clienteRepository.findById(idCliente).isEmpty()) {
            log.error("Cliente com id {} não encontrado", idCliente);
            throw new BadRequestException(String.format("Cliente com id %d não encontrado", idCliente));
        }
        clienteRepository.deleteById(idCliente);
        log.info("Cliente {} deletado com sucesso", idCliente);
    }

    public ClienteDTO createList(Integer idCliente) throws BadRequestException {
        final var clienteById = clienteRepository.findById(idCliente);
        if (clienteById.isEmpty()) {
            log.error("Cliente com id {} não encontrado", idCliente);
            throw new BadRequestException(String.format("Cliente com id %d não encontrado", idCliente));
        } else {
            var cliente = clienteById.get();
            if (cliente.getList() != null) {
                log.error("Cliente com id {} ja possui lista de produtos favoritos", idCliente);
                throw new BadRequestException(String.format("Cliente com id %s ja possui lista de produtos favoritos", idCliente));
            }
            var lista = new Lista();
            lista.setName(String.format("Lista %s", cliente.getName()));
            listaRepository.save(lista);
            cliente.setList(lista);
            clienteRepository.save(cliente);
            log.info("Lista criada com sucesso para cliente com id {}", idCliente);
            return clienteMapper.toClienteDTO(cliente);
        }
    }

}
