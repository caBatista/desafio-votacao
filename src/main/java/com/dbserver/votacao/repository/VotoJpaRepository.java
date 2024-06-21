package com.dbserver.votacao.repository;

import com.dbserver.votacao.entity.Associado;
import com.dbserver.votacao.entity.Pauta;
import com.dbserver.votacao.entity.Voto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoJpaRepository extends JpaRepository<Voto, Long> {
	Page<Voto> findByPautaPautaId(Long pautaId, Pageable pageable);
	
	Boolean existsByPautaAndAssociado(Pauta pauta, Associado associado);
}
