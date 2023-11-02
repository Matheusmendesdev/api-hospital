package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import jdk.javadoc.doclet.Reporter;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.Field;

@RestControllerAdvice
public class TratadorErros {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosDetalhamentoErro400::new).toList());
    }

    private record DadosDetalhamentoErro400(String nome, String mensagem){
        public DadosDetalhamentoErro400(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
