package com.devlu.futuresistema_cliente.api;

import com.devlu.futuresistema_cliente.controller.dto.EnderecoDTO;
import com.devlu.futuresistema_cliente.entities.EnderecoEntity;
import org.springframework.stereotype.Component;

/**
 * Componente responsável por converter a entidade {@link EnderecoEntity} em DTOs e vice-versa.
 *
 * <p>Esta classe fornece métodos para mapear os dados entre a entidade e os DTOs, facilitando a transferência de dados
 * entre as camadas da aplicação.</p>
 */
@Component
public class EnderecoMapper {

    /**
     * Converte uma entidade {@link EnderecoEntity} para um {@link EnderecoDTO}.
     *
     * @param enderecoEntity A entidade {@link EnderecoEntity} a ser convertida.
     * @return Um {@link EnderecoDTO} com os dados da entidade.
     */
    public EnderecoDTO toDTO(EnderecoEntity enderecoEntity) {
        return EnderecoDTO.builder()
                .id(enderecoEntity.getId())
                .cep(enderecoEntity.getCep())
                .logradouro(enderecoEntity.getLogradouro())
                .numero(enderecoEntity.getNumero())
                .complemento(enderecoEntity.getComplemento())
                .bairro(enderecoEntity.getBairro())
                .cidade(enderecoEntity.getCidade())
                .estado(enderecoEntity.getEstado())
                .build();
    }

    /**
     * Converte um {@link EnderecoDTO} para uma entidade {@link EnderecoEntity}.
     *
     * @param enderecoDTO O {@link EnderecoDTO} a ser convertido.
     * @return Uma entidade {@link EnderecoEntity} com os dados do DTO.
     */
    public EnderecoEntity toEntity(EnderecoDTO enderecoDTO) {
        return EnderecoEntity.builder()
                .id(enderecoDTO.getId())
                .cep(enderecoDTO.getCep())
                .logradouro(enderecoDTO.getLogradouro())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .bairro(enderecoDTO.getBairro())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .build();
    }
}
