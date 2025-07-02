package com.devlu.futuresistema_cliente.business;

import com.devlu.futuresistema_cliente.api.EnderecoRequestDTO;
import com.devlu.futuresistema_cliente.controller.dto.EnderecoDTO;
import com.devlu.futuresistema_cliente.entities.Endereco;
import com.devlu.futuresistema_cliente.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço de negócios para operações relacionadas a Endereços.
 * Gerencia a lógica de conversão entre DTOs e entidades, validações e persistência.
 */
@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    /**
     * Converte um EnderecoRequestDTO para EnderecoDTO.
     * @param requestDTO O EnderecoRequestDTO a ser convertido.
     * @return O EnderecoDTO correspondente.
     */
    private EnderecoDTO toEnderecoDTO(EnderecoRequestDTO requestDTO) {
        if (requestDTO == null) return null;
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setId(requestDTO.getId());
        enderecoDTO.setCep(requestDTO.getCep());
        enderecoDTO.setLogradouro(requestDTO.getLogradouro());
        enderecoDTO.setNumero(requestDTO.getNumero());
        enderecoDTO.setComplemento(requestDTO.getComplemento());
        enderecoDTO.setBairro(requestDTO.getBairro());
        enderecoDTO.setCidade(requestDTO.getCidade());
        enderecoDTO.setEstado(requestDTO.getEstado());
        enderecoDTO.setStatus(requestDTO.getStatus());
        return enderecoDTO;
    }

    /**
     * Converte um EnderecoDTO em uma entidade Endereco.
     * @param dto O EnderecoDTO a ser convertido.
     * @return A entidade Endereco.
     */
    private Endereco toEntity(EnderecoDTO dto) {
        if (dto == null) return null;
        Endereco entity = new Endereco();
        entity.setId(dto.getId());
        entity.setCep(dto.getCep());
        entity.setLogradouro(dto.getLogradouro());
        entity.setNumero(dto.getNumero());
        entity.setComplemento(dto.getComplemento());
        entity.setBairro(dto.getBairro());
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
        entity.setStatus(dto.getStatus()); // Esta linha é a que você apontou, deve funcionar agora
        return entity;
    }

    /**
     * Converte uma entidade Endereco em EnderecoDTO.
     * @param entity A entidade Endereco a ser convertida.
     * @return O EnderecoDTO.
     */
    private EnderecoDTO toDTO(Endereco entity) {
        if (entity == null) return null;
        EnderecoDTO dto = new EnderecoDTO();
        dto.setId(entity.getId());
        dto.setCep(entity.getCep());
        dto.setLogradouro(entity.getLogradouro());
        dto.setNumero(entity.getNumero());
        dto.setComplemento(entity.getComplemento());
        dto.setBairro(entity.getBairro());
        dto.setCidade(entity.getCidade());
        dto.setEstado(entity.getEstado());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    /**
     * Insere um novo endereço no banco de dados.
     * @param enderecoRequestDTO Os dados do endereço a serem inseridos.
     * @return O EnderecoDTO do endereço inserido.
     * @throws IllegalArgumentException se os dados forem inválidos.
     */
    @Transactional
    public EnderecoDTO insert(EnderecoRequestDTO enderecoRequestDTO) {
        validateEnderecoRequestDTO(enderecoRequestDTO); // Valida dados

        // Se o DTO já tiver um ID, deve ser uma atualização, não uma inserção
        if (enderecoRequestDTO.getId() != null) {
            throw new IllegalArgumentException("Endereço já possui ID. Use o método 'update' para atualizar.");
        }

        Endereco endereco = toEntity(toEnderecoDTO(enderecoRequestDTO)); // Converte RequestDTO para DTO e depois para Entity
        Endereco savedEndereco = enderecoRepository.save(endereco);
        return toDTO(savedEndereco);
    }

    /**
     * Atualiza um endereço existente no banco de dados.
     * @param id O ID do endereço a ser atualizado.
     * @param enderecoRequestDTO Os dados atualizados do endereço.
     * @return O EnderecoDTO do endereço atualizado.
     * @throws IllegalArgumentException se o endereço não for encontrado ou dados forem inválidos.
     */
    @Transactional
    public EnderecoDTO update(Long id, EnderecoRequestDTO enderecoRequestDTO) {
        validateEnderecoRequestDTO(enderecoRequestDTO); // Valida dados

        if (id == null) {
            throw new IllegalArgumentException("ID do endereço é obrigatório para atualização.");
        }

        // Verifica se o endereço com o ID existe
        Endereco existingEndereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado com ID: " + id));

        // Atualiza os campos do endereço existente com os dados do RequestDTO
        existingEndereco.setCep(enderecoRequestDTO.getCep());
        existingEndereco.setLogradouro(enderecoRequestDTO.getLogradouro());
        existingEndereco.setNumero(enderecoRequestDTO.getNumero());
        existingEndereco.setComplemento(enderecoRequestDTO.getComplemento());
        existingEndereco.setBairro(enderecoRequestDTO.getBairro());
        existingEndereco.setCidade(enderecoRequestDTO.getCidade());
        existingEndereco.setEstado(enderecoRequestDTO.getEstado());
        existingEndereco.setStatus(enderecoRequestDTO.getStatus());

        Endereco updatedEndereco = enderecoRepository.save(existingEndereco);
        return toDTO(updatedEndereco);
    }

    /**
     * Busca um endereço pelo ID.
     * @param id O ID do endereço.
     * @return O EnderecoDTO encontrado ou null se não existir.
     */
    @Transactional(readOnly = true)
    public EnderecoDTO findById(Long id) {
        return enderecoRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    /**
     * Lista todos os endereços.
     * @return Uma lista de EnderecoDTOs.
     */
    @Transactional(readOnly = true)
    public List<EnderecoDTO> findAll() {
        return enderecoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Exclui um endereço pelo ID.
     * @param id O ID do endereço a ser excluído.
     * @throws IllegalArgumentException se o endereço não for encontrado.
     */
    @Transactional
    public void delete(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new IllegalArgumentException("Endereço não encontrado para exclusão: " + id);
        }
        enderecoRepository.deleteById(id);
    }

    /**
     * Valida os dados de um EnderecoRequestDTO.
     * As validações com @NotBlank etc. já são feitas no DTO,
     * mas esta pode adicionar validações de lógica de negócio mais complexas.
     * @param dto O EnderecoRequestDTO a ser validado.
     * @throws IllegalArgumentException se algum campo obrigatório estiver ausente ou inválido.
     */
    private void validateEnderecoRequestDTO(EnderecoRequestDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Dados do endereço não podem ser nulos.");
        }
        // As validações de @NotBlank, @Pattern já são feitas pelo @Valid no controller.
        // Aqui, você adicionaria validações de regra de negócio que dependam de outros dados, por exemplo.
        // Por enquanto, apenas um exemplo simples:
        if (dto.getCep() == null || dto.getCep().trim().isEmpty()) {
            throw new IllegalArgumentException("O CEP é obrigatório (validação extra no serviço).");
        }
        // ... (outras validações que desejar)
    }
}