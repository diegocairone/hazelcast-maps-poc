package com.cairone.hzsb.tasks;

import java.io.Serializable;

public class TaskImplProductoria implements Task<Integer>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer[] numeros = null;

	public TaskImplProductoria(Integer... numeros) {
		this.numeros = numeros;
	}
	
	@Override
	public Integer execute() {
		Integer resultado = 1;
		if(numeros != null && numeros.length > 0) {
			for(Integer numero : numeros) {
				resultado = resultado * numero;
			}
		}
		return resultado;
	}

}
