package com.dbserver.votacao.controller.v1;

import com.dbserver.votacao.dto.PautaRequestDTO;
import com.dbserver.votacao.dto.PautaResponseDTO;
import com.dbserver.votacao.dto.SessaoResponseDTO;
import com.dbserver.votacao.dto.VotoResponseDTO;
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

	@PostMapping("{pautaId}/abrir-sessao")
	public ResponseEntity<SessaoResponseDTO> abreSessao(@PathVariable Long pautaId, @RequestParam(required = false) Integer duracaoEmMinutos, UriComponentsBuilder uriBuilder) {
		var sessao = pautaService.abreSessao(pautaId, duracaoEmMinutos);
		var dto = new SessaoResponseDTO(sessao);

		var uri = uriBuilder.path("api/v1/pautas/{pautaId}/sessoes/{sessaoId}").buildAndExpand(pautaId, dto.id()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

	@GetMapping("{pautaId}/sessoes/abertas")
	public ResponseEntity<SessaoResponseDTO> buscaSessaoAbertaPorPauta(@PathVariable Long pautaId) {
		var sessao = pautaService.buscaSessaoAbertaPorPautaId(pautaId);
		var dto = new SessaoResponseDTO(sessao);

		return ResponseEntity.ok(dto);
	}

	@GetMapping("{pautaId}/sessoes")
	public ResponseEntity<Page<SessaoResponseDTO>> buscaSessoesPorPauta(@PathVariable Long pautaId, @PageableDefault(size = 10) Pageable pageable) {
		var page = pautaService.buscaSessoesPorPautaId(pautaId, pageable);

		if (page.getTotalElements() == 0) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(page.map(SessaoResponseDTO::new));
	}
	
	@PostMapping("{pautaId}/votar")
	public ResponseEntity<VotoResponseDTO> vota(@PathVariable Long pautaId, @RequestParam Long associadoId, @RequestParam String voto, UriComponentsBuilder uriBuilder) {
		var votoCriado = pautaService.vota(pautaId, associadoId, voto);
		var dto = new VotoResponseDTO(votoCriado);
		
		var uri = uriBuilder.path("api/v1/pautas/{pautaId}/votos/{votoId}")
				.buildAndExpand(votoCriado.getPauta().getPautaId(), votoCriado.getVotoId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
	
}
