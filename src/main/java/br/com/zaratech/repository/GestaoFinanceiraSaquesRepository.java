package br.com.zaratech.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.zaratech.model.GestaoFinanceiraSaques;

public interface GestaoFinanceiraSaquesRepository extends JpaRepository<GestaoFinanceiraSaques, Long>, PagingAndSortingRepository<GestaoFinanceiraSaques, Long> {
	
	GestaoFinanceiraSaques findByGestaoFinanceiraSaqueId(long gestaoFinanceiraSaqueId);
				
	@Query(value = "select count(*) from gestao_financeira_saques where pendente=true and usuario_id = ?1", nativeQuery = true)
	public long totalGestaoFinanceiraSaquesPendentes(long usuarioId);
	
	@Query(value = "select valor_saque from gestao_financeira_saques where gestao_financeira_id = ?1", nativeQuery = true)
	public BigDecimal valorSaqueGestaoFinanceira(long gestaoFinanceiraId);
	
	@Query(value = "select sum(valor_saque) from gestao_financeira_saques where gestao_financeira_id = ?1 and pendente=true", nativeQuery = true)
	public BigDecimal valorSaqueGestaoFinanceiraExcedido(long gestaoFinanceiraId);
	
	@Query(value = "select sum(valor_saque) from gestao_financeira_saques where usuario_id = ?1 and pendente=true", nativeQuery = true)
	public BigDecimal valorSaquesPendentes(long usuarioId);
	
	@Query(value = "select count(*) from gestao_financeira_parcelas where usuario_id = ?1", nativeQuery = true)
	public long totalGestaoFinanceiraParcelasUsuario(long usuarioId);
	
	@Query(value = "select count(*) from gestao_financeira_saques where usuario_id = ?1", nativeQuery = true)
	public long totalGestaoFinanceiraSaquesUsuario(long usuarioId);	
}
