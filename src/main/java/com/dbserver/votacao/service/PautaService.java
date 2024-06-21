package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.PautaRequestDTO;
import com.dbserver.votacao.entity.Pauta;
import com.dbserver.votacao.entity.Sessao;
import com.dbserver.votacao.entity.Voto;
import com.dbserver.votacao.enums.EscolhaVoto;
import com.dbserver.votacao.enums.StatusPauta;
import com.dbserver.votacao.exception.AberturaSessaoException;
import com.dbserver.votacao.exception.PautaFechadaException;
import com.dbserver.votacao.repository.PautaJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PautaService {
	@Autowired
	private PautaJpaRepository pautaJpaRepository;
	
	@Autowired
	private SessaoService sessaoService;
	
	@Autowired
	private VotoService votoService;
	
	@Autowired
	private AssociadoService associadoService;
	
	public Pauta criaPauta(PautaRequestDTO pautaRequestDTO) {
		Pauta novaPauta = Pauta.builder()
				.titulo(pautaRequestDTO.titulo())
				.descricao(pautaRequestDTO.descricao())
				.status(StatusPauta.ABERTA)
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
	
	public Sessao abreSessao(Long pautaId, Integer duracaoEmMinutos) {
		if(sessaoService.validaSessaoAbertaPorPautaId(pautaId)){
			throw new AberturaSessaoException("Já existe uma sessão aberta para esta pauta");
		}
		
		Pauta pauta = buscaPautaPorId(pautaId);
		
		if(pauta.getStatus() != StatusPauta.ABERTA) {
			throw new PautaFechadaException("A pauta está fechada");
		}
		
		Sessao novaSessao = Sessao.builder()
				.inicio(LocalDateTime.now())
				.fim(LocalDateTime.now().plusMinutes(duracaoEmMinutos != null ? duracaoEmMinutos : 1))
				.pauta(pauta)
				.build();
		
		return sessaoService.criaSessao(novaSessao);
	}
	
	public Page<Sessao> buscaSessoesPorPautaId(Long pautaId, Pageable pageable) {
		var pauta = buscaPautaPorId(pautaId);
		
		return sessaoService.buscaSessoesPorPautaId(pauta.getPautaId(), pageable);
	}
	
	public Sessao buscaSessaoAbertaPorPautaId(Long pautaId) {
		var pauta = buscaPautaPorId(pautaId);
		
		return sessaoService.buscaSessaoAbertaPorPautaId(pauta.getPautaId());
	}
	
	public Voto vota(Long pautaId, Long associadoId, String voto) {
		var pauta = buscaPautaPorId(pautaId);
		buscaSessaoAbertaPorPautaId(pauta.getPautaId());
		
		var associado = associadoService.buscaAssociadoPorId(associadoId);
		var escolhaVoto = EscolhaVoto.valueOf(voto.toUpperCase());
		
		return votoService.vota(pauta, associado, escolhaVoto);
	}
	
	public Pauta encerraPauta(Long pautaId) {
		var pauta = buscaPautaPorId(pautaId);
		
		if(pauta.getStatus() != StatusPauta.ABERTA) {
			throw new PautaFechadaException("A pauta está fechada");
		}
		
		var votos = votoService.buscaVotoPorPauta(pauta, Pageable.unpaged());
		
		if(votos.isEmpty()) {
			pauta.setStatus(StatusPauta.NAO_CONCLUIDA);
		} else {
			var votosSim = votos.stream()
					.filter(voto -> voto.getEscolhaVoto() == EscolhaVoto.SIM)
					.count();
			
			var votosNao = votos.stream()
					.filter(voto -> voto.getEscolhaVoto() == EscolhaVoto.NAO)
					.count();
			
			if(votosSim > votosNao) {
				pauta.setStatus(StatusPauta.APROVADA);
			} else if (votosSim < votosNao) {
				pauta.setStatus(StatusPauta.REPROVADA);
			} else {
				pauta.setStatus(StatusPauta.EMPATADA);
			}
		}
		
		return pautaJpaRepository.save(pauta);
	}
	
	public Page<Voto> buscaVotosPorPauta(Long pautaId, Pageable pageable) {
		var pauta = buscaPautaPorId(pautaId);
		
		return votoService.buscaVotoPorPauta(pauta, pageable);
	}
}
