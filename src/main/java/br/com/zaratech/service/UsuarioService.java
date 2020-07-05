package br.com.zaratech.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.zaratech.bean.Parametros;
import br.com.zaratech.bean.UsuarioBean;
import br.com.zaratech.model.PagerModel;
import br.com.zaratech.model.ParametrosSistema;
import br.com.zaratech.model.TipoUsuario;
import br.com.zaratech.model.Usuario;
import br.com.zaratech.repository.GestaoFinanceiraRepository;
import br.com.zaratech.repository.GestaoFinanceiraSaquesRepository;
import br.com.zaratech.repository.OrdemServicoRepository;
import br.com.zaratech.repository.ParametrosRepository;
import br.com.zaratech.repository.TipoUsuarioRepository;
import br.com.zaratech.repository.UsuarioRepository;
import br.com.zaratech.security.Scrypt;
import br.com.zaratech.security.UserPrivilegies;
import br.com.zaratech.util.SendMail;

@Service
public class UsuarioService {

	static Logger log = Logger.getLogger(UsuarioService.class);
	
	private static final int BUTTONS_TO_SHOW = 3;
	private static final int INITIAL_PAGE = 0;
	private static final int[] PAGE_SIZES = {5, 10};
	
	@Autowired
    private UsuarioBean usuarioBean;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	@Autowired
	private GestaoFinanceiraRepository gestaoFinanceiraRepository;
	@Autowired
	private GestaoFinanceiraSaquesRepository gestaoFinanceiraSaquesRepository;
	@Autowired
	private TipoUsuarioRepository tipoUsuarioRepository;
	@Autowired
	private ParametrosRepository parametrosRepository;
	@Autowired
	private SendMail sendMail;
	
	public String idRecuperacao;
	
	public ModelAndView listaUsuarios(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		
		ModelAndView mv = new ModelAndView("usuario/usuarios");	
		
		if(UserPrivilegies.eStudio(usuarioBean.getUsuario())) {
			
			int evalPageSize;
			int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
			
			final ParametrosSistema pageSizeList = parametrosRepository.findByChave(Parametros.PAGE_SIZE_LIST);
			if(pageSizeList != null && pageSizeList.getChave() != null && pageSizeList.getValor() != null) {
				evalPageSize = pageSize.orElse(Integer.valueOf(pageSizeList.getValor()));
			}else {
				evalPageSize = 5;
			}
						
			usuarioBean.setPageSize(evalPageSize);
			usuarioBean.setPage((page.orElse(0) < 1) ? INITIAL_PAGE : page.get());
			
			Page<Usuario> usuarios = usuarioRepository.findAllUsuarios(PageRequest.of(evalPage, evalPageSize, Sort.by("acessos").descending()));
			PagerModel pager = new PagerModel(usuarios.getTotalPages(),usuarios.getNumber(),BUTTONS_TO_SHOW);
			mv.addObject("selectedPageSize", evalPageSize);
			mv.addObject("pageSizes", PAGE_SIZES);
			mv.addObject("pager", pager);
			mv.addObject("msg", "");
			mv.addObject("usuarioLogado", usuarioBean.getUsuario());
			mv.addObject("usuarios", usuarios);
			mv.addObject("quantidadeUsuarios", usuarioRepository.totalUsuarios());
			mv.addObject("quantidadeUsuariosAtivos", usuarioRepository.totalUsuariosAtivos());
			mv.addObject("quantidadeUsuariosInativos", usuarioRepository.totalUsuariosInativos());
			return mv;
			
		} else {
			mv = new ModelAndView("redirect:/index");
			return mv;	
		}
	}

	public ModelAndView buscarUsuario(String nomeUsuario, @RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page){ 
		
		ModelAndView mv = new ModelAndView("usuario/usuarios");
    	
    	if(UserPrivilegies.eStudio(usuarioBean.getUsuario())) {
    		
			int evalPageSize;
			int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
			
			final ParametrosSistema pageSizeList = parametrosRepository.findByChave(Parametros.PAGE_SIZE_LIST);
			if(pageSizeList != null && pageSizeList.getChave() != null && pageSizeList.getValor() != null) {
				evalPageSize = pageSize.orElse(Integer.valueOf(pageSizeList.getValor()));
			}else {
				evalPageSize = 5;
			}
						
			usuarioBean.setPageSize(evalPageSize);
			usuarioBean.setPage((page.orElse(0) < 1) ? INITIAL_PAGE : page.get());
    		
    		if(nomeUsuario != "") {
        		List<Usuario> usuario = usuarioRepository.findByNomeContaining(nomeUsuario);		
        		if(usuario.size() <= 0) {
        			Page<Usuario> usuarios = usuarioRepository.findAllUsuarios(PageRequest.of(evalPage, evalPageSize, Sort.by("acessos").descending()));
        			PagerModel pager = new PagerModel(usuarios.getTotalPages(),usuarios.getNumber(),BUTTONS_TO_SHOW);
        			mv.addObject("selectedPageSize", evalPageSize);
        			mv.addObject("pageSizes", PAGE_SIZES);
        			mv.addObject("pager", pager);
        			mv.addObject("usuarios", usuarios);
        		} else {
        			mv.addObject("searchUsuario", true);
        			mv.addObject("usuarios", usuario);
        		}
        		mv.addObject("usuarioLogado", usuarioBean.getUsuario());
    			mv.addObject("quantidadeUsuarios", usuarioRepository.totalUsuarios());
    			mv.addObject("quantidadeUsuariosAtivos", usuarioRepository.totalUsuariosAtivos());
    			mv.addObject("quantidadeUsuariosInativos", usuarioRepository.totalUsuariosInativos());
        		return mv;
        	}else {
        		mv = new ModelAndView("redirect:/usuarios");
        		return mv;
        	}     		
    	} else {
    		mv = new ModelAndView("redirect:/index");
			return mv;	
    	}
	}
	
	public ModelAndView detalhesUsuario(long usuarioId) {

		ModelAndView mv = new ModelAndView("usuario/detalhesUsuario");
		Usuario usuario = usuarioRepository.findByUsuarioId(usuarioId);
		
		if(UserPrivilegies.eAdmin(usuarioBean.getUsuario()) || usuarioBean.getUsuario().getLogin().equals(usuario.getLogin())) {
			try {
				usuario.setSenha("");
				mv.addObject("usuario", usuario);		
				mv.addObject("usuarioLogado", usuarioBean.getUsuario());
				mv.addObject("lsTipoUsuario", tipoUsuarioRepository.findAll());
				mv.addObject("quantidadeSaquePendente", gestaoFinanceiraSaquesRepository.totalGestaoFinanceiraSaquesPendentes(usuarioId));
				mv.addObject("valorSaquePendente", gestaoFinanceiraSaquesRepository.valorSaquesPendentes(usuarioId));

				BigDecimal totalRecebidoTatuadorDinheiro = gestaoFinanceiraRepository.valorRecebidoTatuadorDinheiro(usuarioId);
				BigDecimal totalRecebidoTatuadorDebito = gestaoFinanceiraRepository.valorRecebidoTatuadorDebito(usuarioId);
				BigDecimal totalRecebidoTatuadorCredito = gestaoFinanceiraRepository.valorRecebidoTatuadorCredito(usuarioId);
				
				BigDecimal totalReceberTatuadorDinheiro = gestaoFinanceiraRepository.valorReceberTatuadorDinheiro(usuarioId);
				BigDecimal totalReceberTatuadorDebito = gestaoFinanceiraRepository.valorReceberTatuadorDebito(usuarioId);
				BigDecimal totalReceberTatuadorCredito = gestaoFinanceiraRepository.valorReceberTatuadorCredito(usuarioId);
				
				if(totalRecebidoTatuadorDinheiro == null) {
					totalRecebidoTatuadorDinheiro = new BigDecimal(0.00);
				}
				if(totalRecebidoTatuadorDebito == null) {
					totalRecebidoTatuadorDebito = new BigDecimal(0.00);
				}
				if(totalRecebidoTatuadorCredito == null) {
					totalRecebidoTatuadorCredito = new BigDecimal(0.00);
				}
				if(totalReceberTatuadorDinheiro == null) {
					totalReceberTatuadorDinheiro = new BigDecimal(0.00);
				}
				if(totalReceberTatuadorDebito == null) {
					totalReceberTatuadorDebito = new BigDecimal(0.00);
				}
				if(totalReceberTatuadorCredito == null) {
					totalReceberTatuadorCredito = new BigDecimal(0.00);
				}
				
				mv.addObject("totalRecebidoTatuadorDinheiro", totalRecebidoTatuadorDinheiro);
				mv.addObject("totalRecebidoTatuadorDebito", totalRecebidoTatuadorDebito);
				mv.addObject("totalRecebidoTatuadorCredito", totalRecebidoTatuadorCredito);
				mv.addObject("totalReceberTatuadorDinheiro", totalReceberTatuadorDinheiro);
				mv.addObject("totalReceberTatuadorDebito", totalReceberTatuadorDebito);
				mv.addObject("totalReceberTatuadorCredito", totalReceberTatuadorCredito);
				mv.addObject("quantidadeOS", ordemServicoRepository.totalOSUsuario(usuarioBean.getUsuario()));
				mv.addObject("msg", "");
				return mv;
			} catch(Exception e) {
				log.error("Ocorreu um erro ao acessar os detalhes de usuario. Motivo: " + e.getMessage());
				e.printStackTrace();
				return mv;
			}
		} else {
			return new ModelAndView("redirect:/usuarios");
		}
	}

	public String alterarUsuario(Usuario usuario, long usuarioId, long tipoUsuarioId) {
		
		try {
			
			log.info("\nUsuario: " + usuario.getNome() + 
					"\nTipoUsuario: " + tipoUsuarioId);
			
			Usuario u = usuarioRepository.findByUsuarioId(usuarioId);
			TipoUsuario tipoUsuario = tipoUsuarioRepository.findByTipoUsuarioId(tipoUsuarioId);
			
			if (!usuario.getSenha().equals("")) {
				usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
			} else {
				usuario.setSenha(u.getSenha());
			}

			if (u != null && usuario.getLogin() == u.getLogin()) {
				
				log.warn("Este login já existe!");
				return "redirect:/cadastrarUsuario";
				
			} else {
				
				usuario.setTipoUsuario(tipoUsuario);
				usuario.setAcessos(u.getAcessos());
				usuario.setUltimoAcesso(u.getUltimoAcesso());
				usuarioRepository.save(usuario);
				log.info("\nUsuario " + usuario.getUsuarioId() + " - " + usuario.getNome() + " alterado!");
				
				if (!u.getLogin().equals(usuarioBean.getUsuario().getLogin())) {
					return "redirect:/detalhesUsuario?usuarioId=" + usuario.getUsuarioId();
				} else {
					return "redirect:/login";
				}
			}
		} catch (Exception e) {	
			log.error("Falha ao realizar alteracao do usuario! Motivo: " + e.getMessage());
			return "redirect:/cadastrarUsuario";
		}
	}
	
	public ModelAndView cadastrar() {
		
		ModelAndView mv = new ModelAndView("usuario/cadastrarUsuario");		
		
		if(UserPrivilegies.eStudio(usuarioBean.getUsuario())) {
			List<TipoUsuario> lsTipoUsuario = tipoUsuarioRepository.findAll(); 
			mv.addObject("lsTipoUsuario", lsTipoUsuario);
    		mv.addObject("usuarioLogado", usuarioBean.getUsuario());
    		mv.addObject("msg", "");
    		return mv;
		} else {
			mv = new ModelAndView("redirect:/index");   	
    		return mv;
		}
	}
	
	public ModelAndView cadastrarUsuario(Usuario usuario, Long tipoUsuarioId) {
	ModelAndView mv = new ModelAndView("usuario/cadastrarUsuario");
		
		try {
			
			final ParametrosSistema enviarEmailCadastro = parametrosRepository.findByChave(Parametros.ENVIAR_EMAIL_CADASTRO);
			
			Usuario u = usuarioRepository.findByLogin(usuario.getLogin());
			if(u == null) {
				u = usuarioRepository.findByEmail(usuario.getEmail());
			}
			
			TipoUsuario tipoUsuario = tipoUsuarioRepository.findByTipoUsuarioId(tipoUsuarioId);
			
			if(u != null &&  u.getLogin().equals(usuario.getLogin())) {
				
				log.info("Este login já existe para " + u.getNome());
				List<TipoUsuario> lsTipoDepartamento = tipoUsuarioRepository.findAll(); 
				mv.addObject("lsTipoUsuario", lsTipoDepartamento);
				mv.addObject("usuarioLogado", usuarioBean.getUsuario());
				mv.addObject("msg", "");
				return mv;
				
			} else if(u != null && u.getEmail().equals(usuario.getEmail())) {
				
				log.info("Este e-mail já existe para " + u.getNome());
				List<TipoUsuario> lsTipoUsuario = tipoUsuarioRepository.findAll(); 
				mv.addObject("lsTipoUsuario", lsTipoUsuario);
				mv.addObject("usuarioLogado", usuarioBean.getUsuario());
				mv.addObject("msg", "");
				return mv;
				
			} else {
				
				usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
				usuario.setTipoUsuario(tipoUsuario);
								
				usuario.setEmail(usuario.getEmail());
				usuarioRepository.save(usuario);
				
				if(enviarEmailCadastro != null && enviarEmailCadastro.getValor().equals("1")) {
					sendMail.Enviar(usuario.getEmail(),0);
				}else {
					log.info("envio de e-mail ao cadastrar desabilitado via parametro.");
				}
			
				log.info("Usuario " + usuario.getUsuarioId() + " - " + usuario.getNome() + " cadastrado!");
				return mv = new ModelAndView("redirect:/usuarios");
			}	
		} catch (Exception e) {
			
			log.error("Ocorreu um erro. Causa: " + e.getMessage());
			List<TipoUsuario> lsTipoDepartamento = tipoUsuarioRepository.findAll(); 
			mv.addObject("lsTipoDepartamento", lsTipoDepartamento);
			mv.addObject("usuarioLogado", usuarioBean.getUsuario());
			mv.addObject("msg", "");
			return mv;
		}
	}

	public ModelAndView esqueciMinhaSenhaEnviar(String email) throws UnsupportedEncodingException, MessagingException {
		
		ModelAndView mv = null;
		
		if(email != null) {			
			Usuario u = usuarioRepository.findByEmail(email);
			
			if(u != null) {
				
				log.info("Iniciando recuperacao de senha para " + u.getNome() + " | e-mail: " + u.getEmail());
				
				try {
					idRecuperacao = Scrypt.encrypt(String.valueOf(u.getUsuarioId()));
					log.info("Hash ID: " + idRecuperacao);
				} catch (InvalidKeyException e) {
					e.printStackTrace();
				} catch (BadPaddingException e) {
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (InvalidAlgorithmParameterException e) {
					e.printStackTrace();
				}
				sendMail.Enviar(email, 1);
				
				mv = new ModelAndView("login");
				mv.addObject("msg", "Recuperacao de senha enviada ao e-mail!");
				return mv;
								
			} else {
				mv = new ModelAndView("usuario/esqueciMinhaSenha");
				mv.addObject("msg", "O e-mail não foi encontrado!");
				return mv;
			} 
		} else {
			mv = new ModelAndView("usuario/esqueciMinhaSenha");
			mv.addObject("msg", "Informe o e-mail para prosseguir!");
			return mv;
		}
	}

	public ModelAndView recuperarAcesso(String id) {
		ModelAndView mv = new ModelAndView("usuario/recuperarAcesso");
		try {	
			log.info("ID Recuperacao: " + idRecuperacao);				
			mv.addObject("msg", "");
			return mv;
		} catch(Exception e) {
			log.error("recuperarAcesso: " + e.getMessage());
			mv = new ModelAndView("redirect:/login");
			return mv;
		}
	}

	public ModelAndView recuperarAcessoAgora(String id, String senha) {
		
		Usuario u = null;				
		try {		
			u = usuarioRepository.findByUsuarioId(Integer.valueOf(Scrypt.decrypt(id)));
			log.info("ID Decrypt: " + u.getUsuarioId());
			u.setSenha(new BCryptPasswordEncoder().encode(senha));
			usuarioRepository.save(u);
			log.info("Senha alterada com sucesso!");
			
		} catch (NumberFormatException | InvalidKeyException | BadPaddingException | NoSuchPaddingException
				| IllegalBlockSizeException | NoSuchAlgorithmException | InvalidAlgorithmParameterException
				| UnsupportedEncodingException e) {
			e.printStackTrace();
		}
							
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("msg", "Senha alterada com sucesso!");
		return mv;
	}

	public ModelAndView esqueciMinhaSenha() {
		
		ModelAndView mv = new ModelAndView("usuario/esqueciMinhaSenha");
		
		try {
			mv.addObject("msg", "");
			return mv;
		} catch(Exception e) {
			log.error("esqueciMinhaSenha: " + e.getMessage());
			mv = new ModelAndView("redirect:/esqueciMinhaSenha");
			return mv;
		}
	}
}