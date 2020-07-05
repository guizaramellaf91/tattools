
package br.com.zaratech.security;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.zaratech.bean.Parametros;
import br.com.zaratech.bean.UsuarioBean;
import br.com.zaratech.model.BlackBox;
import br.com.zaratech.model.ParametrosSistema;
import br.com.zaratech.model.Usuario;
import br.com.zaratech.repository.BlackBoxRepository;
import br.com.zaratech.repository.ParametrosRepository;
import br.com.zaratech.repository.UsuarioRepository;

@Repository
@Transactional
public class UserService implements UserDetailsService {

	static Logger log = Logger.getLogger(UserService.class);

	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	BlackBoxRepository blackBoxRepository;
	@Autowired
	ParametrosRepository parametrosRepository;
	@Autowired
	UsuarioBean usuarioBean;
          
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException {

		Usuario usuario = usuarioRepository.findByLogin(login);
							
		if (usuario != null) {
		
			Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			if (authentication instanceof Authentication) {
				
				usuarioBean.setUsuario(usuario);				
				if(!usuarioBean.getUsuario().getLogin().equals("admin")) {
					usuarioBean.getUsuario().setAcessos(usuarioBean.getUsuario().getAcessos() + 1);
				}
		    	usuarioBean.getUsuario().setUltimoAcesso(new Date());
		    	usuarioRepository.save(usuarioBean.getUsuario());
		    	
		    	ParametrosSistema blackBoxReport = parametrosRepository.findByChave(Parametros.BLACK_BOX_REPORT);
		    	
		    	if(blackBoxReport != null && blackBoxReport.getValor().equals(Parametros.ATIVO)) {
			    	BlackBox blackBox = new BlackBox();
			    	blackBox.setDataCadastro(new Date());
			    	blackBox.setUsuario(usuarioBean.getUsuario());
			    	blackBox.setModulo("UserDetails");
			    	blackBox.setAcao("Autenticacao de usuario");
			    	blackBoxRepository.save(blackBox);
		    	}
			}
			
			if (!usuario.isAtivo()) {
				throw new UsernameNotFoundException("Usuario inativo! Entre em contato com o administrador");
			}

		} else {
			throw new UsernameNotFoundException("Nenhum usuario encontrado!");
		}
		return new User(usuario.getUsername(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());
	}
}