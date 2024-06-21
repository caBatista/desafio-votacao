package com.dbserver.votacao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

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
	
	@NotNull
	private LocalDateTime inicio;
	
	@NotNull
	private LocalDateTime fim;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Pauta pauta;
}
