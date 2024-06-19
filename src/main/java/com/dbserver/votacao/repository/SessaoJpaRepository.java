package com.dbserver.votacao.repository;

import com.dbserver.votacao.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoJpaRepository extends JpaRepository<Sessao, Long> {
}
