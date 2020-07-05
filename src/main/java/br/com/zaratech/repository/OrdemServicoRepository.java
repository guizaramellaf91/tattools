package br.com.zaratech.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.zaratech.model.OrdemServico;
import br.com.zaratech.model.Usuario;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, String>, PagingAndSortingRepository<OrdemServico, String> {
	
	OrdemServico findByOrdemServicoId(long ordemServicoId);
		
	List<OrdemServico> findByDescricaoContaining(String descricao);
	
	@Query(value = "select * from ordem_servico where ordem_servico_id = ?1", nativeQuery = true)
	List<OrdemServico> buscarPorOrdemServicoId(long ordemServicoId);
	
	@Query(value = "select o from OrdemServico o where o.valida = true")
	Page<OrdemServico> buscarTodasValidas(PageRequest pageRequest);
	
	@Query(value = "select o from OrdemServico o where o.valida = true and o.usuario = ?1")
	Page<OrdemServico> buscarTodasValidasPorUsuario(Usuario usuario, PageRequest pageRequest);

	@Query(value = "select count(*) from OrdemServico o where o.usuario = ?1 and o.valida = true")
	public long totalOSUsuario(Usuario usuario);
	
	@Query(value = "select SUM(o.valorTotal) from OrdemServico o where o.valida = true and o.usuario = ?1")
	public long valorTotalOSUsuario(Usuario usuario);
	
	@Query(value = "select count(*) from ordem_servico where valida=true", nativeQuery = true)
	public long totalOSvalidas();

	@Query(value = "select count(*) from ordem_servico where valida=false", nativeQuery = true)
	public long totalOSinvalidas();
	
	@Query(value = "select count(*) from ordem_servico where cliente_id = ?1 and valida=true", nativeQuery = true)
	public long totalOSCliente(long clienteId);
	
	@Query(value = "select SUM(valor_total) from ordem_servico where valida=true", nativeQuery = true)
	public long valorTotalOS();
}
