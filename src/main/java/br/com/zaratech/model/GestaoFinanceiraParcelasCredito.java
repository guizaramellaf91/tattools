package br.com.zaratech.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "gestao_financeira_parcelas_credito")
@Entity
public class GestaoFinanceiraParcelasCredito {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gestaoFinanceiraParcelaCreditoId;

	@OneToOne
	@JoinColumn(name = "gestao_financeira_id", nullable = false)
	private GestaoFinanceira gestaoFinanceira;
		
	@Column(name= "data_vencimento")
	private LocalDate dataVencimento;
	
	@Column(name= "valor_parcela")
	private BigDecimal valorParcela;
	
	@Column(name= "valor_parcela_studio")
	private BigDecimal valorParcelaStudio;
	
	@Column(name= "valor_parcela_tatuador")
	private BigDecimal valorParcelaTatuador;
	
	@Column(name= "recebido")
	private boolean recebido;

	public Long getGestaoFinanceiraParcelaCreditoId() {
		return gestaoFinanceiraParcelaCreditoId;
	}

	public void setGestaoFinanceiraParcelaCreditoId(Long gestaoFinanceiraParcelaCreditoId) {
		this.gestaoFinanceiraParcelaCreditoId = gestaoFinanceiraParcelaCreditoId;
	}

	public GestaoFinanceira getGestaoFinanceira() {
		return gestaoFinanceira;
	}

	public void setGestaoFinanceira(GestaoFinanceira gestaoFinanceira) {
		this.gestaoFinanceira = gestaoFinanceira;
	}
	
	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public BigDecimal getValorParcelaStudio() {
		return valorParcelaStudio;
	}

	public void setValorParcelaStudio(BigDecimal valorParcelaStudio) {
		this.valorParcelaStudio = valorParcelaStudio;
	}

	public BigDecimal getValorParcelaTatuador() {
		return valorParcelaTatuador;
	}

	public void setValorParcelaTatuador(BigDecimal valorParcelaTatuador) {
		this.valorParcelaTatuador = valorParcelaTatuador;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}

	public boolean isRecebido() {
		return recebido;
	}

	public void setRecebido(boolean recebido) {
		this.recebido = recebido;
	}
}