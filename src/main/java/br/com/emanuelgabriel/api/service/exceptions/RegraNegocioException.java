package br.com.emanuelgabriel.api.service.exceptions;

public class RegraNegocioException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RegraNegocioException(String mensagem){
        super(mensagem);
    }

}
