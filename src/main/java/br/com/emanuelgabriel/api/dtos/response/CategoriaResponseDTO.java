package br.com.emanuelgabriel.api.dtos.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponseDTO {

	private Long codigo;
	private String nome;
	private String descricao;
	private Boolean ativa;
	private LocalDateTime dataCadastro;
	private LocalDateTime dataAtualizacao;

}
