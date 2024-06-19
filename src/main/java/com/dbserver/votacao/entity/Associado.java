package com.dbserver.votacao.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "associados")
public class Associado {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long associadoId;
	
	private String name;
	private String cpf;
}
