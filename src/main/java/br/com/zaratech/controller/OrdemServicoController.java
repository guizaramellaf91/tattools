package br.com.zaratech.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.zaratech.model.OrdemServico;
import br.com.zaratech.service.OrdemServicoService;
import net.sf.jasperreports.engine.JRException;

@Controller
public class OrdemServicoController {
	
	static Logger log = Logger.getLogger(OrdemServicoController.class);

	@Autowired
	private OrdemServicoService ordemServicoService;

	@RequestMapping(value = "/ordemServico", method = RequestMethod.GET)
	public ModelAndView ordemServico(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {

		return ordemServicoService.ordemServico(pageSize, page);
	}
	
	@RequestMapping(value = "/buscaOS")
	public ModelAndView buscarOS(String nOS, @RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page){   
    			
		return ordemServicoService.buscaOS(nOS, pageSize, page);
	}
	
	@RequestMapping(value = "/inativarOS/{ordemServicoId}", method = RequestMethod.GET)
	public ModelAndView inativarOS(@PathVariable(value = "ordemServicoId", required = false) long ordemServicoId) {
		
		return ordemServicoService.inativarOS(ordemServicoId);
	}
	
	@RequestMapping(value = "detalhesOS/{ordemServicoId}", method = RequestMethod.GET)
	public ModelAndView detalhesOS(@PathVariable(value = "ordemServicoId", required = false) long ordemServicoId) {
		
		return ordemServicoService.detalhesOS(ordemServicoId);
	}
	
	@RequestMapping(value = "imprimirOS/{ordemServicoId}", method = RequestMethod.GET)
	public void imprimirOS(@PathVariable(value = "ordemServicoId", required = false) long ordemServicoId, HttpServletResponse response) throws JRException, IOException {
		
		ordemServicoService.imprimirOS(ordemServicoId, response);
	}
	
	@RequestMapping(value = "novaOS", method = RequestMethod.GET)
	public ModelAndView novaOS(OrdemServico ordemServico) {
		
		return ordemServicoService.novaOS();
	}
	
	@RequestMapping(value = "novaOS", method = RequestMethod.POST)
	public ModelAndView gerarOrdemServico(OrdemServico ordemServico, Long produtoId, Long servicoId, Long totalParcelasCredito, BigDecimal saqueTatuador) {
		
		return ordemServicoService.gerarOrdemServico(ordemServico, produtoId, servicoId, totalParcelasCredito, saqueTatuador);
	}
}