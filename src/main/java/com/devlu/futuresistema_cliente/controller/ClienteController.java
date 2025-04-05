package com.devlu.futuresistema_cliente.controller;

import com.devlu.futuresistema_cliente.api.ClienteRequestDTO;
import com.devlu.futuresistema_cliente.business.ClienteService;
import com.devlu.futuresistema_cliente.controller.dto.ClienteDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controlador que gerencia as requisições da API para o gerenciamento de clientes.
 *
 * <p>Esta classe define os endpoints para buscar, listar, inserir, atualizar e excluir clientes.</p>
 */
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Construtor para injeção de dependência.
     *
     * @param clienteService O serviço de clientes.
     */
    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Busca um cliente por ID.
     *
     * @param id O ID do cliente a ser buscado.
     * @return Um {@link ResponseEntity} com o {@link ClienteDTO} do cliente encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.findById(id);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Lista todos os clientes.
     *
     * @return Um {@link ResponseEntity} com a lista de {@link ClienteDTO} de todos os clientes.
     */
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<ClienteDTO> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Insere um novo cliente.
     *
     * @param clienteRequestDTO O {@link ClienteRequestDTO} com os dados do cliente a ser inserido.
     * @return Um {@link ResponseEntity} com o {@link ClienteDTO} do cliente inserido e o header Location.
     */
    @PostMapping
    public ResponseEntity<ClienteDTO> insert(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteDTO cliente = clienteService.insert(clienteRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(cliente);
    }

    /**
     * Exclui um cliente por ID.
     *
     * @param id O ID do cliente a ser excluído.
     * @return Um {@link ResponseEntity} com status 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza um cliente existente.
     *
     * @param id              O ID do cliente a ser atualizado.
     * @param clienteRequestDTO O {@link ClienteRequestDTO} com os dados do cliente a serem atualizados.
     * @return Um {@link ResponseEntity} com o {@link ClienteDTO} do cliente atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteDTO cliente = clienteService.update(id, clienteRequestDTO);
        return ResponseEntity.ok(cliente);
    }
}
