package com.dbserver.votacao.service;

import com.dbserver.votacao.entity.Associado;
import com.dbserver.votacao.entity.Pauta;
import com.dbserver.votacao.entity.Voto;
import com.dbserver.votacao.enums.EscolhaVoto;
import com.dbserver.votacao.exception.AssociadoJaVotouException;
import com.dbserver.votacao.repository.VotoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VotoService {
	
	@Autowired
	private VotoJpaRepository votoJpaRepository;
	
	public Voto vota(Pauta pauta, Associado associado, EscolhaVoto escolhaVoto) {
		Voto voto = Voto.builder()
				.escolhaVoto(escolhaVoto)
				.pauta(pauta)
				.associado(associado)
				.build();
		
		if(validaAssociadoVotouNaPauta(pauta, associado)) {
			throw new AssociadoJaVotouException("Associado ja votou nesta pauta");
		}
		
		return votoJpaRepository.save(voto);
	}
	
	public Voto buscaVotoPorId(Long votoId) {
		return votoJpaRepository.findById(votoId)
				.orElseThrow(() -> new IllegalArgumentException("Voto não encontrado"));
	}
	
	public Page<Voto> buscaVotoPorPauta(Pauta pauta, Pageable pageable) {
		return votoJpaRepository.findByPautaPautaId(pauta.getPautaId(), pageable);
	}
	
	public Page<Voto> buscaTodosVotos(Pageable pageable) {
		return votoJpaRepository.findAll(pageable);
	}
	
	private boolean validaAssociadoVotouNaPauta(Pauta pauta, Associado associado) {
		return votoJpaRepository.existsByPautaAndAssociado(pauta, associado);
	}
}
