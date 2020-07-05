package br.com.zaratech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.zaratech.model.Endereco;
import br.com.zaratech.model.Servico;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	
	Servico findByEnderecoId(long enderecoId);
	
	List<Endereco> findAll();
	
	@Query(value = "select count(*) from Endereco")
	public long totalEnderecos();
}
