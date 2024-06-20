package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.PautaRequestDTO;
import com.dbserver.votacao.entity.Pauta;
import com.dbserver.votacao.repository.PautaJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PautaService {

	@Autowired
	private PautaJpaRepository pautaJpaRepository;
	
	public Pauta criaPauta(PautaRequestDTO pautaRequestDTO) {
		Pauta novaPauta = Pauta.builder()
				.titulo(pautaRequestDTO.titulo())
				.descricao(pautaRequestDTO.descricao())
				.build();
		
		return pautaJpaRepository.save(novaPauta);
	}
	
	public Pauta buscaPautaPorId(Long pautaId) {
		return pautaJpaRepository.findById(pautaId)
				.orElseThrow(() -> new IllegalArgumentException("Pauta não encontrada"));
	}
	
	public Page<Pauta> buscaTodasPautas(Pageable pageable) {
		Page<Pauta> pautas = pautaJpaRepository.findAll(pageable);
		
		return pautas;
	}
}
