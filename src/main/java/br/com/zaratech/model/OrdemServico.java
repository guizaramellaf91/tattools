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
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "ordem_servico")
@Entity
public class OrdemServico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ordemServicoId;
	
	@Column(name= "descricao", nullable = false)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "servico_id")
	private Servico servico;
	
	@Column(name = "observacao", nullable = false)
	private String observacao;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "data_cadastro", nullable = false)
	private Date dataCadastro;
	
	@Column(name = "data_alteracao")
	private Date dataAlteracao;
	
	@Column(name = "valor_servico")
	private BigDecimal valorServico;
	
	@Column(name = "valor_produto")
	private BigDecimal valorProduto;
	
	@Column(name = "valor_total", nullable = false)
	private BigDecimal valorTotal;

	@Column(name = "totalDinheiro")
	private BigDecimal totalDinheiro;
	
	@Column(name = "totalDebito")
	private BigDecimal totalDebito;
	
	@Column(name = "totalCredito")
	private BigDecimal totalCredito;

	@Column
	private long totalParcelasCredito;
	
	@Column(name = "valida")
	private boolean valida;
	
	public Long getOrdemServicoId() {
		return ordemServicoId;
	}

	public void setOrdemServicoId(Long ordemServicoId) {
		this.ordemServicoId = ordemServicoId;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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

	public BigDecimal getValorServico() {
		return valorServico;
	}

	public void setValorServico(BigDecimal valorServico) {
		this.valorServico = valorServico;
	}

	public BigDecimal getValorProduto() {
		return valorProduto;
	}

	public void setValorProduto(BigDecimal valorProduto) {
		this.valorProduto = valorProduto;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getTotalDinheiro() {
		return totalDinheiro;
	}

	public void setTotalDinheiro(BigDecimal totalDinheiro) {
		this.totalDinheiro = totalDinheiro;
	}

	public BigDecimal getTotalDebito() {
		return totalDebito;
	}

	public void setTotalDebito(BigDecimal totalDebito) {
		this.totalDebito = totalDebito;
	}

	public BigDecimal getTotalCredito() {
		return totalCredito;
	}

	public void setTotalCredito(BigDecimal totalCredito) {
		this.totalCredito = totalCredito;
	}

	public long getTotalParcelasCredito() {
		return totalParcelasCredito;
	}

	public void setTotalParcelasCredito(long totalParcelasCredito) {
		this.totalParcelasCredito = totalParcelasCredito;
	}

	public boolean isValida() {
		return valida;
	}

	public void setValida(boolean valida) {
		this.valida = valida;
	}
}