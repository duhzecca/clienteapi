package com.clienteapi.lista.mapper;

import com.clienteapi.cliente.dto.ClienteDTO;
import com.clienteapi.cliente.entity.Cliente;
import com.clienteapi.lista.dto.ListaDTO;
import com.clienteapi.lista.entity.Lista;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListaMapper {

    ListaDTO toListaDTO(Lista lista);

    Lista toLista(ListaDTO listaDTO);

}
