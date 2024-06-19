package com.dbserver.votacao.controller.v1;

import com.dbserver.votacao.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/sessao")
public class SessaoController {
	
	@Autowired
	private SessaoService sessaoService;
}
