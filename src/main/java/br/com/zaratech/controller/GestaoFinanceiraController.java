package br.com.zaratech.controller;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.zaratech.service.GestaoFinanceiraService;

@Controller
public class GestaoFinanceiraController {
	
	static Logger log = Logger.getLogger(GestaoFinanceiraController.class);

	@Autowired
	private GestaoFinanceiraService gestaoFinanceiraService;
	
	@RequestMapping(value = "/gestaoFinanceira", method = RequestMethod.GET)
	public ModelAndView gestaoFinanceira(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		
		return gestaoFinanceiraService.gestaoFinanceira(pageSize, page);
	}
	
	@RequestMapping(value = "detalhesGestaoFinanceiraOS/{ordemServicoId}", method = RequestMethod.GET)
	public ModelAndView detalhesGestaoFinanceiraOS(@PathVariable(value = "ordemServicoId", required = false) long ordemServicoId) {
		
		return gestaoFinanceiraService.detalhesGestaoFinanceiraOS(ordemServicoId);
	}
	
	@RequestMapping(value = "efetuarRecebimentoGestaoFinanceira", method = RequestMethod.POST)
	public ModelAndView efetuarRecebimentoGestaoFinanceira(long gestaoFinanceiraId, boolean recebeDinheiro, boolean recebeDebito, boolean recebeCredito) {
		
		return gestaoFinanceiraService.efetuarRecebimentoGestaoFinanceira(gestaoFinanceiraId, recebeDinheiro, recebeDebito, recebeCredito);
	}
}