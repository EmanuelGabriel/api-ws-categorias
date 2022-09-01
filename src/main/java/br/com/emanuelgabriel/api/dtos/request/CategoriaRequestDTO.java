package br.com.emanuelgabriel.api.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRequestDTO {

    @NotBlank(message = "Campo nome não pode ser vazio ou nulo")
    @Size(max = 30)
    private String nome;

    @NotBlank(message = "Campo descrição não pode ser vazio ou nulo")
    @Size(max = 100)
    private String descricao;

}
