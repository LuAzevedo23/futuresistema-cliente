package com.devlu.futuresistema_cliente.business;

import com.devlu.futuresistema_cliente.api.ClienteMapper;
import com.devlu.futuresistema_cliente.api.ClienteRequestDTO;
import com.devlu.futuresistema_cliente.controller.dto.ClienteDTO;
import com.devlu.futuresistema_cliente.entities.ClienteEntity;
import com.devlu.futuresistema_cliente.entities.EnderecoEntity;
import com.devlu.futuresistema_cliente.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço que implementa a lógica de negócios para gerenciamento de clientes.
 *
 * <p>Esta classe fornece métodos para buscar, listar, inserir, atualizar e excluir clientes.</p>
 */
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    /**
     * Construtor para injeção de dependência.
     *
     * @param clienteRepository O repositório de clientes.
     * @param clienteMapper O mapper de clientes.
     */
    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    /**
     * Busca um cliente por ID.
     *
     * @param id O ID do cliente a ser buscado.
     * @return Um {@link ClienteDTO} com os dados do cliente.
     * @throws ResourceNotFoundException Se o cliente não for encontrado.
     */
    @Transactional
    public ClienteDTO findById(Long id) {
        Optional<ClienteEntity> cliente = clienteRepository.findById(id);
        return cliente.map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + id));
    }

    /**
     * Lista todos os clientes.
     *
     * @return Uma lista de {@link ClienteDTO} com os dados de todos os clientes.
     */
    @Transactional
    public List<ClienteDTO> findAll() {
        List<ClienteEntity> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Insere um novo cliente.
     *
     * @param clienteRequestDTO O {@link ClienteRequestDTO} com os dados do cliente a ser inserido.
     * @return Um {@link ClienteDTO} com os dados do cliente inserido.
     */
    @Transactional
    public ClienteDTO insert(ClienteRequestDTO clienteRequestDTO) {
        EnderecoEntity enderecoEntity = clienteMapper.paraEnderecoEntity(clienteRequestDTO);
        ClienteEntity clienteEntity = clienteMapper.paraClienteEntity(clienteRequestDTO);
        clienteEntity.setEndereco(enderecoEntity);

        ClienteEntity savedCliente = clienteRepository.save(clienteEntity);
        return toDTO(savedCliente);
    }

    /**
     * Exclui um cliente por ID.
     *
     * @param id O ID do cliente a ser excluído.
     * @throws ResourceNotFoundException Se o cliente não for encontrado.
     * @throws DatabaseException Se ocorrer uma violação de integridade do banco de dados.
     */
    @Transactional
    public void delete(Long id) {
        try {
            clienteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Cliente não encontrado com o ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade do banco de dados");
        }
    }

    /**
     * Atualiza um cliente existente.
     *
     * @param id O ID do cliente a ser atualizado.
     * @param clienteRequestDTO O {@link ClienteRequestDTO} com os dados do cliente a serem atualizados.
     * @return Um {@link ClienteDTO} com os dados do cliente atualizado.
     * @throws ResourceNotFoundException Se o cliente não for encontrado.
     */
    @Transactional
    public ClienteDTO update(Long id, ClienteRequestDTO clienteRequestDTO) {
        Optional<ClienteEntity> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            ClienteEntity clienteEntity = clienteOptional.get();
            clienteEntity.setNome(clienteRequestDTO.getNome());
            clienteEntity.setEmail(clienteRequestDTO.getEmail());
            clienteEntity.setTelefone(clienteRequestDTO.getTelefone());

            EnderecoEntity enderecoEntity = clienteEntity.getEndereco();
            enderecoEntity.setCep(clienteRequestDTO.getCep());
            enderecoEntity.setLogradouro(clienteRequestDTO.getLogradouro());
            enderecoEntity.setNumero(clienteRequestDTO.getNumero()
                    != null ? Long.parseLong(clienteRequestDTO.getNumero()) : null);
            enderecoEntity.setComplemento(clienteRequestDTO.getComplemento());
            enderecoEntity.setBairro(clienteRequestDTO.getBairro());
            enderecoEntity.setCidade(clienteRequestDTO.getCidade());
            enderecoEntity.setEstado(clienteRequestDTO.getEstado());

            ClienteEntity updatedCliente = clienteRepository.save(clienteEntity);
            return toDTO(updatedCliente);
        } else {
            throw new ResourceNotFoundException("Cliente não encontrado com o ID: " + id);
        }
    }

    private ClienteDTO toDTO(ClienteEntity clienteEntity) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(clienteEntity.getId());
        dto.setNome(clienteEntity.getNome());
        dto.setEmail(clienteEntity.getEmail());
        dto.setTelefone(clienteEntity.getTelefone());
        if (clienteEntity.getEndereco() != null) {
            dto.setEndereco(clienteEntity.getEndereco().getLogradouro() + ", " + clienteEntity.getEndereco().getNumero());
        }
        return dto;
    }
}