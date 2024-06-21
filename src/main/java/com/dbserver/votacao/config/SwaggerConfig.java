package com.dbserver.votacao.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info =
@Info(title = "DBServer - Desafio Votação",
		description = "Este é um serviço de votação. Ele permite criar pautas, abrir sessões de votação, votar e encerrar pautas.",
		version = "v1"))
public class SwaggerConfig {
}