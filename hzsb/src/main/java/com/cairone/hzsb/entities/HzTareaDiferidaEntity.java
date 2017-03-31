package com.cairone.hzsb.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.cairone.hzsb.tasks.TaskKey;

@Entity @Table(name="hazelcast_tareasdiferidas")
public class HzTareaDiferidaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId private HzTareaDiferidaPkEntity pk = null;
	
	@Column(name="fecha_operativa", nullable=false, insertable=false, updatable=false)
	private LocalDate fechaOperativa = null;
	
	@Column(name="numero_transaccion", nullable=false, insertable=false, updatable=false)
	private Long numeroTransaccion = null;
	
	@Column(name="serialized_object", nullable=false) @Lob
	private byte[] serializedObject = null;
	
	public HzTareaDiferidaEntity() {
		pk = new HzTareaDiferidaPkEntity();
	}

	public HzTareaDiferidaEntity(LocalDate fechaOperativa, Long numeroTransaccion) {
		super();
		this.fechaOperativa = fechaOperativa;
		this.numeroTransaccion = numeroTransaccion;
		this.pk = new HzTareaDiferidaPkEntity(fechaOperativa, numeroTransaccion);
	}

	public HzTareaDiferidaEntity(TaskKey taskKey) {
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

	public byte[] getSerializedObject() {
		return serializedObject;
	}

	public void setSerializedObject(byte[] serializedObject) {
		this.serializedObject = serializedObject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
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
		HzTareaDiferidaEntity other = (HzTareaDiferidaEntity) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
}
