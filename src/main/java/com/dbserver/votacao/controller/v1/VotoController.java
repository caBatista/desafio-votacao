package com.dbserver.votacao.controller.v1;

import com.dbserver.votacao.dto.PautaResponseDTO;
import com.dbserver.votacao.dto.VotoResponseDTO;
import com.dbserver.votacao.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/votos")
public class VotoController {
	
	@Autowired
	private VotoService votoService;
	
	@GetMapping
	public ResponseEntity<Page<VotoResponseDTO>> buscaTodosVotos(@PageableDefault(size = 10) Pageable pageable) {
		var page = votoService.buscaTodosVotos(pageable);
		
		if (page.getTotalElements() == 0) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(page.map(VotoResponseDTO :: new));
	}
	
	@GetMapping("{votoId}")
	public ResponseEntity<VotoResponseDTO> buscaotoPorId(@PathVariable Long votoId) {
		var voto = votoService.buscaVotoPorId(votoId);
		var dto = new VotoResponseDTO(voto);
		
		return ResponseEntity.ok(dto);
	}
}
