package br.com.zaratech.controller;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.zaratech.service.FinancasService;

@Controller
public class FinancasController {

	static Logger log = Logger.getLogger(FinancasController.class);

	@Autowired
	private FinancasService financasService;
	
	@RequestMapping(value = "/financas", method = RequestMethod.GET)
	public ModelAndView financas(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		
		return financasService.financas(pageSize, page);
	}	
}
