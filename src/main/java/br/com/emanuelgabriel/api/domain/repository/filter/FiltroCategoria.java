package br.com.emanuelgabriel.api.domain.repository.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class FiltroCategoria implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;

    private String descricao;

    private LocalDateTime dataCadastro;
}
