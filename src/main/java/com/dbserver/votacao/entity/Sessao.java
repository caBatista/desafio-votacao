package com.dbserver.votacao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sessoes")
public class Sessao {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sessaoId;
	
	private LocalDateTime inicio;
	
	private LocalDateTime fim;
	
	private Duration duracao = Duration.ofMinutes(1);
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Pauta pauta;
	
	@OneToMany(mappedBy = "sessao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Voto> votos;
}
