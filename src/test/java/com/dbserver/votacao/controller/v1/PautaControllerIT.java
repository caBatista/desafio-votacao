package com.dbserver.votacao.controller.v1;

import com.dbserver.votacao.dto.PautaRequestDTO;
import com.dbserver.votacao.entity.Pauta;
import com.dbserver.votacao.enums.StatusPauta;
import com.dbserver.votacao.service.PautaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PautaControllerIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PautaService pautaService;
	
	@Test
	void deveCriarPauta() throws Exception {
		Pauta pauta = Pauta.builder()
				.titulo("Pauta 1")
				.descricao("Descricao da Pauta 1")
				.status(StatusPauta.ABERTA)
				.build();
		
		when(pautaService.criaPauta(any(PautaRequestDTO.class))).thenReturn(pauta);
		
		mockMvc.perform(post("/api/v1/pautas")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{ \"titulo\": \"Pauta 1\", \"descricao\": \"Descricao da Pauta 1\" }"))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"));
	}
}