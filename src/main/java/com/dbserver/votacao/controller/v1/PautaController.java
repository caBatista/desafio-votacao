package com.dbserver.votacao.controller.v1;

import com.dbserver.votacao.dto.PautaRequestDTO;
import com.dbserver.votacao.dto.PautaResponseDTO;
import com.dbserver.votacao.dto.SessaoResponseDTO;
import com.dbserver.votacao.dto.VotoResponseDTO;
import com.dbserver.votacao.service.PautaService;
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
@RequestMapping("api/v1/pautas")
public class PautaController {
	
	@Autowired
	private PautaService pautaService;
	
	@PostMapping
	@Operation(summary = "Cria uma nova pauta", description = "Este endpoint é usado para criar uma nova pauta.")
	public ResponseEntity<PautaResponseDTO> criaPauta(@RequestBody @Valid PautaRequestDTO pautaRequestDTO, UriComponentsBuilder uriBuilder) {
		var pautaCriada = pautaService.criaPauta(pautaRequestDTO);
		var dto = new PautaResponseDTO(pautaCriada);
		
		var uri = uriBuilder.path("api/v1/pautas/{id}").buildAndExpand(pautaCriada.getPautaId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
	
	@GetMapping
	@Operation(summary = "Busca todas as pautas", description = "Este endpoint é usado para buscar todas as pautas.")
	public ResponseEntity<Page<PautaResponseDTO>> buscaTodasPautas(@PageableDefault() Pageable pageable) {
		var page = pautaService.buscaTodasPautas(pageable);
		
		if (page.getTotalElements() == 0) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(page.map(PautaResponseDTO :: new));
	}
	
	@GetMapping("{pautaId}")
	@Operation(summary = "Busca uma pauta por ID", description = "Este endpoint é usado para buscar uma pauta específica pelo seu ID.")
	public ResponseEntity<PautaResponseDTO> buscaPautaPorId(@PathVariable Long pautaId) {
		var pauta = pautaService.buscaPautaPorId(pautaId);
		var dto = new PautaResponseDTO(pauta);
		
		return ResponseEntity.ok(dto);
	}

	@PostMapping("{pautaId}/abrir-sessao")
	@Operation(summary = "Abre uma sessão para uma pauta", description = "Este endpoint é usado para abrir uma sessão para uma pauta específica.")
	public ResponseEntity<SessaoResponseDTO> abreSessao(@PathVariable Long pautaId, @RequestParam(required = false) Integer duracaoEmMinutos, UriComponentsBuilder uriBuilder) {
		var sessao = pautaService.abreSessao(pautaId, duracaoEmMinutos);
		var dto = new SessaoResponseDTO(sessao);

		var uri = uriBuilder.path("api/v1/pautas/{pautaId}/sessoes/{sessaoId}").buildAndExpand(pautaId, dto.id()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

	@GetMapping("{pautaId}/sessoes/abertas")
	@Operation(summary = "Busca sessão aberta por pauta", description = "Este endpoint é usado para buscar a sessão aberta de uma pauta específica.")
	public ResponseEntity<SessaoResponseDTO> buscaSessaoAbertaPorPauta(@PathVariable Long pautaId) {
		var sessao = pautaService.buscaSessaoAbertaPorPautaId(pautaId);
		var dto = new SessaoResponseDTO(sessao);

		return ResponseEntity.ok(dto);
	}

	@GetMapping("{pautaId}/sessoes")
	@Operation(summary = "Busca sessões por pauta", description = "Este endpoint é usado para buscar todas as sessões de uma pauta específica.")
	public ResponseEntity<Page<SessaoResponseDTO>> buscaSessoesPorPauta(@PathVariable Long pautaId, @PageableDefault() Pageable pageable) {
		var page = pautaService.buscaSessoesPorPautaId(pautaId, pageable);

		if (page.getTotalElements() == 0) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(page.map(SessaoResponseDTO::new));
	}
	
	@PostMapping("{pautaId}/votar")
	@Operation(summary = "Registra um voto em uma pauta", description = "Este endpoint é usado para registrar um voto em uma pauta específica.")
	public ResponseEntity<VotoResponseDTO> vota(@PathVariable Long pautaId, @RequestParam Long associadoId, @RequestParam String voto, UriComponentsBuilder uriBuilder) {
		var votoCriado = pautaService.vota(pautaId, associadoId, voto);
		var dto = new VotoResponseDTO(votoCriado);
		
		var uri = uriBuilder.path("api/v1/votos/{votoId}")
				.buildAndExpand(votoCriado.getPauta().getPautaId(), votoCriado.getVotoId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
	
	@GetMapping("{pautaId}/votos")
	@Operation(summary = "Busca votos por pauta", description = "Este endpoint é usado para buscar todos os votos de uma pauta específica.")
	public ResponseEntity<Page<VotoResponseDTO>> buscaVotosPorPauta(@PathVariable Long pautaId, @PageableDefault() Pageable pageable) {
		var page = pautaService.buscaVotosPorPauta(pautaId, pageable);
		
		if (page.getTotalElements() == 0) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(page.map(VotoResponseDTO :: new));
	}
	
	@PostMapping("{pautaId}/encerrar")
	@Operation(summary = "Encerra uma pauta", description = "Este endpoint é usado para encerrar uma pauta específica.")
	public ResponseEntity<PautaResponseDTO> encerraPauta(@PathVariable Long pautaId) {
		var pautaEncerrada = pautaService.encerraPauta(pautaId);
		var dto = new PautaResponseDTO(pautaEncerrada);
		
		return ResponseEntity.accepted().body(dto);
	}
}