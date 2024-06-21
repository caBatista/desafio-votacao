package com.dbserver.votacao.entity;

import com.dbserver.votacao.enums.EscolhaVoto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "votos")
public class Voto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long votoId;
	
	@Enumerated(EnumType.STRING)
	private EscolhaVoto escolhaVoto;
	
	@ManyToOne
	private Associado associado;
	
	@ManyToOne
	private Pauta pauta;
}
