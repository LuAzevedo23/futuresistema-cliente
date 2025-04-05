package com.devlu.futuresistema_cliente.business;

import com.devlu.futuresistema_cliente.api.EnderecoMapper;
import com.devlu.futuresistema_cliente.controller.dto.EnderecoDTO;
import com.devlu.futuresistema_cliente.entities.EnderecoEntity;
import com.devlu.futuresistema_cliente.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço que implementa a lógica de negócios para gerenciamento de endereços.
 *
 * <p>Esta classe fornece métodos para buscar, listar, inserir, atualizar e excluir endereços.</p>
 */
@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    /**
     * Construtor para injeção de dependência.
     *
     * @param enderecoRepository O repositório de endereços.
     * @param enderecoMapper   O mapper de endereços.
     */
    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    /**
     * Busca um endereço por ID.
     *
     * @param id O ID do endereço a ser buscado.
     * @return Um {@link EnderecoDTO} com os dados do endereço.
     * @throws ResourceNotFoundException Se o endereço não for encontrado.
     */
    //@Transactional(readOnly = true)
    @Transactional
    public EnderecoDTO findById(Long id) {
        Optional<EnderecoEntity> endereco = enderecoRepository.findById(id);
        return endereco.map(enderecoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com o ID: " + id));
    }

    /**
     * Lista todos os endereços.
     *
     * @return Uma lista de {@link EnderecoDTO} com os dados de todos os endereços.
     */
    //@Transactional(readOnly = true)
    @Transactional
    public List<EnderecoDTO> findAll() {
        List<EnderecoEntity> enderecos = enderecoRepository.findAll();
        return enderecos.stream()
                .map(enderecoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Insere um novo endereço.
     *
     * @param enderecoDTO O {@link EnderecoDTO} com os dados do endereço a ser inserido.
     * @return Um {@link EnderecoDTO} com os dados do endereço inserido.
     */
    @Transactional
    public EnderecoDTO insert(EnderecoDTO enderecoDTO) {
        EnderecoEntity enderecoEntity = enderecoMapper.toEntity(enderecoDTO);
        EnderecoEntity savedEndereco = enderecoRepository.save(enderecoEntity);
        return enderecoMapper.toDTO(savedEndereco);
    }

    /**
     * Exclui um endereço por ID.
     *
     * @param id O ID do endereço a ser excluído.
     * @throws ResourceNotFoundException Se o endereço não for encontrado.
     * @throws DatabaseException Se ocorrer uma violação de integridade do banco de dados.
     */
    @Transactional
    public void delete(Long id) {
        try {
            enderecoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Endereço não encontrado com o ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade do banco de dados");
        }
    }

    /**
     * Atualiza um endereço existente.
     *
     * @param id          O ID do endereço a ser atualizado.
     * @param enderecoDTO O {@link EnderecoDTO} com os dados do endereço a serem atualizados.
     * @return Um {@link EnderecoDTO} com os dados do endereço atualizado.
     * @throws ResourceNotFoundException Se o endereço não for encontrado.
     */
    @Transactional
    public EnderecoDTO update(Long id, EnderecoDTO enderecoDTO) {
        Optional<EnderecoEntity> enderecoOptional = enderecoRepository.findById(id);
        if (enderecoOptional.isPresent()) {
            EnderecoEntity enderecoEntity = enderecoOptional.get();
            enderecoEntity.setCep(enderecoDTO.getCep());
            enderecoEntity.setLogradouro(enderecoDTO.getLogradouro());
            enderecoEntity.setNumero(enderecoDTO.getNumero());
            enderecoEntity.setComplemento(enderecoDTO.getComplemento());
            enderecoEntity.setBairro(enderecoDTO.getBairro());
            enderecoEntity.setCidade(enderecoDTO.getCidade());
            enderecoEntity.setEstado(enderecoDTO.getEstado());

            EnderecoEntity updatedEndereco = enderecoRepository.save(enderecoEntity);
            return enderecoMapper.toDTO(updatedEndereco);
        } else {
            throw new ResourceNotFoundException("Endereço não encontrado com o ID: " + id);
        }
    }
}



