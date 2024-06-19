package com.dbserver.votacao.repository;

import com.dbserver.votacao.entity.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoJpaRepository extends JpaRepository<Associado, Long> {
}
