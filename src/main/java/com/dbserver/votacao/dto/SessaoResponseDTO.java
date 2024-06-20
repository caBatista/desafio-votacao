package com.dbserver.votacao.dto;

import com.dbserver.votacao.entity.Sessao;

import java.time.LocalDateTime;

public record SessaoResponseDTO(
		Long id,
		Long pautaId,
		LocalDateTime inicio,
		LocalDateTime fim
) {
	public SessaoResponseDTO(Sessao sessao) {
		this(
				sessao.getSessaoId(),
				sessao.getPauta().getPautaId(),
				sessao.getInicio(),
				sessao.getFim()
		);
	}
}
