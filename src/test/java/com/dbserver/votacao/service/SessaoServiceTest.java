package com.dbserver.votacao.service;

import com.dbserver.votacao.entity.Sessao;
import com.dbserver.votacao.exceptions.AberturaSessaoException;
import com.dbserver.votacao.exceptions.NenhumaSessaoAbertaException;
import com.dbserver.votacao.repository.SessaoJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {
	
	@Mock
	private SessaoJpaRepository sessaoJpaRepository;
	
	@InjectMocks
	private SessaoService sessaoService;
	
	@Test
	public void deveCriarSessao() {
		Sessao sessao = new Sessao();
		when(sessaoJpaRepository.save(any(Sessao.class))).thenReturn(sessao);
		
		Sessao result = sessaoService.criaSessao(sessao);
		
		assertNotNull(result);
		assertEquals(sessao, result);
	}
	
	@Test
	public void deveLancarExcessaoQuandoExisteSessaoAberta() {
		Sessao sessao = new Sessao();
		sessao.setFim(LocalDateTime.now().plusMinutes(10));
		when(sessaoJpaRepository.findFirstByPautaPautaIdOrderByFimDesc(anyLong())).thenReturn(Optional.of(sessao));
		
		assertThrows(AberturaSessaoException.class, () -> sessaoService.validaSessaoAbertaPorPautaId(1L));
	}
	
	@Test
	public void deveBuscarSessoesPorPautaId() {
		Page<Sessao> page = Page.empty();
		when(sessaoJpaRepository.findAllByPautaPautaId(anyLong(), any())).thenReturn(page);
		
		Page<Sessao> result = sessaoService.buscaSessoesPorPautaId(1L, PageRequest.of(0, 10));
		
		assertEquals(page, result);
	}
	
	@Test
	public void deveRetornarVazioQuandoNaoExistemSessoesPorPautaId() {
		when(sessaoJpaRepository.findAllByPautaPautaId(anyLong(), any())).thenReturn(Page.empty());
		
		Page<Sessao> result = sessaoService.buscaSessoesPorPautaId(1L, PageRequest.of(0, 10));
		
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void deveBuscarSessaoAbertaPorPautaId() {
		Sessao sessao = new Sessao();
		sessao.setFim(LocalDateTime.now().plusMinutes(10));
		when(sessaoJpaRepository.findFirstByPautaPautaIdOrderByFimDesc(anyLong())).thenReturn(Optional.of(sessao));
		
		Sessao result = sessaoService.buscaSessaoAbertaPorPautaId(1L);
		
		assertNotNull(result);
		assertEquals(sessao, result);
	}
	
	@Test
	public void deveLancarExcecaoQuandoNaoExisteSessaoAbertaPorPautaId() {
		when(sessaoJpaRepository.findFirstByPautaPautaIdOrderByFimDesc(anyLong())).thenReturn(Optional.empty());
		
		assertThrows(NenhumaSessaoAbertaException.class, () -> sessaoService.buscaSessaoAbertaPorPautaId(1L));
	}
	
	@Test
	public void deveBuscarSessoesAbertas() {
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
	public void deveLancarExcecaooQuandoNaoExistemSessoesAbertas() {
		when(sessaoJpaRepository.findByFimAfter(any(), any())).thenReturn(Page.empty());
		
		assertThrows(NenhumaSessaoAbertaException.class, () -> sessaoService.buscaSessoesAbertas(PageRequest.of(0, 10)));
	}
}