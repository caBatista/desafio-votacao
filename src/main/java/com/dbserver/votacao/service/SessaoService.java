package com.dbserver.votacao.service;

import com.dbserver.votacao.repository.SessaoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessaoService {
	
	@Autowired
	private SessaoJpaRepository sessaoJpaRepository;
}
