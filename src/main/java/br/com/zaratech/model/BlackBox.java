package br.com.zaratech.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class BlackBox {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long blackBoxId;

	@Column(nullable = false)
	private Date dataCadastro;
	
	@OneToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@Column(nullable = false)
	private String modulo;
	
	@Column(nullable = false)
	private String acao;

	public Long getBlackBoxId() {
		return blackBoxId;
	}

	public void setBlackBoxId(Long blackBoxId) {
		this.blackBoxId = blackBoxId;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}
}