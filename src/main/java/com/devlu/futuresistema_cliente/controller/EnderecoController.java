package com.devlu.futuresistema_cliente.controller;

import com.devlu.futuresistema_cliente.api.EnderecoRequestDTO;
import com.devlu.futuresistema_cliente.business.EnderecoService;
import com.devlu.futuresistema_cliente.controller.dto.EnderecoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controlador que gerencia as requisições da API para o gerenciamento de endereços.
 *
 * <p>Esta classe define os endpoints para buscar, listar, inserir, atualizar e excluir endereços.</p>
 */
@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    /**
     * Construtor para injeção de dependência.
     *
     * @param enderecoService O serviço de endereços.
     */
    @Autowired
    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    /**
     * Busca um endereço por ID.
     *
     * @param id O ID do endereço a ser buscado.
     * @return Um {@link ResponseEntity} com o {@link EnderecoDTO} do endereço encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> findById(@PathVariable Long id) {
        EnderecoDTO endereco = enderecoService.findById(id);
        return ResponseEntity.ok(endereco);
    }

    /**
     * Lista todos os endereços.
     *
     * @return Um {@link ResponseEntity} com a lista de {@link EnderecoDTO} de todos os endereços.
     */
    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> findAll() {
        List<EnderecoDTO> enderecos = enderecoService.findAll();
        return ResponseEntity.ok(enderecos);
    }

    /**
     * Insere um novo endereço.
     *
     * @param enderecoRequestDTO O {@link EnderecoRequestDTO} com os dados do endereço a ser inserido.
     * @return Um {@link ResponseEntity} com o {@link EnderecoDTO} do endereço inserido e o header Location.
     */
    @PostMapping
    public ResponseEntity<EnderecoDTO> insert(@Valid @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        EnderecoDTO enderecoDTO = EnderecoDTO.builder()
                .cep(enderecoRequestDTO.getCep())
                .logradouro(enderecoRequestDTO.getLogradouro())
                .numero(enderecoRequestDTO.getNumero())
                .complemento(enderecoRequestDTO.getComplemento())
                .bairro(enderecoRequestDTO.getBairro())
                .cidade(enderecoRequestDTO.getCidade())
                .estado(enderecoRequestDTO.getEstado())
                .status(enderecoRequestDTO.getStatus())
                .build();

        EnderecoDTO endereco = enderecoService.insert(enderecoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(endereco.getId()).toUri();
        return ResponseEntity.created(uri).body(endereco);
    }

    /**
     * Exclui um endereço por ID.
     *
     * @param id O ID do endereço a ser excluído.
     * @return Um {@link ResponseEntity} com status 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza um endereço existente.
     *
     * @param id               O ID do endereço a ser atualizado.
     * @param enderecoRequestDTO O {@link EnderecoRequestDTO} com os dados do endereço a serem atualizados.
     * @return Um {@link ResponseEntity} com o {@link EnderecoDTO} do endereço atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> update(@PathVariable Long id, @Valid @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        EnderecoDTO enderecoDTO = EnderecoDTO.builder()
                .cep(enderecoRequestDTO.getCep())
                .logradouro(enderecoRequestDTO.getLogradouro())
                .numero(enderecoRequestDTO.getNumero())
                .complemento(enderecoRequestDTO.getComplemento())
                .bairro(enderecoRequestDTO.getBairro())
                .cidade(enderecoRequestDTO.getCidade())
                .estado(enderecoRequestDTO.getEstado())
                .status(enderecoRequestDTO.getStatus())
                .build();
        EnderecoDTO endereco = enderecoService.update(id, enderecoDTO);
        return ResponseEntity.ok(endereco);
    }
}



