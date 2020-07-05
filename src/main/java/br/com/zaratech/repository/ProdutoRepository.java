package br.com.zaratech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.zaratech.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	Produto findByProdutoId(long produtoId);
	
	List<Produto> findAll();
	
	@Query(value = "select p from Produto p where p.ativo = true")
	List<Produto> findProdutosAtivos();
	
	@Query(value = "select count(*) from produto", nativeQuery = true)
	public long totalProdutos();

}
