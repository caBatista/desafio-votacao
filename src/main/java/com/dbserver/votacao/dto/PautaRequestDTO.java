package com.dbserver.votacao.dto;

import com.dbserver.votacao.entity.Pauta;
import jakarta.validation.constraints.NotBlank;

public record PautaRequestDTO(
		@NotBlank
		String titulo,
		@NotBlank
		String descricao
) {
	public PautaRequestDTO(Pauta pauta) {
		this(
				pauta.getTitulo(),
				pauta.getDescricao()
		);
	}
}
