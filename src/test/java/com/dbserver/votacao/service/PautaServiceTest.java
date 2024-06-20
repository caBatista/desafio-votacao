package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.PautaRequestDTO;
import com.dbserver.votacao.entity.Pauta;
import com.dbserver.votacao.repository.PautaJpaRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {
	
	@Mock
	private PautaJpaRepository pautaJpaRepository;
	
	@InjectMocks
	private PautaService pautaService;
	
	private Validator validator;
	
	@BeforeEach
	public void configura() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void deveCriarPauta() {
		Pauta pauta = Pauta.builder()
				.titulo("TituloTeste")
				.descricao("DescricaoTeste")
				.build();
		
		PautaRequestDTO pautaRequestDTO = new PautaRequestDTO(pauta);
		
		when(pautaJpaRepository.save(Mockito.any(Pauta.class))).thenReturn(pauta);
		
		Pauta pautaCriada = pautaService.criaPauta(pautaRequestDTO);
		
		assertNotNull(pautaCriada);
		assertEquals(pauta.getTitulo(), pautaCriada.getTitulo());
		assertEquals(pauta.getDescricao(), pautaCriada.getDescricao());
		verify(pautaJpaRepository).save(Mockito.any(Pauta.class));
	}
	
	@Test
	public void deveLancarExcecaoQuandoTituloVazio() {
		Pauta pauta = Pauta.builder()
				.titulo("")
				.descricao("DescricaoTeste")
				.build();
		
		var violations = validator.validate(pauta);
		
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void deveLancarExcecaoQuandoDescricaoVazia() {
		Pauta pauta = Pauta.builder()
				.titulo("TituloTeste")
				.descricao("")
				.build();
		
		var violations = validator.validate(pauta);
		
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void deveRetornarTodasPautas() {
		List<Pauta> pautas = new ArrayList<>();
		pautas.add(Pauta.builder().titulo("Titulo1").descricao("Descricao1").build());
		pautas.add(Pauta.builder().titulo("Titulo2").descricao("Descricao2").build());
		
		Page<Pauta> pautaPage = new PageImpl<>(pautas);
		
		when(pautaJpaRepository.findAll(PageRequest.of(0, 10))).thenReturn(pautaPage);
		
		Page<Pauta> returnedPage = pautaService.buscaTodasPautas(PageRequest.of(0, 10));
		
		assertEquals(pautaPage, returnedPage);
		verify(pautaJpaRepository).findAll(PageRequest.of(0, 10));
	}
	
	@Test
	public void deveAcharPautaQuandoExiste() {
		Pauta pauta = new Pauta();
		when(pautaJpaRepository.findById(1L)).thenReturn(Optional.of(pauta));
		
		Pauta result = pautaService.buscaPautaPorId(1L);
		
		assertEquals(pauta, result);
	}
	
	@Test
	public void deveLancarExcecaoQuandoNaoExiste() {
		assertThrows(IllegalArgumentException.class, () -> {
			pautaService.buscaPautaPorId(1L);
		});
	}
}
