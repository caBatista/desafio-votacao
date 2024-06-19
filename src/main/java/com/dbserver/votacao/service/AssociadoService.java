package com.dbserver.votacao.service;

import com.dbserver.votacao.repository.AssociadoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssociadoService {
	
	@Autowired
	private AssociadoJpaRepository associadoJpaRepository;
}
