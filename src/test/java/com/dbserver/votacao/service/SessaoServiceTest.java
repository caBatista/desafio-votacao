package com.dbserver.votacao.service;

import com.dbserver.votacao.entity.Sessao;
import com.dbserver.votacao.exception.NenhumaSessaoAbertaException;
import com.dbserver.votacao.repository.SessaoJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessaoServiceTest {
	
	@Mock
	private SessaoJpaRepository sessaoJpaRepository;
	
	@InjectMocks
	private SessaoService sessaoService;
	
	@Test
	void deveCriarSessao() {
		Sessao sessao = new Sessao();
		when(sessaoJpaRepository.save(any(Sessao.class))).thenReturn(sessao);
		
		Sessao result = sessaoService.criaSessao(sessao);
		
		assertNotNull(result);
		assertEquals(sessao, result);
	}
	
	@Test
	void deveLancarExcessaoQuandoExisteSessaoAberta() {
		Sessao sessao = new Sessao();
		sessao.setFim(LocalDateTime.now().plusMinutes(10));
		when(sessaoJpaRepository.findFirstByPautaPautaIdOrderByFimDesc(anyLong())).thenReturn(Optional.of(sessao));
		
		assertTrue(sessaoService.validaSessaoAbertaPorPautaId(1L));
	}
	
	@Test
	void deveBuscarSessoesPorPautaId() {
		Page<Sessao> page = Page.empty();
		when(sessaoJpaRepository.findAllByPautaPautaId(anyLong(), any())).thenReturn(page);
		
		Page<Sessao> result = sessaoService.buscaSessoesPorPautaId(1L, PageRequest.of(0, 10));
		
		assertEquals(page, result);
	}
	
	@Test
	void deveRetornarVazioQuandoNaoExistemSessoesPorPautaId() {
		when(sessaoJpaRepository.findAllByPautaPautaId(anyLong(), any())).thenReturn(Page.empty());
		
		Page<Sessao> result = sessaoService.buscaSessoesPorPautaId(1L, PageRequest.of(0, 10));
		
		assertTrue(result.isEmpty());
	}
	
	@Test
	void deveBuscarSessaoAbertaPorPautaId() {
		Sessao sessao = new Sessao();
		sessao.setFim(LocalDateTime.now().plusMinutes(10));
		when(sessaoJpaRepository.findFirstByPautaPautaIdOrderByFimDesc(anyLong())).thenReturn(Optional.of(sessao));
		
		Sessao result = sessaoService.buscaSessaoAbertaPorPautaId(1L);
		
		assertNotNull(result);
		assertEquals(sessao, result);
	}
	
	@Test
	void deveLancarExcecaoQuandoNaoExisteSessaoAbertaPorPautaId() {
		when(sessaoJpaRepository.findFirstByPautaPautaIdOrderByFimDesc(anyLong())).thenReturn(Optional.empty());
		
		assertThrows(NenhumaSessaoAbertaException.class, () -> sessaoService.buscaSessaoAbertaPorPautaId(1L));
	}
	
	@Test
	void deveBuscarSessoesAbertas() {
		Sessao sessao = Sessao.builder()
				.fim(LocalDateTime.now().plusMinutes(10))
				.build();
		when(sessaoJpaRepository.save(any(Sessao.class))).thenReturn(sessao);
		when(sessaoJpaRepository.findByFimAfter(any(), any())).thenReturn(new PageImpl<>(List.of(sessao)));
		
		sessaoService.criaSessao(sessao);
		Page<Sessao> result = sessaoService.buscaSessoesAbertas(PageRequest.of(0, 10));
		
		assertFalse(result.isEmpty());
		assertEquals(sessao, result.getContent().get(0));
	}
	
	@Test
	void deveLancarExcecaooQuandoNaoExistemSessoesAbertas() {
		when(sessaoJpaRepository.findByFimAfter(any(), any())).thenReturn(Page.empty());
		
		Pageable pageable = PageRequest.of(0, 10);
		
		assertThrows(NenhumaSessaoAbertaException.class, () -> sessaoService.buscaSessoesAbertas(pageable));
	}
}