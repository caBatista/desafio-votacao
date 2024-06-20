package com.dbserver.votacao.dto;

import com.dbserver.votacao.entity.Associado;
import jakarta.validation.constraints.NotBlank;

public record AssociadoRequestDTO(
		@NotBlank
		String nome,
		@NotBlank
		String cpf
) {
	public AssociadoRequestDTO(Associado associado) {
		this(
				associado.getNome(),
				associado.getCpf()
		);
	}
}
