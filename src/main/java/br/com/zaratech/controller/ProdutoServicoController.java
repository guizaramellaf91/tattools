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

import br.com.zaratech.model.Produto;
import br.com.zaratech.model.Servico;
import br.com.zaratech.service.ProdutoServicoService;

@Controller
public class ProdutoServicoController {

static Logger log = Logger.getLogger(ClienteController.class);
	
	@Autowired
	private ProdutoServicoService produtoServicoService;
	
	@RequestMapping(value = "/produtoServico", method = RequestMethod.GET)
	public ModelAndView produtoServico(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {

		return produtoServicoService.produtoServico(pageSize, page);
	}
	
	@RequestMapping(value = "detalhesProduto/{produtoId}", method = RequestMethod.GET)
	public ModelAndView detalhesProduto(@PathVariable(value = "produtoId", required = false) long produtoId) {
		
		return produtoServicoService.detalhesProduto(produtoId);
	}
	
	@RequestMapping(value = "alterarProduto", method = RequestMethod.GET)
	public ModelAndView alterarProduto(Produto produto) {
		
		return produtoServicoService.alterarProduto(produto);
	}
	
	@RequestMapping(value = "detalhesServico/{servicoId}", method = RequestMethod.GET)
	public ModelAndView detalhesServico(@PathVariable(value = "servicoId", required = false) long servicoId) {
		
		return produtoServicoService.detalhesServico(servicoId);
	}
	
	@RequestMapping(value = "alterarServico", method = RequestMethod.GET)
	public ModelAndView alterarServico(Servico servico) {
		
		return produtoServicoService.alterarServico(servico);
	}
	
	@RequestMapping(value = "novoProduto", method = RequestMethod.POST)
	public ModelAndView novoProduto(Produto produto) {
		
		return produtoServicoService.novoProduto(produto);
	}
	
	@RequestMapping(value = "novoServico", method = RequestMethod.POST)
	public ModelAndView novoServico(Servico servico) {
		
		return produtoServicoService.novoServico(servico);
	}
}