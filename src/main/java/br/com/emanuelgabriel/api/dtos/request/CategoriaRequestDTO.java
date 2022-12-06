package br.com.emanuelgabriel.api.dtos.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
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
