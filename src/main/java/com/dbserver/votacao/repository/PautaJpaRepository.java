package com.dbserver.votacao.repository;

import com.dbserver.votacao.entity.Pauta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaJpaRepository extends JpaRepository<Pauta, Long> {
	Page<Pauta> findAll(Pageable pageable);
}
