package br.com.zaratech.controller;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.zaratech.model.Usuario;
import br.com.zaratech.service.UsuarioService;

@Controller
public class UsuarioController {

	static Logger log = Logger.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(value = "/usuarios", method = RequestMethod.GET)
	public ModelAndView listaUsuarios(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
				
		return usuarioService.listaUsuarios(pageSize, page);
	}

    @RequestMapping(value = "/buscarUsuario")
	public ModelAndView buscarUsuario(String nomeUsuario, @RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page){   
    		
    	return usuarioService.buscarUsuario(nomeUsuario, pageSize, page);
	}
			
	@RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.GET)
	public ModelAndView cadastrar() {
				
		return usuarioService.cadastrar();
	}

	@RequestMapping(value = "cadastrarUsuario", method = RequestMethod.POST)
	public ModelAndView cadastrar(Usuario usuario, Long tipoUsuarioId) {
		
		return usuarioService.cadastrarUsuario(usuario, tipoUsuarioId);
	}

	@RequestMapping(value = "detalhesUsuario", method = RequestMethod.GET)
	public ModelAndView detalhesUsuario(long usuarioId) {
		
		return usuarioService.detalhesUsuario(usuarioId);
	}
	
	@RequestMapping(value = "detalhesUsuario", method = RequestMethod.POST)
	public String alterarUsuario(Usuario usuario, long usuarioId, long tipoUsuarioId) {
				
		return usuarioService.alterarUsuario(usuario, usuarioId, tipoUsuarioId);
	}
	
	@RequestMapping(value = "/recuperarAcesso", method = RequestMethod.GET)
	public ModelAndView recuperarAcesso(String id) {

		return usuarioService.recuperarAcesso(id);
	}
	
	@RequestMapping(value = "/recuperarAcesso", method = RequestMethod.POST)
	public ModelAndView recuperarAcessoAgora(String id, String senha) {

		return usuarioService.recuperarAcessoAgora(id, senha);
	}
	
	@RequestMapping(value = "/esqueciMinhaSenha", method = RequestMethod.GET)
	public ModelAndView esqueciMinhaSenha() {

		return usuarioService.esqueciMinhaSenha();
	}
	
	@RequestMapping(value = "/esqueciMinhaSenha", method = RequestMethod.POST)
	public ModelAndView esqueciMinhaSenhaEnviar(String email) throws UnsupportedEncodingException, MessagingException {
		
		return usuarioService.esqueciMinhaSenhaEnviar(email);
	}
}