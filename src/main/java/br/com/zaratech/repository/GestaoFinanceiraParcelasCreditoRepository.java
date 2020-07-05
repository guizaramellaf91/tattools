package br.com.zaratech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.zaratech.model.GestaoFinanceira;
import br.com.zaratech.model.GestaoFinanceiraParcelasCredito;

public interface GestaoFinanceiraParcelasCreditoRepository extends JpaRepository<GestaoFinanceiraParcelasCredito, Long>, PagingAndSortingRepository<GestaoFinanceiraParcelasCredito, Long> {
	
	GestaoFinanceiraParcelasCredito findByGestaoFinanceiraParcelaCreditoId(long gestaoFinanceiraParcelaCreditoId);
	
	@Query(value = "select g from GestaoFinanceiraParcelasCredito g where g.gestaoFinanceira = ?1")
	public List<GestaoFinanceiraParcelasCredito> buscaPorGestaoFinanceiraId(GestaoFinanceira gestaoFinanceira);
}
