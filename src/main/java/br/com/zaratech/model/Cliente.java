package br.com.zaratech.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "cliente", uniqueConstraints = {@UniqueConstraint(columnNames="nome")})
@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long clienteId;

	@Column(nullable = false)
	private String nome;

	@Column
	private String instagram;

	@Column
	private String email;

	@Column
	private String cpf;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column
	private Date dataNascimento;

	@Column
	private String telefone;

	@Column
	private String celular;

	@Column(nullable = false)
	private Date dataCadastro;

	@Column
	private Date dataModificacao;

	@OneToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "endereco_id")
	private Endereco endereco;
	
	@OneToMany(mappedBy = "cliente", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<OrdemServico> ordemServico;

	@Column
	private boolean autorizaFotos;
	
	@Column
	private String tipoSangue;
	
	@Column
	private boolean possuiHepatite;
	
	@Column
	private boolean possuiAlergias;
	
	@Column
	private boolean possuiDiabetes;
	
	@Column
	private boolean tomaAnticoagulante;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getDataModificacao() {
		return dataModificacao;
	}

	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<OrdemServico> getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(List<OrdemServico> ordemServico) {
		this.ordemServico = ordemServico;
	}

	public boolean isAutorizaFotos() {
		return autorizaFotos;
	}

	public void setAutorizaFotos(boolean autorizaFotos) {
		this.autorizaFotos = autorizaFotos;
	}

	public String getTipoSangue() {
		return tipoSangue;
	}

	public void setTipoSangue(String tipoSangue) {
		this.tipoSangue = tipoSangue;
	}

	public boolean isPossuiHepatite() {
		return possuiHepatite;
	}

	public void setPossuiHepatite(boolean possuiHepatite) {
		this.possuiHepatite = possuiHepatite;
	}

	public boolean isPossuiAlergias() {
		return possuiAlergias;
	}

	public void setPossuiAlergias(boolean possuiAlergias) {
		this.possuiAlergias = possuiAlergias;
	}

	public boolean isPossuiDiabetes() {
		return possuiDiabetes;
	}

	public void setPossuiDiabetes(boolean possuiDiabetes) {
		this.possuiDiabetes = possuiDiabetes;
	}

	public boolean isTomaAnticoagulante() {
		return tomaAnticoagulante;
	}

	public void setTomaAnticoagulante(boolean tomaAnticoagulante) {
		this.tomaAnticoagulante = tomaAnticoagulante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clienteId == null) ? 0 : clienteId.hashCode());
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
		Cliente other = (Cliente) obj;
		if (clienteId == null) {
			if (other.clienteId != null)
				return false;
		} else if (!clienteId.equals(other.clienteId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [nome=" + nome + "]";
	}
}