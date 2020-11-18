package br.com.zaratech.controller;

import br.com.zaratech.service.IndexService;
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

	@Autowired
	private IndexService indexService;
	
	@RequestMapping("/")
	public String home() {		
		return indexService.home();
	}

	@RequestMapping(value = "/login")
	public ModelAndView login() {
		return indexService.login();
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		return indexService.index();
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String isLogout() {
		return indexService.isLogout();
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout() {
		return indexService.logout();
	}
}