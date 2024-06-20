package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.AssociadoRequestDTO;
import com.dbserver.votacao.entity.Associado;
import com.dbserver.votacao.entity.Pauta;
import com.dbserver.votacao.entity.Sessao;
import com.dbserver.votacao.repository.AssociadoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AssociadoService {
	
	@Autowired
	private AssociadoJpaRepository associadoJpaRepository;
	
	public Associado criaAssociado(AssociadoRequestDTO associadoRequestDTO) {
		Associado novoAssociado = Associado.builder()
				.nome(associadoRequestDTO.nome())
				.cpf(associadoRequestDTO.cpf())
				.build();
		
		return associadoJpaRepository.save(novoAssociado);
	}
	
	public Page<Associado> buscaTodosAssociados(Pageable pageable) {
		Page<Associado> associados = associadoJpaRepository.findAll(pageable);
		
		return associados;
	}
	
	public Associado buscaAssociadoPorId(Long associadoId) {
		return associadoJpaRepository.findById(associadoId)
				.orElseThrow(() -> new IllegalArgumentException("Associado não encontrado"));
	}
}
