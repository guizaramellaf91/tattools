package br.com.zaratech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zaratech.model.TipoUsuario;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Long> {
	
	TipoUsuario findByTipoUsuarioId(long tipoUsuarioId);
	
	List<TipoUsuario> findAll();
}
