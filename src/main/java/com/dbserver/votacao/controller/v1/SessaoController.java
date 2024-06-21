package com.dbserver.votacao.controller.v1;

import com.dbserver.votacao.dto.SessaoResponseDTO;
import com.dbserver.votacao.service.SessaoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/sessoes")
public class SessaoController {
	
	@Autowired
	private SessaoService sessaoService;
	
	@GetMapping
	@Operation(summary = "Busca todas as sessões", description = "Este endpoint é usado para buscar todas as sessões.")
	public ResponseEntity<Page<SessaoResponseDTO>> buscaTodasSessoes(@PageableDefault() Pageable pageable) {
		var page = sessaoService.buscaTodasSessoes(pageable);
		
		if (page.getTotalElements() == 0) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(page.map(SessaoResponseDTO::new));
	}
	
	@GetMapping("abertas")
	@Operation(summary = "Busca sessões abertas", description = "Este endpoint é usado para buscar todas as sessões que estão atualmente abertas.")
	public ResponseEntity<Page<SessaoResponseDTO>> buscaSessoesAbertas(@PageableDefault() Pageable pageable) {
		var page = sessaoService.buscaSessoesAbertas(pageable);
		
		if (page.getTotalElements() == 0) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(page.map(SessaoResponseDTO::new));
	}
}