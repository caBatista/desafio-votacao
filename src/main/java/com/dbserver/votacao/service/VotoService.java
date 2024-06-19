package com.dbserver.votacao.service;

import com.dbserver.votacao.repository.VotoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoService {
	
	@Autowired
	private VotoJpaRepository votoJpaRepository;
}
