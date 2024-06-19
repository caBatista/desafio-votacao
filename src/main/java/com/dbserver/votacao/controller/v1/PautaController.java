package com.dbserver.votacao.controller.v1;

import com.dbserver.votacao.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/pauta")
public class PautaController {
	
	@Autowired
	private PautaService pautaService;
}
