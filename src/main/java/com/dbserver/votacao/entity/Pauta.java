package com.dbserver.votacao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pautas")
public class Pauta {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pautaId;
	
	private String titulo;
	
	private String descricao;
	
	@OneToMany(mappedBy = "pauta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Sessao> sessoes;
}
