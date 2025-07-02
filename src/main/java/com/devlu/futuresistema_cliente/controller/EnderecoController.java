package com.devlu.futuresistema_cliente.controller;

import com.devlu.futuresistema_cliente.api.EnderecoRequestDTO;
import com.devlu.futuresistema_cliente.business.EnderecoService;
import com.devlu.futuresistema_cliente.controller.dto.EnderecoDTO;
import jakarta.validation.Valid; // Importe para validação
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST para operações relacionadas a Endereços.
 * Expõe endpoints para gerenciar endereços.
 */
@RestController
@RequestMapping("/enderecos") // Endpoint base para endereços
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService; // Injetando o novo EnderecoService

    /**
     * Insere um novo endereço.
     * @param enderecoRequestDTO O DTO com os dados do endereço a ser inserido.
     * @return ResponseEntity com o EnderecoDTO do endereço criado e status 201 Created.
     */
    @PostMapping
    public ResponseEntity<EnderecoDTO> insert(@Valid @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        // O EnderecoService já espera o EnderecoRequestDTO e faz as conversões internas.
        EnderecoDTO endereco = enderecoService.insert(enderecoRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(endereco.getId()).toUri();
        return ResponseEntity.created(uri).body(endereco);
    }

    /**
     * Atualiza um endereço existente.
     * @param id O ID do endereço a ser atualizado.
     * @param enderecoRequestDTO O DTO com os dados atualizados do endereço.
     * @return ResponseEntity com o EnderecoDTO do endereço atualizado e status 200 OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> update(@PathVariable Long id, @Valid @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        // O EnderecoService já espera o EnderecoRequestDTO e faz as conversões internas.
        // Não é mais necessário chamar enderecoMapper aqui.
        EnderecoDTO endereco = enderecoService.update(id, enderecoRequestDTO);
        return ResponseEntity.ok(endereco);
    }

    /**
     * Busca um endereço pelo ID.
     * @param id O ID do endereço.
     * @return ResponseEntity com o EnderecoDTO do endereço encontrado e status 200 OK, ou 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> findById(@PathVariable Long id) {
        EnderecoDTO endereco = enderecoService.findById(id);
        if (endereco == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(endereco);
    }

    /**
     * Lista todos os endereços.
     * @return ResponseEntity com uma lista de EnderecoDTOs e status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> findAll() {
        List<EnderecoDTO> enderecos = enderecoService.findAll();
        return ResponseEntity.ok(enderecos);
    }

    /**
     * Exclui um endereço pelo ID.
     * @param id O ID do endereço a ser excluído.
     * @return ResponseEntity com status 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}