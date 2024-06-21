package com.dbserver.votacao.service;

import com.dbserver.votacao.entity.Associado;
import com.dbserver.votacao.entity.Pauta;
import com.dbserver.votacao.entity.Voto;
import com.dbserver.votacao.enums.EscolhaVoto;
import com.dbserver.votacao.repository.VotoJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VotoServiceTest {
	
	@Mock
	private VotoJpaRepository votoJpaRepository;
	
	@InjectMocks
	private VotoService votoService;
	
	@Test
	void deveCriarVoto() {
		Pauta pauta = new Pauta();
		Associado associado = new Associado();
		EscolhaVoto escolhaVoto = EscolhaVoto.SIM;
		
		Voto voto = Voto.builder()
				.escolhaVoto(escolhaVoto)
				.pauta(pauta)
				.associado(associado)
				.build();
		
		when(votoJpaRepository.save(any(Voto.class))).thenReturn(voto);
		
		Voto votoCriado = votoService.vota(pauta, associado, escolhaVoto);
		
		assertNotNull(votoCriado);
		assertEquals(voto.getEscolhaVoto(), votoCriado.getEscolhaVoto());
		assertEquals(voto.getPauta(), votoCriado.getPauta());
		assertEquals(voto.getAssociado(), votoCriado.getAssociado());
		verify(votoJpaRepository).save(any(Voto.class));
	}
	
	@Test
	void deveRetornarVotoPorId() {
		Long votoId = 1L;
		Voto voto = new Voto();
		when(votoJpaRepository.findById(votoId)).thenReturn(Optional.of(voto));
		
		Voto result = votoService.buscaVotoPorId(votoId);
		
		assertEquals(voto, result);
		verify(votoJpaRepository).findById(votoId);
	}
	
	@Test
	void deveLancarExcecaoQuandoBuscaVotoPorIdInvalido() {
		Long votoId = 1L;
		when(votoJpaRepository.findById(votoId)).thenReturn(Optional.empty());
		
		assertThrows(IllegalArgumentException.class, () -> votoService.buscaVotoPorId(votoId));
		verify(votoJpaRepository).findById(votoId);
	}
	
	@Test
	void deveRetornarVotosPorPauta() {
		Pauta pauta = new Pauta();
		pauta.setPautaId(1L);
		List<Voto> votos = new ArrayList<>();
		votos.add(new Voto());
		votos.add(new Voto());
		
		Page<Voto> votoPage = new PageImpl<>(votos);
		
		when(votoJpaRepository.findByPautaPautaId(pauta.getPautaId(), PageRequest.of(0, 10))).thenReturn(votoPage);
		
		Page<Voto> returnedPage = votoService.buscaVotoPorPauta(pauta, PageRequest.of(0, 10));
		
		assertEquals(votoPage, returnedPage);
		verify(votoJpaRepository).findByPautaPautaId(pauta.getPautaId(), PageRequest.of(0, 10));
	}
	
	@Test
	void deveRetornarPageVaziaQuandoBuscaVotoPorPautaInvalida() {
		Pauta pauta = new Pauta();
		pauta.setPautaId(1L);
		Pageable pageable = PageRequest.of(0, 10);
		when(votoJpaRepository.findByPautaPautaId(pauta.getPautaId(), pageable)).thenReturn(Page.empty());
		
		Page<Voto> returnedPage = votoService.buscaVotoPorPauta(pauta, pageable);
		
		assertTrue(returnedPage.isEmpty());
		verify(votoJpaRepository).findByPautaPautaId(pauta.getPautaId(), pageable);
	}
}