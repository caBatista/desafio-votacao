package com.dbserver.votacao.controller.v1;

import com.dbserver.votacao.dto.PautaRequestDTO;
import com.dbserver.votacao.dto.PautaResponseDTO;
import com.dbserver.votacao.service.PautaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/v1/pautas")
public class PautaController {
	
	@Autowired
	private PautaService pautaService;
	
	@PostMapping
	public ResponseEntity<PautaResponseDTO> criaPauta(@RequestBody @Valid PautaRequestDTO pautaRequestDTO, UriComponentsBuilder uriBuilder) {
		var pautaCriada = pautaService.criaPauta(pautaRequestDTO);
		var dto = new PautaResponseDTO(pautaCriada);
		
		var uri = uriBuilder.path("api/v1/pautas/{id}").buildAndExpand(pautaCriada.getPautaId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
	
	@GetMapping
	public ResponseEntity<Page<PautaResponseDTO>> buscaTodasPautas(@PageableDefault(size = 10) Pageable pageable) {
		var page = pautaService.buscaTodasPautas(pageable);
		
		if (page.getTotalElements() == 0) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(page.map(PautaResponseDTO :: new));
	}
	
	@GetMapping("{pautaId}")
	public ResponseEntity<PautaResponseDTO> buscaPautaPorId(@PathVariable Long pautaId) {
		var pauta = pautaService.buscaPautaPorId(pautaId);
		var dto = new PautaResponseDTO(pauta);
		
		return ResponseEntity.ok(dto);
	}
}
