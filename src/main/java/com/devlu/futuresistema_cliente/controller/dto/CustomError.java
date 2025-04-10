package com.devlu.futuresistema_cliente.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

/**
 * Classe que define o formato de erro personalizado para as respostas da API.
 *
 * <p>Esta classe encapsula informações sobre o timestamp, status, mensagem de erro e o caminho da requisição.</p>
 */
@Getter
@AllArgsConstructor
public class CustomError {

    /*
    É o mesmo retorno do Json veja:
    {
    "timestamp": "2025-04-07T21:40:25.910+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "path": "/products/100"
     */

    /**
     * Timestamp do momento em que o erro ocorreu.
     */
    private Instant timestamp;

    /**
     * Código de status HTTP do erro.
     */
    private Integer status;

    /**
     * Mensagem detalhada do erro.
     */
    private String error;

    /**
     * Caminho da requisição que gerou o erro.
     */
    private String path;
}


