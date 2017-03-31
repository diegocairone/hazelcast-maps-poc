package com.cairone.hzsb.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.cairone.hzsb.tasks.TaskKey;

@Embeddable
public class HzTareaDiferidaPkEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="fecha_operativa", nullable = false)
	private LocalDate fechaOperativa = null;
	
	@Column(name="numero_transaccion", nullable = false)
	private Long numeroTransaccion = null;
	
	public HzTareaDiferidaPkEntity() {}

	public HzTareaDiferidaPkEntity(LocalDate fechaOperativa, Long numeroTransaccion) {
		super();
		this.fechaOperativa = fechaOperativa;
		this.numeroTransaccion = numeroTransaccion;
	}
	
	public HzTareaDiferidaPkEntity(TaskKey taskKey) {
		this(taskKey.getFechaOperativa(), taskKey.getNumeroTransaccion());
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
		HzTareaDiferidaPkEntity other = (HzTareaDiferidaPkEntity) obj;
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
}
