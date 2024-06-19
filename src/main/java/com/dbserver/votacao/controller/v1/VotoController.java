package com.dbserver.votacao.controller.v1;

import com.dbserver.votacao.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/voto")
public class VotoController {
	
	@Autowired
	private VotoService votoService;
}
