package com.dbserver.votacao.dto;

import com.dbserver.votacao.entity.Voto;

public record VotoResponseDTO(
		Long pautaId,
		String associado,
		String pauta,
		String escolhaVoto
) {
	public VotoResponseDTO(Voto voto) {
		this(
				voto.getPauta().getPautaId(),
				voto.getAssociado().getNome(),
				voto.getPauta().getTitulo(),
				voto.getEscolhaVoto().name()
		);
	}
}
