package com.cairone.hzsb.tasks;

import java.io.Serializable;
import java.time.LocalDate;

public class TaskKey implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private LocalDate fechaOperativa = null;
	private Long numeroTransaccion = null;
	
	public TaskKey() {}

	public TaskKey(LocalDate fechaOperativa, Long numeroTransaccion) {
		super();
		this.fechaOperativa = fechaOperativa;
		this.numeroTransaccion = numeroTransaccion;
	}
	
	public TaskKey(Long numeroTransaccion) {
		this(LocalDate.now(), numeroTransaccion);
	}

	public LocalDate getFechaOperativa() {
		return fechaOperativa;
	}

	public void setFechaOperativa(LocalDate fechaOperativa) {
		this.fechaOperativa = fechaOperativa;
	}

	public Long getNumeroTransaccion() {
		return numeroTransaccion;
	}

	public void setNumeroTransaccion(Long numeroTransaccion) {
		this.numeroTransaccion = numeroTransaccion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fechaOperativa == null) ? 0 : fechaOperativa.hashCode());
		result = prime
				* result
				+ ((numeroTransaccion == null) ? 0 : numeroTransaccion
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskKey other = (TaskKey) obj;
		if (fechaOperativa == null) {
			if (other.fechaOperativa != null)
				return false;
		} else if (!fechaOperativa.equals(other.fechaOperativa))
			return false;
		if (numeroTransaccion == null) {
			if (other.numeroTransaccion != null)
				return false;
		} else if (!numeroTransaccion.equals(other.numeroTransaccion))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("[%s - %s]", fechaOperativa, numeroTransaccion);
	}
}
