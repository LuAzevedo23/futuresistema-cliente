// File: src/main/java/com/devlu/futuresistema_cliente/business/ClienteService.java
package com.devlu.futuresistema_cliente.business;
import com.devlu.futuresistema_cliente.api.ClienteRequestDTO;
import com.devlu.futuresistema_cliente.api.EnderecoRequestDTO;
import com.devlu.futuresistema_cliente.controller.dto.ClienteDTO;
import com.devlu.futuresistema_cliente.controller.dto.EnderecoDTO;
import com.devlu.futuresistema_cliente.entities.AuditEntry;
import com.devlu.futuresistema_cliente.entities.Cliente;
import com.devlu.futuresistema_cliente.entities.Endereco;
import com.devlu.futuresistema_cliente.entities.StatusCliente;
import com.devlu.futuresistema_cliente.repository.AuditEntryRepository;
import com.devlu.futuresistema_cliente.repository.ClienteRepository;
import com.devlu.futuresistema_cliente.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Serviço de negócios para operações relacionadas a Clientes.
 * Gerencia a lógica de conversão entre DTOs e entidades, validações e persistência.
 */
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private AuditEntryRepository auditEntryRepository; // Injetando o repositório de auditoria

    // Padrão regex para validação de e-mail - Mais robusto e comprovado
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^(?!/.)(?!.*/./.)([A-Za-z0-9_/-/.])+/@([A-Za-z0-9_/-/.])+/.([A-Za-z]{2,})$");

    /**
     * Converte um ClienteDTO em uma entidade Cliente.
     * @param dto O ClienteDTO a ser convertido.
     * @return A entidade Cliente.
     */
    private Cliente toEntity(ClienteDTO dto) {
        if (dto == null) return null;
        Cliente entity = new Cliente();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        try {
            entity.setStatus(StatusCliente.valueOf(dto.getStatus()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido para cliente: " + dto.getStatus());
        }

        if (dto.getEndereco() != null) {
            entity.setEndereco(toEntity(dto.getEndereco()));
        }
        return entity;
    }

    /**
     * Converte uma entidade Cliente em ClienteDTO.
     * @param entity A entidade Cliente a ser convertida.
     * @return O ClienteDTO.
     */
    private ClienteDTO toDTO(Cliente entity) {
        if (entity == null) return null;
        ClienteDTO dto = new ClienteDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setTelefone(entity.getTelefone());
        dto.setStatus(entity.getStatus().name());

        if (entity.getEndereco() != null) {
            dto.setEndereco(toDTO(entity.getEndereco()));
        }
        return dto;
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
        // Se a entidade Endereco tiver campo status, descomente:
        // entity.setStatus(dto.getStatus());
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
        // Se a entidade Endereco tiver campo status, descomente:
        // dto.setStatus(entity.getStatus());
        return dto;
    }

    /**
     * Converte um ClienteRequestDTO para ClienteDTO.
     * @param requestDTO O ClienteRequestDTO a ser convertido.
     * @return O ClienteDTO correspondente.
     */
    private ClienteDTO toClienteDTO(ClienteRequestDTO requestDTO) {
        if (requestDTO == null) return null;
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome(requestDTO.getNome());
        clienteDTO.setEmail(requestDTO.getEmail());
        clienteDTO.setTelefone(requestDTO.getTelefone());
        clienteDTO.setStatus(requestDTO.getStatus());
        if (requestDTO.getEndereco() != null) {
            clienteDTO.setEndereco(toEnderecoDTO(requestDTO.getEndereco()));
        }
        return clienteDTO;
    }

    /**
     * Converte um EnderecoRequestDTO para EnderecoDTO.
     * @param requestDTO O EnderecoRequestDTO a ser convertido.
     * @return O EnderecoDTO correspondente.
     */
    private EnderecoDTO toEnderecoDTO(EnderecoRequestDTO requestDTO) {
        if (requestDTO == null) return null;
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setId(requestDTO.getId()); // O ID pode vir da requisição para endereços existentes
        enderecoDTO.setCep(requestDTO.getCep());
        enderecoDTO.setLogradouro(requestDTO.getLogradouro());
        enderecoDTO.setNumero(requestDTO.getNumero());
        enderecoDTO.setComplemento(requestDTO.getComplemento());
        enderecoDTO.setBairro(requestDTO.getBairro());
        enderecoDTO.setCidade(requestDTO.getCidade());
        enderecoDTO.setEstado(requestDTO.getEstado());
        return enderecoDTO;
    }

    /**
     * Salva um novo cliente no banco de dados.
     * Este é o método interno que lida com a persistência de um ClienteDTO.
     * @param clienteDTO Os dados do cliente a serem salvos.
     * @return O ClienteDTO do cliente salvo.
     * @throws IllegalArgumentException se os dados forem inválidos.
     */
    @Transactional
    public ClienteDTO save(ClienteDTO clienteDTO) {
        validateClienteDTO(clienteDTO); // Valida DTO para criação

        // Se o clienteDTO já tiver um ID, ele deveria ser um update
        if (clienteDTO.getId() != null) {
            throw new IllegalArgumentException("Cliente já possui ID. Use o método 'update' para atualizar.");
        }
        // Verifica se já existe cliente com o mesmo email
        if (clienteRepository.findByEmail(clienteDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este e-mail.");
        }

        Cliente cliente = toEntity(clienteDTO);

        Cliente savedCliente = clienteRepository.save(cliente);

        // Registra a auditoria
        auditEntryRepository.save(new AuditEntry("Cliente", savedCliente.getId(), "CREATE", "Novo cliente criado. Nome: " + savedCliente.getNome()));
        if (savedCliente.getEndereco() != null) {
            auditEntryRepository.save(new AuditEntry("Endereco", savedCliente.getEndereco().getId(), "CREATE", "Endereço criado para cliente: " + savedCliente.getNome()));
        }

        return toDTO(savedCliente);
    }

    /**
     * Insere um novo cliente a partir de um ClienteRequestDTO.
     * Este é o método que o Controller de API deve chamar para criação.
     * @param clienteRequestDTO Os dados do cliente para inserção.
     * @return O ClienteDTO do cliente criado.
     */
    @Transactional
    public ClienteDTO insert(ClienteRequestDTO clienteRequestDTO) { // Renomeado de 'create' para 'insert'
        return save(toClienteDTO(clienteRequestDTO));
    }

    /**
     * Atualiza um cliente existente no banco de dados a partir de um ClienteDTO.
     * Este é o método principal de atualização que pode ser chamado internamente
     * ou por camadas que já manipulam ClienteDTO (como a interface JavaFX).
     * @param clienteDTO O DTO do cliente com os dados a serem atualizados. Deve conter o ID.
     * @return O ClienteDTO do cliente atualizado.
     * @throws IllegalArgumentException se o cliente não for encontrado ou dados forem inválidos.
     */
    @Transactional
    public ClienteDTO update(ClienteDTO clienteDTO) { // NOVO MÉTODO PARA ATUALIZAÇÃO VIA CLIENTEDTO
        validateClienteDTO(clienteDTO); // Valida os dados do DTO

        if (clienteDTO.getId() == null) {
            throw new IllegalArgumentException("ID do cliente é obrigatório para atualização.");
        }
        Cliente existingCliente = clienteRepository.findById(clienteDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + clienteDTO.getId()));

        // Verifica se o e-mail mudou e se o novo e-mail já existe para outro cliente
        if (!existingCliente.getEmail().equals(clienteDTO.getEmail())) {
            if (clienteRepository.findByEmail(clienteDTO.getEmail())
                    .filter(c -> !c.getId().equals(clienteDTO.getId())) // Ignora o próprio cliente
                    .isPresent()) {
                throw new IllegalArgumentException("Já existe outro cliente cadastrado com este e-mail.");
            }
        }
        // Atualiza os campos do cliente existente com os dados do DTO
        existingCliente.setNome(clienteDTO.getNome());
        existingCliente.setEmail(clienteDTO.getEmail());
        existingCliente.setTelefone(clienteDTO.getTelefone());
        try {
            existingCliente.setStatus(StatusCliente.valueOf(clienteDTO.getStatus()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido para cliente: " + clienteDTO.getStatus());
        }

        // Lógica para atualização do endereço
        if (clienteDTO.getEndereco() != null) {
            EnderecoDTO enderecoDTO = clienteDTO.getEndereco();
            Endereco enderecoEntity;
            if (existingCliente.getEndereco() != null &&
                    enderecoDTO.getId() != null &&
                    existingCliente.getEndereco().getId().equals(enderecoDTO.getId())) {
                // Endereço já existe e o ID do DTO corresponde: atualiza o existente
                enderecoEntity = existingCliente.getEndereco();
            } else if (enderecoDTO.getId() != null) {
                // DTO do endereço tem ID, mas não corresponde ao do cliente existente ou cliente não tem endereço.
                // Busca o endereço existente por ID para associar ou verifica se o ID é inválido.
                enderecoEntity = enderecoRepository.findById(enderecoDTO.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Endereço com ID " + enderecoDTO.getId() + " não encontrado."));
            } else {
                // DTO do endereço não tem ID: cria um novo endereço
                enderecoEntity = new Endereco();
            }
            // Copia as propriedades do DTO para a entidade de endereço
            enderecoEntity.setCep(enderecoDTO.getCep());
            enderecoEntity.setLogradouro(enderecoDTO.getLogradouro());
            enderecoEntity.setNumero(enderecoDTO.getNumero());
            enderecoEntity.setComplemento(enderecoDTO.getComplemento());
            enderecoEntity.setBairro(enderecoDTO.getBairro());
            enderecoEntity.setCidade(enderecoDTO.getCidade());
            enderecoEntity.setEstado(enderecoDTO.getEstado());
            // enderecoEntity.setStatus(enderecoDTO.getStatus()); // Descomente se tiver status no Endereco

            existingCliente.setEndereco(enderecoEntity); // Associa o endereço (novo ou atualizado) ao cliente
        } else {
            // Se o EnderecoDTO é nulo, significa que o endereço deve ser removido do cliente
            existingCliente.setEndereco(null); // orphanRemoval=true garante que o endereço será deletado
        }
        Cliente updatedCliente = clienteRepository.save(existingCliente); // Salva o cliente (e endereço via cascade)

        // Registra a auditoria
        auditEntryRepository.save(new AuditEntry("Cliente", updatedCliente.getId(), "UPDATE", "Cliente atualizado. Nome: " + updatedCliente.getNome()));
        if (updatedCliente.getEndereco() != null) {
            auditEntryRepository.save(new AuditEntry("Endereco", updatedCliente.getEndereco().getId(), "UPDATE", "Endereço atualizado para cliente: " + updatedCliente.getNome()));
        } else {
            auditEntryRepository.save(new AuditEntry("Cliente", updatedCliente.getId(), "UPDATE", "Endereço removido do cliente: " + updatedCliente.getNome()));
        }

        return toDTO(updatedCliente);
    }


    /**
     * Atualiza um cliente existente no banco de dados a partir de um ClienteRequestDTO.
     * Este é o método que o Controller de API REST deve chamar para atualização.
     * @param id O ID do cliente a ser atualizado.
     * @param clienteRequestDTO Os dados atualizados do cliente.
     * @return O ClienteDTO do cliente atualizado.
     * @throws IllegalArgumentException se o cliente não for encontrado ou dados forem inválidos.
     */
    @Transactional
    public ClienteDTO update(Long id, ClienteRequestDTO clienteRequestDTO) { // MÉTODO DE ATUALIZAÇÃO VIA REQUESTDTO (PARA REST API)
        // Converte o ClienteRequestDTO em ClienteDTO e define o ID.
        // A validação de @Valid já ocorreu no controller.
        ClienteDTO clienteDTO = toClienteDTO(clienteRequestDTO);
        clienteDTO.setId(id); // Garante que o ID da URL seja usado para a atualização.
        // Delega a lógica de atualização para o método update(ClienteDTO)
        return update(clienteDTO);
    }

    /**
     * Busca todos os clientes no banco de dados.
     * @return Uma lista de ClienteDTOs.
     */
    @Transactional(readOnly = true)
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um cliente pelo ID.
     * @param id O ID do cliente.
     * @return O ClienteDTO encontrado ou null se não existir.
     */
    @Transactional(readOnly = true)
    public ClienteDTO findById(Long id) {
        return clienteRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    /**
     * Exclui um cliente pelo ID.
     * @param id O ID do cliente a ser excluído.
     * @throws IllegalArgumentException se o cliente não for encontrado.
     */
    @Transactional
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado para exclusão: " + id));
        String clienteNome = cliente.getNome(); // Pega o nome antes de deletar
        Long clienteId = cliente.getId();
        Long enderecoId = (cliente.getEndereco() != null) ? cliente.getEndereco().getId() : null;

        clienteRepository.delete(cliente); // Exclui o cliente e o endereço (se orphanRemoval=true)
        // Registra a auditoria
        auditEntryRepository.save(new AuditEntry("Cliente", clienteId, "DELETE", "Cliente excluído. Nome: " + clienteNome));
        if (enderecoId != null) { // O endereço também foi removido
            auditEntryRepository.save(new AuditEntry("Endereco", enderecoId, "DELETE", "Endereço associado ao cliente " + clienteNome + " foi excluído."));
        }
    }

    /**
     * Busca entradas de auditoria para um cliente específico.
     * @param clientId O ID do cliente.
     * @return Uma lista de AuditEntry.
     */
    @Transactional(readOnly = true)
    public List<AuditEntry> findAuditEntriesForClient(Long clientId) {
        List<AuditEntry> clienteAudits = auditEntryRepository.findByEntityTypeAndEntityIdOrderByTimestampAsc("Cliente", clientId);
        // Busca o cliente para obter o ID do endereço, se existir
        clienteRepository.findById(clientId).ifPresent(cliente -> {
            if (cliente.getEndereco() != null) {
                Long enderecoId = cliente.getEndereco().getId();
                List<AuditEntry> enderecoAudits = auditEntryRepository.findByEntityTypeAndEntityIdOrderByTimestampAsc("Endereco", enderecoId);
                clienteAudits.addAll(enderecoAudits);
            }
        });
        // Ordena todas as entradas por timestamp
        clienteAudits.sort((e1, e2) -> e1.getTimestamp().compareTo(e2.getTimestamp()));
        return clienteAudits;
    }

    /**
     * Valida os dados de um ClienteDTO.
     * @param dto O ClienteDTO a ser validado.
     * @throws IllegalArgumentException se algum campo obrigatório estiver ausente ou inválido.
     */
    private void validateClienteDTO(ClienteDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Dados do cliente não podem ser nulos.");
        }
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório.");
        }
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email do cliente é obrigatório.");
        }
        if (!EMAIL_PATTERN.matcher(dto.getEmail()).matches()) {
            throw new IllegalArgumentException("Formato de e-mail inválido.");
        }
        if (dto.getTelefone() == null || dto.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone do cliente é obrigatório.");
        }
        if (dto.getStatus() == null || dto.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Status do cliente é obrigatório.");
        }
        // Tenta converter para o enum para validar se é um status válido
        try {
            StatusCliente.valueOf(dto.getStatus());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status de cliente inválido: " + dto.getStatus() + ". Valores permitidos: ATIVO, INATIVO, EXCLUIDO.");
        }
        if (dto.getEndereco() != null) {
            validateEnderecoDTO(dto.getEndereco());
        }
    }

    /**
     * Valida os dados de um EnderecoDTO.
     * @param dto O EnderecoDTO a ser validado.
     * @throws IllegalArgumentException se algum campo obrigatório estiver ausente ou inválido.
     */
    private void validateEnderecoDTO(EnderecoDTO dto) {
        if (dto.getCep() == null || dto.getCep().trim().isEmpty()) {
            throw new IllegalArgumentException("CEP do endereço é obrigatório.");
        }
        if (dto.getLogradouro() == null || dto.getLogradouro().trim().isEmpty()) {
            throw new IllegalArgumentException("Logradouro do endereço é obrigatório.");
        }
        if (dto.getBairro() == null || dto.getBairro().trim().isEmpty()) {
            throw new IllegalArgumentException("Bairro do endereço é obrigatório.");
        }
        if (dto.getCidade() == null || dto.getCidade().trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade do endereço é obrigatória.");
        }
        if (dto.getEstado() == null || dto.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("Estado do endereço é obrigatório.");
        }
    }
}