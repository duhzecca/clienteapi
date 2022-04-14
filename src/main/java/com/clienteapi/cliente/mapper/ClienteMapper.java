package com.clienteapi.cliente.mapper;

import com.clienteapi.cliente.dto.ClienteDTO;
import com.clienteapi.cliente.entity.Cliente;
import com.clienteapi.lista.mapper.ListaMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ListaMapper.class)
public interface ClienteMapper {

    ClienteDTO toClienteDTO(Cliente cliente);

    Cliente toCliente(ClienteDTO clienteDTO);
}
