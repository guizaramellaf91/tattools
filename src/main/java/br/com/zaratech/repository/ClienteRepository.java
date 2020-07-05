package br.com.zaratech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.zaratech.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String>, PagingAndSortingRepository<Cliente, String> {
	
	Cliente findByClienteId(long clienteId);
	Cliente findByNome(String nome);
	Cliente findByCpf(String cpf);
	Cliente findByEmail(String email);
	
	List<Cliente> findByNomeContaining(String nome);
		
	@Query
	(value = "SELECT * FROM cliente c ORDER BY nome asc",
	 countQuery = "SELECT count(*) FROM cliente", 
	 nativeQuery = true)
	List<Cliente> findAllClientes();
	
	@Query(value = "select count(*) from Cliente")
	public long totalClientes();
}
