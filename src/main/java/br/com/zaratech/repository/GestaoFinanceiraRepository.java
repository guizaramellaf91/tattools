package br.com.zaratech.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.zaratech.model.GestaoFinanceira;

public interface GestaoFinanceiraRepository extends JpaRepository<GestaoFinanceira, Long>, PagingAndSortingRepository<GestaoFinanceira, Long> {
	
	GestaoFinanceira findByGestaoFinanceiraId(long gestaoFinanceiraId);
		
	List<GestaoFinanceira> findByDescricaoContaining(String descricao);
	
	@Query(value = "select g from GestaoFinanceira g where g.valida=true")
	Page<GestaoFinanceira> buscarTodasValidas(PageRequest page);
	
	@Query(value = "select g from GestaoFinanceira g where g.gestaoFinanceiraId=?1")
	public GestaoFinanceira buscaPorGestaoFinanceiraId(long gestaoFinanceiraId);
	
	@Query(value = "select * from gestao_financeira where ordem_servico_id=?1", nativeQuery = true)
	public GestaoFinanceira buscaPorOrdemServicoId(long ordemServicoId);
	
	@Query(value = "select count(*) from gestao_financeira where valida=true", nativeQuery = true)
	public long totalGestaoFinanceiraValidos();
	
	@Query(value = "select count(*) from gestao_financeira where valida=false", nativeQuery = true)
	public long totalGestaoFinanceiraInvalidos();
	
	@Query(value = "select count(*) from gestao_financeira where usuario_id = ?1 and valida=true", nativeQuery = true)
	public long totalGestaoFinanceiraUsuario(long usuarioId);
	
	@Query(value = "select count(*) from gestao_financeira where cliente_id = ?1 and valida=true", nativeQuery = true)
	public long totalGestaoFinanceiraCliente(long clienteId);
	
	@Query(value = "select SUM(valor_total) from ordem_servico where valida=true", nativeQuery = true)
	public long valorTotalOS();
	
	@Query(value = "select SUM(g.valorTotal) from GestaoFinanceira g where g.valida=true")
	public BigDecimal valorTotalGestaoFinanceiraValidas();
	
	@Query(value = "select SUM(g.totalComissaoStudio) from GestaoFinanceira g where g.valida=true")
	public BigDecimal valorTotalStudioValidos();
	
	@Query(value = "select SUM(g.totalComissaoStudio) from GestaoFinanceira g where g.valida=false")
	public BigDecimal valorTotalStudioInvalidos();

	@Query(value = "select SUM(g.totalComissaoTatuador) from GestaoFinanceira g where g.valida=true")
	public BigDecimal valorTotalTatuadorValidos();
	
	@Query(value = "select SUM(g.totalComissaoTatuador) from GestaoFinanceira g where g.valida=false")
	public BigDecimal valorTotalTatuadorInvalidos();
	
	@Query(value = "select SUM(g.totalReceberStudio) from GestaoFinanceira g where g.valida=true")
	public BigDecimal valorTotalStudioReceberValidos();
	
	@Query(value = "select SUM(g.totalReceberTatuador) from GestaoFinanceira g where g.valida=true")
	public BigDecimal valorTotalTatuadorReceberValidos();
	
	@Query(value = "select SUM(g.valorTotal) from GestaoFinanceira g where g.valida=true and g.totalReceberStudio > 0 and g.totalReceberTatuador > 0")
	public BigDecimal valorTotalReceberValidos();
		
	@Query(value = "select SUM(g.totalRecebidoStudio) from GestaoFinanceira g where g.valida=true")
	public BigDecimal valorTotalStudioRecebidoValidos();
	
	@Query(value = "select SUM(g.totalRecebidoTatuador) from GestaoFinanceira g where g.valida=true")
	public BigDecimal valorTotalTatuadorRecebidoValidos();
	
	@Query(value = "select SUM(g.valorTotal) from GestaoFinanceira g where g.valida=true and g.totalRecebidoStudio > 0 and g.totalRecebidoTatuador > 0")
	public BigDecimal valorTotalRecebidoValidos();
	
	@Query(value = "select SUM(total_recebido_tatuador_dinheiro) from gestao_financeira where usuario_id = ?1 and valida=true", nativeQuery = true)
	public BigDecimal valorRecebidoTatuadorDinheiro(long usuarioId);
	
	@Query(value = "select SUM(total_recebido_tatuador_debito) from gestao_financeira where usuario_id = ?1 and valida=true", nativeQuery = true)
	public BigDecimal valorRecebidoTatuadorDebito(long usuarioId);
	
	@Query(value = "select SUM(total_recebido_tatuador_credito) from gestao_financeira where usuario_id = ?1 and valida=true", nativeQuery = true)
	public BigDecimal valorRecebidoTatuadorCredito(long usuarioId);
	
	@Query(value = "select SUM(total_receber_tatuador_dinheiro) from gestao_financeira where usuario_id = ?1 and valida=true", nativeQuery = true)
	public BigDecimal valorReceberTatuadorDinheiro(long usuarioId);
	
	@Query(value = "select SUM(total_receber_tatuador_debito) from gestao_financeira where usuario_id = ?1 and valida=true", nativeQuery = true)
	public BigDecimal valorReceberTatuadorDebito(long usuarioId);
	
	@Query(value = "select SUM(total_receber_tatuador_credito) from gestao_financeira where usuario_id = ?1 and valida=true", nativeQuery = true)
	public BigDecimal valorReceberTatuadorCredito(long usuarioId);
}