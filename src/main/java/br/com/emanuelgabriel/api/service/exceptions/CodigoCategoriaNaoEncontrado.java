package br.com.emanuelgabriel.api.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CodigoCategoriaNaoEncontrado extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CodigoCategoriaNaoEncontrado(String mensagem) {
        super(mensagem);
    }
}
