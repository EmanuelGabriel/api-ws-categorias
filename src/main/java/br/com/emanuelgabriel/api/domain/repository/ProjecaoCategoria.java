package br.com.emanuelgabriel.api.domain.repository;

import java.time.LocalDateTime;

public interface ProjecaoCategoria {

    Long getCodigo();

    String getNome();

    String getDescricao();

    LocalDateTime getDataCadastro();


}
