package com.dbserver.votacao.repository;

import com.dbserver.votacao.entity.Sessao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SessaoJpaRepository extends JpaRepository<Sessao, Long> {
	Optional<Sessao> findFirstByPautaPautaIdOrderByFimDesc(Long pautaId);
	
	Page<Sessao> findAllByPautaPautaId(Long pautaId, Pageable pageable);
	
	Page<Sessao> findByFimAfter(LocalDateTime fim, Pageable pageable);
}
