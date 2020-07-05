package br.com.zaratech.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "gestao_financeira_saques")
@Entity
public class GestaoFinanceiraSaques {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gestaoFinanceiraSaqueId;
	
	@Column(name= "valor_saque")
	private BigDecimal valorSaque;
			
	@Column(name= "pendente")
	private boolean pendente;
	
	@ManyToOne
	@JoinColumn(name = "gestao_financeira_id", nullable = false)
	private GestaoFinanceira gestaoFinanceira;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	
	@OneToOne
	@JoinColumn(name = "ordem_servico_id")
	private OrdemServico ordemServico;

	public Long getGestaoFinanceiraSaqueId() {
		return gestaoFinanceiraSaqueId;
	}

	public void setGestaoFinanceiraSaqueId(Long gestaoFinanceiraSaqueId) {
		this.gestaoFinanceiraSaqueId = gestaoFinanceiraSaqueId;
	}

	public BigDecimal getValorSaque() {
		return valorSaque;
	}

	public void setValorSaque(BigDecimal valorSaque) {
		this.valorSaque = valorSaque;
	}

	public boolean isPendente() {
		return pendente;
	}

	public void setPendente(boolean pendente) {
		this.pendente = pendente;
	}

	public GestaoFinanceira getGestaoFinanceira() {
		return gestaoFinanceira;
	}

	public void setGestaoFinanceira(GestaoFinanceira gestaoFinanceira) {
		this.gestaoFinanceira = gestaoFinanceira;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}
}