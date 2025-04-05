package com.devlu.futuresistema_cliente.api;

import com.devlu.futuresistema_cliente.entities.ClienteEntity;
import com.devlu.futuresistema_cliente.entities.EnderecoEntity;
import org.springframework.stereotype.Component;

/**
 * Componente responsável por converter a entidade {@link ClienteEntity} em DTOs e vice-versa.
 *
 * <p>Esta classe fornece métodos para mapear os dados entre a entidade e os DTOs, facilitando a transferência de dados
 * entre as camadas da aplicação.</p>
 */
@Component
public class ClienteMapper {

    /**
     * Converte um {@link ClienteRequestDTO} para uma entidade {@link ClienteEntity}.
     *
     * @param clienteDTO O {@link ClienteRequestDTO} a ser convertido.
     * @return Uma entidade {@link ClienteEntity} com os dados do request.
     */
    public ClienteEntity paraClienteEntity(ClienteRequestDTO clienteDTO) {
        return ClienteEntity.builder()
                .nome(clienteDTO.getNome())
                .email(clienteDTO.getEmail())
                .telefone(clienteDTO.getTelefone())
                .build();
    }

    /**
     * Converte um {@link ClienteRequestDTO} para uma entidade {@link EnderecoEntity}.
     *
     * @param clienteDTO O {@link ClienteRequestDTO} contendo os dados do endereço.
     * @return Uma entidade {@link EnderecoEntity} com os dados do endereço.
     */
    public EnderecoEntity paraEnderecoEntity(ClienteRequestDTO clienteDTO) {
        return EnderecoEntity.builder()
                .cep(clienteDTO.getCep())
                .logradouro(clienteDTO.getLogradouro())
                .numero(clienteDTO.getNumero())
                .complemento(clienteDTO.getComplemento())
                .bairro(clienteDTO.getBairro())
                .cidade(clienteDTO.getCidade())
                .estado(clienteDTO.getEstado())
                .build();
    }
}



