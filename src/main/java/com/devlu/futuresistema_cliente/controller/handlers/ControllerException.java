package com.devlu.futuresistema_cliente.controller.handlers;

import com.devlu.futuresistema_cliente.business.DatabaseException;
import com.devlu.futuresistema_cliente.business.ResourceNotFoundException;
import com.devlu.futuresistema_cliente.controller.dto.CustomError;
import com.devlu.futuresistema_cliente.controller.dto.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

/**
 * Classe para tratamento de exceções no controller.
 *
 * <p>Esta classe utiliza a anotação {@link ControllerAdvice} para interceptar exceções lançadas pelos controllers
 * e retornar respostas de erro personalizadas.</p>
 */
@ControllerAdvice
public class ControllerException {

    /**
     * Trata exceções do tipo {@link ResourceNotFoundException}.
     *
     * @param e       A exceção {@link ResourceNotFoundException} capturada.
     * @param request O objeto {@link HttpServletRequest} da requisição.
     * @return Um {@link ResponseEntity} com o erro personalizado.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND; //= erro 404
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    /**
     * Trata exceções do tipo {@link DatabaseException}.
     *
     * @param e       A exceção {@link DatabaseException} capturada.
     * @param request O objeto {@link HttpServletRequest} da requisição.
     * @return Um {@link ResponseEntity} com o erro personalizado.
     */
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> database(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST; //= erro 400
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    /**
     * Trata exceções do tipo {@link MethodArgumentNotValidException}.
     *
     * @param e       A exceção {@link MethodArgumentNotValidException} capturada.
     * @param request O objeto {@link HttpServletRequest} da requisição.
     * @return Um {@link ResponseEntity} com o erro de validação personalizado.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;//= erro 422
        ValidationError err = new ValidationError(Instant.now(), status.value(), "Dados inválidos", request.getRequestURI());

        //for abaixo é uma lista de todos os erros previstos nas anotations do ProductDTO
        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            err.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }
}