package com.dbserver.votacao.controller.v1;

import com.dbserver.votacao.dto.SessaoResponseDTO;
import com.dbserver.votacao.service.SessaoService;
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
	public ResponseEntity<Page<SessaoResponseDTO>> buscaTodasSessoes(@PageableDefault(size = 10) Pageable pageable) {
		var page = sessaoService.buscaTodasSessoes(pageable);
		
		if (page.getTotalElements() == 0) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(page.map(SessaoResponseDTO::new));
	}
	
	@GetMapping("abertas")
	public ResponseEntity<Page<SessaoResponseDTO>> buscaSessoesAbertas(@PageableDefault(size = 10) Pageable pageable) {
		var page = sessaoService.buscaSessoesAbertas(pageable);
		
		if (page.getTotalElements() == 0) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(page.map(SessaoResponseDTO::new));
	}
}
