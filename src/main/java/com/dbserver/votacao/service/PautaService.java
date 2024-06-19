package com.dbserver.votacao.service;

import com.dbserver.votacao.repository.PautaJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PautaService {

	@Autowired
	private PautaJpaRepository pautaJpaRepository;
}
