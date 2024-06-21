package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.AssociadoRequestDTO;
import com.dbserver.votacao.entity.Associado;
import com.dbserver.votacao.repository.AssociadoJpaRepository;
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
class AssociadoServiceTest {
	
	@Mock
	private AssociadoJpaRepository associadoJpaRepository;
	
	@InjectMocks
	private AssociadoService associadoService;
	
	private Validator validator;
	
	@BeforeEach
	void configura() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void deveCriarAssociado() {
		Associado associado = Associado.builder()
				.nome("Nome Teste")
				.cpf("111.111.111-11")
				.build();
		
		AssociadoRequestDTO associadoRequestDTO = new AssociadoRequestDTO(associado);
		
		when(associadoJpaRepository.save(Mockito.any(Associado.class))).thenReturn(associado);
		
		Associado associadoCriado = associadoService.criaAssociado(associadoRequestDTO);
		
		assertNotNull(associadoCriado);
		assertEquals(associado.getNome(), associadoCriado.getNome());
		assertEquals(associado.getCpf(), associadoCriado.getCpf());
		verify(associadoJpaRepository).save(Mockito.any(Associado.class));
	}
	
	@Test
	void deveLancarExcecaoQuandoNomeVazio() {
		Associado associado = Associado.builder()
				.nome("")
				.cpf("111.111.111-11")
				.build();
		
		var violations = validator.validate(associado);
		
		assertFalse(violations.isEmpty());
	}
	
	@Test
	void deveLancarExcecaoQuandoCpfVazio() {
		Associado associado = Associado.builder()
				.nome("Nome Teste")
				.cpf("")
				.build();
		
		var violations = validator.validate(associado);
		
		assertFalse(violations.isEmpty());
	}
	
	@Test
	void deveRetornarTodosAssociados() {
		List<Associado> associados = new ArrayList<>();
		associados.add(Associado.builder().nome("Nome Um").cpf("111-111-111.11").build());
		associados.add(Associado.builder().nome("Nome Dois").cpf("222.222.222-22").build());
		
		Page<Associado> associadoPage = new PageImpl<>(associados);
		
		when(associadoJpaRepository.findAll(PageRequest.of(0, 10))).thenReturn(associadoPage);
		
		Page<Associado> returnedPage = associadoService.buscaTodosAssociados(PageRequest.of(0, 10));
		
		assertEquals(associadoPage, returnedPage);
		verify(associadoJpaRepository).findAll(PageRequest.of(0, 10));
	}
	
	@Test
	void deveAcharAssociadoQuandoExiste() {
		Associado associado = new Associado();
		when(associadoJpaRepository.findById(1L)).thenReturn(Optional.of(associado));
		
		Associado result = associadoService.buscaAssociadoPorId(1L);
		
		assertEquals(associado, result);
	}
	
	@Test
	void deveLancarExcecaoQuandoAssociadoNaoExiste() {
		assertThrows(IllegalArgumentException.class, () -> associadoService.buscaAssociadoPorId(1L));
	}
}
