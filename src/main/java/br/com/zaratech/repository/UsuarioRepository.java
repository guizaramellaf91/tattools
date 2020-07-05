package br.com.zaratech.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.zaratech.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
	
	Usuario findByUsuarioId(long usuarioId);
	Usuario findByEmail(String email);
	
	@Query("select u from Usuario u where u.nome like %?1% and u.login <> 'admin'")
	List<Usuario> findByNomeContaining(String nomeUsuario);
	
	@Query("select u from Usuario u where u.login <> 'admin' order by u.acessos desc")
	Page<Usuario> findAllUsuarios(PageRequest pageRequest);
	
	@Query(value = "select count(*) from usuario", nativeQuery = true)
	public long totalUsuarios();
	
	@Query(value = "select count(*) from Usuario u where u.tipoUsuario.nomeTipo = 'STUDIO'")
	public long totalAdministradores();
	
	@Query(value = "select count(*) from Usuario u where u.tipoUsuario.nomeTipo = 'TATUADOR'")
	public long totalTatuadores();
	
	@Query(value = "select count(*) from usuario where ativo = true;", nativeQuery = true)
	public long totalUsuariosAtivos();
	
	@Query(value = "select count(*) from usuario where ativo = false", nativeQuery = true)
	public long totalUsuariosInativos();
	
	@Query("select u from Usuario u where u.login = ?1")
	Usuario findByLogin(String login);
			
	@Query("select u from Usuario u where u.login = ?1 and u.senha = ?2")
	Usuario findByLoginSenha(String login, String senha);	
}