package com.dbserver.votacao.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class HttpExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("Erro: ", e);
		return ResponseEntity.badRequest().body("Parâmetros inválidos");
	}
	
	@ExceptionHandler(AberturaSessaoException.class)
	public ResponseEntity<String> handleAberturaSessaoException(AberturaSessaoException e) {
		log.error("Erro ao tentar abrir uma sessão: ", e);
		return ResponseEntity.unprocessableEntity().body(e.getMessage());
	}
	
	@ExceptionHandler(NenhumaSessaoAbertaException.class)
	public ResponseEntity<String> handleNenhumaSessaoAbertaException(NenhumaSessaoAbertaException e) {
		log.error("Erro ao encontrar uma sessão aberta: ", e);
		return ResponseEntity.notFound().build();
	}
}