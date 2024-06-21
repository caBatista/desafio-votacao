package com.dbserver.votacao.service;

import com.dbserver.votacao.entity.Sessao;
import com.dbserver.votacao.exception.NenhumaSessaoAbertaException;
import com.dbserver.votacao.repository.SessaoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessaoService {
	
	@Autowired
	private SessaoJpaRepository sessaoJpaRepository;
	
	public Sessao criaSessao(Sessao sessao) {
		return sessaoJpaRepository.save(sessao);
	}
	
	public boolean validaSessaoAbertaPorPautaId(Long pautaId) {
		var sessaoOptional = sessaoJpaRepository.findFirstByPautaPautaIdOrderByFimDesc(pautaId);
		
		if (sessaoOptional.isPresent() &&
				sessaoOptional.get().getFim().isAfter(LocalDateTime.now())) {
			return true;
		}
		
		return false;
	}
	
	public Page<Sessao> buscaSessoesPorPautaId(Long pautaId, Pageable pageable) {
		return sessaoJpaRepository.findAllByPautaPautaId(pautaId, pageable);
	}
	
	public Sessao buscaSessaoAbertaPorPautaId(Long pautaId) {
		var sessao = sessaoJpaRepository.findFirstByPautaPautaIdOrderByFimDesc(pautaId)
				.orElseThrow(() -> new NenhumaSessaoAbertaException("Nenhuma sessão aberta para esta pauta"));
		
		if (sessao.getFim().isBefore(LocalDateTime.now())) {
			throw new NenhumaSessaoAbertaException("Nenhuma sessão aberta para esta pauta");
		}
		
		return sessao;
	}
	
	public Page<Sessao> buscaTodasSessoes(Pageable pageable) {
		return sessaoJpaRepository.findAll(pageable);
	}
	
	public Page<Sessao> buscaSessoesAbertas(Pageable pageable) {
		Page<Sessao> sessoes = sessaoJpaRepository.findByFimAfter(LocalDateTime.now(), pageable);
		if (sessoes.isEmpty()) {
			throw new NenhumaSessaoAbertaException("Nenhuma sessão aberta encontrada");
		}
		return sessoes;
	}
}
