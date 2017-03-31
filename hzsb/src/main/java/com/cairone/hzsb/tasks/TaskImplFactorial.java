package com.cairone.hzsb.tasks;

import java.io.Serializable;

public class TaskImplFactorial implements Task<Integer>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer numero = null;

	public TaskImplFactorial(Integer numero) {
		this.numero = numero;
	}
	
	@Override
	public Integer execute() {
		Integer c, resultado = 1;
		for ( c = 1 ; c <= numero ; c++ ) {
			resultado = resultado * c;
		}
		return resultado;
	}

	public Integer getNumero() {
		return numero;
	}
	
}
