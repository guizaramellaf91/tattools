package br.com.zaratech.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "gestao_financeira")
@Entity
public class GestaoFinanceira {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gestaoFinanceiraId;
	
	@Column(name = "data_cadastro", nullable = false)
	private Date dataCadastro;
	
	@Column(name = "data_alteracao")
	private Date dataAlteracao;
	
	@Column(name= "descricao", nullable = false)
	private String descricao;
	
	@Column(name = "valida")
	private boolean valida;
	
	@Column(name = "tatuadorDinheiroRecebido")
	private boolean tatuadorDinheiroRecebido;
	
	@Column(name = "tatuadorDebitoRecebido")
	private boolean tatuadorDebitoRecebido;
	
	@Column(name = "tatuadorCreditoRecebido")
	private boolean tatuadorCreditoRecebido;

	@Column(name = "studioDinheiroRecebido")
	private boolean studioDinheiroRecebido;
	
	@Column(name = "studioDebitoRecebido")
	private boolean studioDebitoRecebido;
	
	@Column(name = "studioCreditoRecebido")
	private boolean studioCreditoRecebido;
	
	@Column(name= "percentual_aplicado_studio_dinheiro", nullable = false)
	private BigDecimal percentualAplicadoStudioDinheiro = BigDecimal.ZERO;

	@Column(name= "percentual_aplicado_studio_debito", nullable = false)
	private BigDecimal percentualAplicadoStudioDebito = BigDecimal.ZERO;
	
	@Column(name= "percentual_aplicado_studio_credito", nullable = false)
	private BigDecimal percentualAplicadoStudioCredito = BigDecimal.ZERO;
		
	@Column(name= "total_comissao_studio_dinheiro", nullable = false)
	private BigDecimal totalComissaoStudioDinheiro = BigDecimal.ZERO;
	
	@Column(name= "total_comissao_studio_debito", nullable = false)
	private BigDecimal totalComissaoStudioDebito = BigDecimal.ZERO;
	
	@Column(name= "total_comissao_studio_credito", nullable = false)
	private BigDecimal totalComissaoStudioCredito = BigDecimal.ZERO;
			
	@Column(name= "total_comissao_studio", nullable = false)
	private BigDecimal totalComissaoStudio = BigDecimal.ZERO;
		
	@Column(name= "percentual_aplicado_tatuador_dinheiro", nullable = false)
	private BigDecimal percentualAplicadoTatuadorDinheiro = BigDecimal.ZERO;

	@Column(name= "percentual_aplicado_tatuador_debito", nullable = false)
	private BigDecimal percentualAplicadoTatuadorDebito = BigDecimal.ZERO;
	
	@Column(name= "percentual_aplicado_tatuador_credito", nullable = false)
	private BigDecimal percentualAplicadoTatuadorCredito = BigDecimal.ZERO;
			
	@Column(name= "total_comissao_tatuador_dinheiro", nullable = false)
	private BigDecimal totalComissaoTatuadorDinheiro = BigDecimal.ZERO;

	@Column(name= "total_comissao_tatuador_debito", nullable = false)
	private BigDecimal totalComissaoTatuadorDebito = BigDecimal.ZERO;
	
	@Column(name= "total_comissao_tatuador_credito", nullable = false)
	private BigDecimal totalComissaoTatuadorCredito = BigDecimal.ZERO;
	
	@Column(name= "total_comissao_tatuador", nullable = false)
	private BigDecimal totalComissaoTatuador = BigDecimal.ZERO;
	
	@Column(name= "percentual_aplicado_tatuador_produto", nullable = false)
	private BigDecimal percentualAplicadoTatuadorProduto = BigDecimal.ZERO;
	
	@Column(name= "percentual_aplicado_studio_produto", nullable = false)
	private BigDecimal percentualAplicadoStudioProduto = BigDecimal.ZERO;
	
	@Column(name= "total_comissao_tatuador_produto", nullable = false)
	private BigDecimal totalComissaoTatuadorProduto = BigDecimal.ZERO;
	
	@Column(name= "total_comissao_studio_produto", nullable = false)
	private BigDecimal totalComissaoStudioProduto = BigDecimal.ZERO;
	
	@Column(name= "total_recebido_studio_dinheiro", nullable = false)
	private BigDecimal totalRecebidoStudioDinheiro = BigDecimal.ZERO;
	
	@Column(name= "total_recebido_studio_debito", nullable = false)
	private BigDecimal totalRecebidoStudioDebito = BigDecimal.ZERO;
	
	@Column(name= "total_recebido_studio_credito", nullable = false)
	private BigDecimal totalRecebidoStudioCredito = BigDecimal.ZERO;
	
	@Column(name= "total_recebido_tatuador_dinheiro", nullable = false)
	private BigDecimal totalRecebidoTatuadorDinheiro = BigDecimal.ZERO;
	
	@Column(name= "total_recebido_tatuador_debito", nullable = false)
	private BigDecimal totalRecebidoTatuadorDebito = BigDecimal.ZERO;
	
	@Column(name= "total_recebido_tatuador_credito", nullable = false)
	private BigDecimal totalRecebidoTatuadorCredito = BigDecimal.ZERO;

	@Column(name= "total_receber_studio_dinheiro", nullable = false)
	private BigDecimal totalReceberStudioDinheiro = BigDecimal.ZERO;
	
	@Column(name= "total_receber_studio_debito", nullable = false)
	private BigDecimal totalReceberStudioDebito = BigDecimal.ZERO;
	
	@Column(name= "total_receber_studio_credito", nullable = false)
	private BigDecimal totalReceberStudioCredito = BigDecimal.ZERO;
	
	@Column(name= "total_receber_tatuador_dinheiro", nullable = false)
	private BigDecimal totalReceberTatuadorDinheiro = BigDecimal.ZERO;
	
	@Column(name= "total_receber_tatuador_debito", nullable = false)
	private BigDecimal totalReceberTatuadorDebito = BigDecimal.ZERO;

	@Column(name= "total_receber_tatuador_credito", nullable = false)
	private BigDecimal totalReceberTatuadorCredito = BigDecimal.ZERO;
	
	@Column(name= "total_receber_tatuador", nullable = false)
	private BigDecimal totalReceberTatuador = BigDecimal.ZERO;
	
	@Column(name= "total_recebido_tatuador", nullable = false)
	private BigDecimal totalRecebidoTatuador = BigDecimal.ZERO;

	@Column(name= "total_receber_studio", nullable = false)
	private BigDecimal totalReceberStudio = BigDecimal.ZERO;
	
	@Column(name= "total_recebido_studio", nullable = false)
	private BigDecimal totalRecebidoStudio = BigDecimal.ZERO;
	
	@Column(name = "valor_saque", nullable = false)
	private BigDecimal valorSaque = BigDecimal.ZERO;
	
	@Column(name = "valor_total", nullable = false)
	private BigDecimal valorTotal = BigDecimal.ZERO;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	
	@OneToOne
	@JoinColumn(name = "ordem_servico_id")
	private OrdemServico ordemServico;

	public Long getGestaoFinanceiraId() {
		return gestaoFinanceiraId;
	}

	public void setGestaoFinanceiraId(Long gestaoFinanceiraId) {
		this.gestaoFinanceiraId = gestaoFinanceiraId;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isValida() {
		return valida;
	}
	
	public void setValida(boolean valida) {
		this.valida = valida;
	}

	public boolean isTatuadorDinheiroRecebido() {
		return tatuadorDinheiroRecebido;
	}

	public void setTatuadorDinheiroRecebido(boolean tatuadorDinheiroRecebido) {
		this.tatuadorDinheiroRecebido = tatuadorDinheiroRecebido;
	}

	public boolean isTatuadorDebitoRecebido() {
		return tatuadorDebitoRecebido;
	}

	public void setTatuadorDebitoRecebido(boolean tatuadorDebitoRecebido) {
		this.tatuadorDebitoRecebido = tatuadorDebitoRecebido;
	}

	public boolean isTatuadorCreditoRecebido() {
		return tatuadorCreditoRecebido;
	}

	public void setTatuadorCreditoRecebido(boolean tatuadorCreditoRecebido) {
		this.tatuadorCreditoRecebido = tatuadorCreditoRecebido;
	}

	public boolean isStudioDinheiroRecebido() {
		return studioDinheiroRecebido;
	}

	public void setStudioDinheiroRecebido(boolean studioDinheiroRecebido) {
		this.studioDinheiroRecebido = studioDinheiroRecebido;
	}

	public boolean isStudioDebitoRecebido() {
		return studioDebitoRecebido;
	}

	public void setStudioDebitoRecebido(boolean studioDebitoRecebido) {
		this.studioDebitoRecebido = studioDebitoRecebido;
	}

	public boolean isStudioCreditoRecebido() {
		return studioCreditoRecebido;
	}

	public void setStudioCreditoRecebido(boolean studioCreditoRecebido) {
		this.studioCreditoRecebido = studioCreditoRecebido;
	}

	public BigDecimal getPercentualAplicadoStudioDinheiro() {
		return percentualAplicadoStudioDinheiro;
	}

	public void setPercentualAplicadoStudioDinheiro(BigDecimal percentualAplicadoStudioDinheiro) {
		this.percentualAplicadoStudioDinheiro = percentualAplicadoStudioDinheiro;
	}

	public BigDecimal getPercentualAplicadoStudioDebito() {
		return percentualAplicadoStudioDebito;
	}

	public void setPercentualAplicadoStudioDebito(BigDecimal percentualAplicadoStudioDebito) {
		this.percentualAplicadoStudioDebito = percentualAplicadoStudioDebito;
	}

	public BigDecimal getPercentualAplicadoStudioCredito() {
		return percentualAplicadoStudioCredito;
	}

	public void setPercentualAplicadoStudioCredito(BigDecimal percentualAplicadoStudioCredito) {
		this.percentualAplicadoStudioCredito = percentualAplicadoStudioCredito;
	}

	public BigDecimal getTotalComissaoStudioDinheiro() {
		return totalComissaoStudioDinheiro;
	}

	public void setTotalComissaoStudioDinheiro(BigDecimal totalComissaoStudioDinheiro) {
		this.totalComissaoStudioDinheiro = totalComissaoStudioDinheiro;
	}

	public BigDecimal getTotalComissaoStudioDebito() {
		return totalComissaoStudioDebito;
	}

	public void setTotalComissaoStudioDebito(BigDecimal totalComissaoStudioDebito) {
		this.totalComissaoStudioDebito = totalComissaoStudioDebito;
	}

	public BigDecimal getTotalComissaoStudioCredito() {
		return totalComissaoStudioCredito;
	}

	public void setTotalComissaoStudioCredito(BigDecimal totalComissaoStudioCredito) {
		this.totalComissaoStudioCredito = totalComissaoStudioCredito;
	}

	public BigDecimal getTotalComissaoStudio() {
		return totalComissaoStudio;
	}

	public void setTotalComissaoStudio(BigDecimal totalComissaoStudio) {
		this.totalComissaoStudio = totalComissaoStudio;
	}

	public BigDecimal getPercentualAplicadoTatuadorDinheiro() {
		return percentualAplicadoTatuadorDinheiro;
	}

	public void setPercentualAplicadoTatuadorDinheiro(BigDecimal percentualAplicadoTatuadorDinheiro) {
		this.percentualAplicadoTatuadorDinheiro = percentualAplicadoTatuadorDinheiro;
	}

	public BigDecimal getPercentualAplicadoTatuadorDebito() {
		return percentualAplicadoTatuadorDebito;
	}

	public void setPercentualAplicadoTatuadorDebito(BigDecimal percentualAplicadoTatuadorDebito) {
		this.percentualAplicadoTatuadorDebito = percentualAplicadoTatuadorDebito;
	}

	public BigDecimal getPercentualAplicadoTatuadorCredito() {
		return percentualAplicadoTatuadorCredito;
	}

	public void setPercentualAplicadoTatuadorCredito(BigDecimal percentualAplicadoTatuadorCredito) {
		this.percentualAplicadoTatuadorCredito = percentualAplicadoTatuadorCredito;
	}

	public BigDecimal getTotalComissaoTatuadorDinheiro() {
		return totalComissaoTatuadorDinheiro;
	}

	public void setTotalComissaoTatuadorDinheiro(BigDecimal totalComissaoTatuadorDinheiro) {
		this.totalComissaoTatuadorDinheiro = totalComissaoTatuadorDinheiro;
	}

	public BigDecimal getTotalComissaoTatuadorDebito() {
		return totalComissaoTatuadorDebito;
	}

	public void setTotalComissaoTatuadorDebito(BigDecimal totalComissaoTatuadorDebito) {
		this.totalComissaoTatuadorDebito = totalComissaoTatuadorDebito;
	}

	public BigDecimal getTotalComissaoTatuadorCredito() {
		return totalComissaoTatuadorCredito;
	}

	public void setTotalComissaoTatuadorCredito(BigDecimal totalComissaoTatuadorCredito) {
		this.totalComissaoTatuadorCredito = totalComissaoTatuadorCredito;
	}

	public BigDecimal getTotalComissaoTatuador() {
		return totalComissaoTatuador;
	}

	public void setTotalComissaoTatuador(BigDecimal totalComissaoTatuador) {
		this.totalComissaoTatuador = totalComissaoTatuador;
	}
	
	public BigDecimal getPercentualAplicadoTatuadorProduto() {
		return percentualAplicadoTatuadorProduto;
	}

	public void setPercentualAplicadoTatuadorProduto(BigDecimal percentualAplicadoTatuadorProduto) {
		this.percentualAplicadoTatuadorProduto = percentualAplicadoTatuadorProduto;
	}

	public BigDecimal getPercentualAplicadoStudioProduto() {
		return percentualAplicadoStudioProduto;
	}

	public void setPercentualAplicadoStudioProduto(BigDecimal percentualAplicadoStudioProduto) {
		this.percentualAplicadoStudioProduto = percentualAplicadoStudioProduto;
	}

	public BigDecimal getTotalComissaoTatuadorProduto() {
		return totalComissaoTatuadorProduto;
	}

	public void setTotalComissaoTatuadorProduto(BigDecimal totalComissaoTatuadorProduto) {
		this.totalComissaoTatuadorProduto = totalComissaoTatuadorProduto;
	}

	public BigDecimal getTotalComissaoStudioProduto() {
		return totalComissaoStudioProduto;
	}

	public void setTotalComissaoStudioProduto(BigDecimal totalComissaoStudioProduto) {
		this.totalComissaoStudioProduto = totalComissaoStudioProduto;
	}

	public BigDecimal getTotalRecebidoStudioDinheiro() {
		return totalRecebidoStudioDinheiro;
	}

	public void setTotalRecebidoStudioDinheiro(BigDecimal totalRecebidoStudioDinheiro) {
		this.totalRecebidoStudioDinheiro = totalRecebidoStudioDinheiro;
	}

	public BigDecimal getTotalRecebidoStudioDebito() {
		return totalRecebidoStudioDebito;
	}

	public void setTotalRecebidoStudioDebito(BigDecimal totalRecebidoStudioDebito) {
		this.totalRecebidoStudioDebito = totalRecebidoStudioDebito;
	}

	public BigDecimal getTotalRecebidoStudioCredito() {
		return totalRecebidoStudioCredito;
	}

	public void setTotalRecebidoStudioCredito(BigDecimal totalRecebidoStudioCredito) {
		this.totalRecebidoStudioCredito = totalRecebidoStudioCredito;
	}

	public BigDecimal getTotalRecebidoTatuadorDinheiro() {
		return totalRecebidoTatuadorDinheiro;
	}

	public void setTotalRecebidoTatuadorDinheiro(BigDecimal totalRecebidoTatuadorDinheiro) {
		this.totalRecebidoTatuadorDinheiro = totalRecebidoTatuadorDinheiro;
	}

	public BigDecimal getTotalRecebidoTatuadorDebito() {
		return totalRecebidoTatuadorDebito;
	}

	public void setTotalRecebidoTatuadorDebito(BigDecimal totalRecebidoTatuadorDebito) {
		this.totalRecebidoTatuadorDebito = totalRecebidoTatuadorDebito;
	}

	public BigDecimal getTotalRecebidoTatuadorCredito() {
		return totalRecebidoTatuadorCredito;
	}

	public void setTotalRecebidoTatuadorCredito(BigDecimal totalRecebidoTatuadorCredito) {
		this.totalRecebidoTatuadorCredito = totalRecebidoTatuadorCredito;
	}

	public BigDecimal getTotalReceberStudioDinheiro() {
		return totalReceberStudioDinheiro;
	}

	public void setTotalReceberStudioDinheiro(BigDecimal totalReceberStudioDinheiro) {
		this.totalReceberStudioDinheiro = totalReceberStudioDinheiro;
	}

	public BigDecimal getTotalReceberStudioDebito() {
		return totalReceberStudioDebito;
	}

	public void setTotalReceberStudioDebito(BigDecimal totalReceberStudioDebito) {
		this.totalReceberStudioDebito = totalReceberStudioDebito;
	}

	public BigDecimal getTotalReceberStudioCredito() {
		return totalReceberStudioCredito;
	}

	public void setTotalReceberStudioCredito(BigDecimal totalReceberStudioCredito) {
		this.totalReceberStudioCredito = totalReceberStudioCredito;
	}

	public BigDecimal getTotalReceberTatuadorDinheiro() {
		return totalReceberTatuadorDinheiro;
	}

	public void setTotalReceberTatuadorDinheiro(BigDecimal totalReceberTatuadorDinheiro) {
		this.totalReceberTatuadorDinheiro = totalReceberTatuadorDinheiro;
	}

	public BigDecimal getTotalReceberTatuadorDebito() {
		return totalReceberTatuadorDebito;
	}

	public void setTotalReceberTatuadorDebito(BigDecimal totalReceberTatuadorDebito) {
		this.totalReceberTatuadorDebito = totalReceberTatuadorDebito;
	}

	public BigDecimal getTotalReceberTatuadorCredito() {
		return totalReceberTatuadorCredito;
	}

	public void setTotalReceberTatuadorCredito(BigDecimal totalReceberTatuadorCredito) {
		this.totalReceberTatuadorCredito = totalReceberTatuadorCredito;
	}

	public BigDecimal getTotalReceberTatuador() {
		return totalReceberTatuador;
	}

	public void setTotalReceberTatuador(BigDecimal totalReceberTatuador) {
		this.totalReceberTatuador = totalReceberTatuador;
	}

	public BigDecimal getTotalRecebidoTatuador() {
		return totalRecebidoTatuador;
	}

	public void setTotalRecebidoTatuador(BigDecimal totalRecebidoTatuador) {
		this.totalRecebidoTatuador = totalRecebidoTatuador;
	}

	public BigDecimal getTotalReceberStudio() {
		return totalReceberStudio;
	}

	public void setTotalReceberStudio(BigDecimal totalReceberStudio) {
		this.totalReceberStudio = totalReceberStudio;
	}

	public BigDecimal getTotalRecebidoStudio() {
		return totalRecebidoStudio;
	}

	public void setTotalRecebidoStudio(BigDecimal totalRecebidoStudio) {
		this.totalRecebidoStudio = totalRecebidoStudio;
	}

	public BigDecimal getValorSaque() {
		return valorSaque;
	}

	public void setValorSaque(BigDecimal valorSaque) {
		this.valorSaque = valorSaque;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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