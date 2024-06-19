package com.dbserver.votacao.controller.v1;

import com.dbserver.votacao.service.AssociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/associado")
public class AssociadoController {
	
	@Autowired
	private AssociadoService associadoService;
}
