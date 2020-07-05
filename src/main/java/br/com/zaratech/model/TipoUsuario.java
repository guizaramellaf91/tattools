package br.com.zaratech.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tipo_usuario", uniqueConstraints = {@UniqueConstraint(columnNames="nomeTipo")})
public class TipoUsuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long tipoUsuarioId;
	
	@Column(nullable = false)
	private String nomeTipo;

	public long getTipoUsuarioId() {
		return tipoUsuarioId;
	}

	public void setTipoUsuarioId(long tipoUsuarioId) {
		this.tipoUsuarioId = tipoUsuarioId;
	}

	public String getNomeTipo() {
		return nomeTipo;
	}

	public void setNomeTipo(String nomeTipo) {
		this.nomeTipo = nomeTipo;
	}
}