package com.dbserver.votacao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String cpf;
}
