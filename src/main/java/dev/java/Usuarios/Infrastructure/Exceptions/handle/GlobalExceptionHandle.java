package dev.java.Usuarios.Infrastructure.Exceptions.handle;

import dev.java.Usuarios.Infrastructure.Exceptions.ConflictException;
import dev.java.Usuarios.Infrastructure.Exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandle {

    // todas as três exceções tem a mesma lógica, só muda o status HTTP que retorna e qual exceção captura


    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, String>> handleConflict(ConflictException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error); // 409
    }

    // Anotação faz com que sempre que o sistema lançar essa excessão, ele seja interceptado por esse método
    @ExceptionHandler(ResourceNotFoundException.class)
    // é entregada automaticamente pelo Spring a exceção lançada
    public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException ex) {
        // monta um corpo de resposta simples, padronizado — um JSON com a chave "error" e o valor sendo a mensagem
        // que você escreveu ao lançar a exceção
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); // 404
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRunTime(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error); // 400
    }


}
