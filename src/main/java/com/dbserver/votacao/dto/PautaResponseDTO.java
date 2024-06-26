package com.dbserver.votacao.dto;

import com.dbserver.votacao.entity.Pauta;

public record PautaResponseDTO(
		Long id,
		String titulo,
		String descricao,
		String status
) {
	public PautaResponseDTO(Pauta pauta) {
		this(
				pauta.getPautaId(),
				pauta.getTitulo(),
				pauta.getDescricao(),
				pauta.getStatus().name()
		);
	}
}
