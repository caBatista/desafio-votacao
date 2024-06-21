package com.dbserver.votacao.controller.v1;

import com.dbserver.votacao.dto.AssociadoRequestDTO;
import com.dbserver.votacao.dto.AssociadoResponseDTO;
import com.dbserver.votacao.service.AssociadoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/v1/associados")
public class AssociadoController {
	
	@Autowired
	private AssociadoService associadoService;
	
	@PostMapping
	@Operation(summary = "Cria um novo associado", description = "Este endpoint é usado para criar um novo associado.")
	public ResponseEntity<AssociadoResponseDTO> criaPauta(@RequestBody @Valid AssociadoRequestDTO associadoRequestDTO, UriComponentsBuilder uriBuilder) {
		var associadoCriado = associadoService.criaAssociado(associadoRequestDTO);
		var dto = new AssociadoResponseDTO(associadoCriado);
		
		var uri = uriBuilder.path("api/v1/associados/{id}").buildAndExpand(associadoCriado.getAssociadoId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
	
	@GetMapping
	@Operation(summary = "Busca todos os associados", description = "Este endpoint é usado para buscar todos os associados.")
	public ResponseEntity<Page<AssociadoResponseDTO>> buscaTodosAssociados(@PageableDefault() Pageable pageable) {
		var page = associadoService.buscaTodosAssociados(pageable);
		
		if (page.getTotalElements() == 0) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(page.map(AssociadoResponseDTO::new));
	}
	
	@GetMapping("{associadoId}")
	@Operation(summary = "Busca um associado por ID", description = "Este endpoint é usado para buscar um associado específico pelo seu ID.")
	public ResponseEntity<AssociadoResponseDTO> buscaAssociadoPorId(@PathVariable Long associadoId) {
		var associado = associadoService.buscaAssociadoPorId(associadoId);
		var dto = new AssociadoResponseDTO(associado);
		
		return ResponseEntity.ok(dto);
	}
}
