package com.dbserver.votacao.exceptions;

public class NenhumaSessaoAbertaException extends RuntimeException {
	public NenhumaSessaoAbertaException(String message) {
		super(message);
	}
}
