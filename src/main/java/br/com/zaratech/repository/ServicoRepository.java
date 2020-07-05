package br.com.zaratech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.zaratech.model.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
	
	Servico findByServicoId(long servicoId);
	
	List<Servico> findAll();
	
	@Query(value = "select s from Servico s where s.ativo = true")
	List<Servico> findServicosAtivos();
	
	@Query(value = "select count(*) from servico", nativeQuery = true)
	public long totalServicos();

}
