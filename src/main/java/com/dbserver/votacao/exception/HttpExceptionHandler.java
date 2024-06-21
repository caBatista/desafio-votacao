package com.dbserver.votacao.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
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
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
		log.error("Erro: ", e);
		return ResponseEntity.badRequest().body("Parâmetros inválidos");
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		log.error("Erro ao tentar salvar um registro: ", e);
		return ResponseEntity.badRequest().body("Voto já computado para este associado nesta pauta");
	}
	
	@ExceptionHandler(AssociadoJaVotouException.class)
	public ResponseEntity<String> handleAssociadoJaVotouException(AssociadoJaVotouException e) {
		log.error("Erro ao tentar votar: ", e);
		return ResponseEntity.badRequest().body("Associado já votou para esta pauta");
	}
}
