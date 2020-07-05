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

import br.com.zaratech.model.Cliente;
import br.com.zaratech.model.Endereco;
import br.com.zaratech.service.ClienteService;

@Controller
public class ClienteController {

	static Logger log = Logger.getLogger(ClienteController.class);
		
	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	public ModelAndView clientes(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {

		return clienteService.clientes(pageSize, page);
	}
	
    @RequestMapping(value = "/buscarCliente")
	public ModelAndView buscarClientes(String nomeCliente, @RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page){   
    	
    	return clienteService.buscarClientes(nomeCliente, pageSize, page);   
	}
		
	@RequestMapping(value = "/cadastrarCliente", method = RequestMethod.GET)
	public ModelAndView cadastrar() {		
		
		return clienteService.cadastrar();
	}

	@RequestMapping(value = "/cadastrarCliente", method = RequestMethod.POST)
	public ModelAndView cadastrar(Cliente cliente, Endereco endereco) {		
		
		return clienteService.cadastrar(cliente, endereco);
	}

	@RequestMapping(value = "detalhesCliente/{clienteId}", method = RequestMethod.GET)
	public ModelAndView detalhesCliente(@PathVariable(value = "clienteId", required = false) long clienteId) {
		
		return clienteService.detalhesCliente(clienteId);
	}

	@RequestMapping(value = "detalhesCliente/{clienteId}", method = RequestMethod.POST)
	public String alterarCliente(@PathVariable(value = "clienteId", required = false) long clienteId, Cliente cliente) {	
		
		return clienteService.alterarCliente(clienteId, cliente);
	}
	
	@RequestMapping(value = "deletarCliente/{clienteId}", method = RequestMethod.GET)
	public String deletarCliente(@PathVariable(value = "clienteId", required = false) long clienteId) {
	
		return clienteService.deletarCliente(clienteId);
	}
}