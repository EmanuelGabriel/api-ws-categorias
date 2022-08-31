package br.com.emanuelgabriel.api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponseDTO {

    private Long codigo;
    private String nome;
    private String descricao;
    private LocalDateTime dataCadastro;

}
