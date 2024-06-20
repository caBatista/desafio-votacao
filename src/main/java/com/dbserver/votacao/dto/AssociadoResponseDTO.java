package com.dbserver.votacao.dto;

import com.dbserver.votacao.entity.Associado;

public record AssociadoResponseDTO(
		Long id,
		String nome,
		String cpf
) {
	public AssociadoResponseDTO(Associado associado) {
		this(
				associado.getAssociadoId(),
				associado.getNome(),
				associado.getCpf()
		);
	}
}
