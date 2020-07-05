package br.com.zaratech.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.zaratech.bean.Parametros;
import br.com.zaratech.bean.UsuarioBean;
import br.com.zaratech.model.ParametrosSistema;
import br.com.zaratech.repository.ParametrosRepository;

@Controller
public class IndexController {

	static Logger log = Logger.getLogger(IndexController.class);

	@Autowired
	private UsuarioBean usuarioBean;
	@Autowired
	private ParametrosRepository parametrosRepository;
	
	@RequestMapping("/")
	public String home() {		
		if(usuarioBean != null) {
			return "redirect:/index";
		} else {
			return "redirect:/login";
		}
	}

	@RequestMapping(value = "/login")
	public ModelAndView login() {

		ModelAndView mv = new ModelAndView("login");
		mv.addObject("msg", "");
		return mv;
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
	
		ModelAndView mv = new ModelAndView("index");		
		
		if(usuarioBean.getUsuario() != null) {
			
			final ParametrosSistema modalInformativo = parametrosRepository.findByChave(Parametros.MODAL_INFORMATIVO);
			if(modalInformativo != null && modalInformativo.getChave() != null && modalInformativo.getValor().equals("1")) {
				mv.addObject("modalInformativo",true);
			}else {
				mv.addObject("modalInformativo",false);
			}	
			mv.addObject("usuarioLogado", usuarioBean.getUsuario());
			return mv;
		}else {
			mv.addObject("msg", "");
			mv = new ModelAndView("login");
			return mv;
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String isLogout() {

		return "logout";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout() {

		log.info("Logout realizado!");
		return "logout";
	}
}