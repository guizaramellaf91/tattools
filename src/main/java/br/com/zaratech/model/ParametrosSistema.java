package br.com.zaratech.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "parametros_sistema", uniqueConstraints = {@UniqueConstraint(columnNames="chave")})
public class ParametrosSistema {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long parametroId;
	@Column(nullable = false)
	private String chave;
	@Column(nullable = false)
	private String valor;
	@Column(nullable = false)
	private String descricao;
	
	public long getParametroId() {
		return parametroId;
	}
	public void setParametroId(long parametroId) {
		this.parametroId = parametroId;
	}
	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}