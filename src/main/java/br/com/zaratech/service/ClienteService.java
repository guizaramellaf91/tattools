package br.com.zaratech.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.zaratech.bean.Parametros;
import br.com.zaratech.bean.UsuarioBean;
import br.com.zaratech.model.Cliente;
import br.com.zaratech.model.Endereco;
import br.com.zaratech.model.PagerModel;
import br.com.zaratech.model.ParametrosSistema;
import br.com.zaratech.repository.ClienteRepository;
import br.com.zaratech.repository.EnderecoRepository;
import br.com.zaratech.repository.OrdemServicoRepository;
import br.com.zaratech.repository.ParametrosRepository;

@Service
public class ClienteService {

	static Logger log = Logger.getLogger(ClienteService.class);
	
	private static final int BUTTONS_TO_SHOW = 3;
	private static final int INITIAL_PAGE = 0;
	private static final int[] PAGE_SIZES = {5, 10};
	
	@Autowired
    private UsuarioBean usuarioBean;
	@Autowired
	private ParametrosRepository parametrosRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	public ModelAndView clientes(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		
		ModelAndView mv = new ModelAndView("cliente/clientes");		
		try {

			int evalPageSize;
			int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

			final ParametrosSistema pageSizeList = parametrosRepository.findByChave(Parametros.PAGE_SIZE_LIST);
			if (pageSizeList != null && pageSizeList.getChave() != null && pageSizeList.getValor() != null) {
				evalPageSize = pageSize.orElse(Integer.valueOf(pageSizeList.getValor()));
			} else {
				evalPageSize = 5;
			}

			usuarioBean.setPageSize(evalPageSize);
			usuarioBean.setPage((page.orElse(0) < 1) ? INITIAL_PAGE : page.get());

			Page<Cliente> lsClientes = clienteRepository
					.findAll(PageRequest.of(evalPage, evalPageSize, Sort.by("nome").ascending()));
			PagerModel pager = new PagerModel(lsClientes.getTotalPages(), lsClientes.getNumber(), BUTTONS_TO_SHOW);
			mv.addObject("selectedPageSize", evalPageSize);
			mv.addObject("pageSizes", PAGE_SIZES);
			mv.addObject("pager", pager);
			mv.addObject("msg", "");
			mv.addObject("usuarioLogado", usuarioBean.getUsuario());
			mv.addObject("lsClientes", lsClientes);
			mv.addObject("quantidadeClientes", clienteRepository.totalClientes());
			return mv;
		} catch (Exception e) {
			log.error(e.getMessage());
			mv = new ModelAndView("index");
			return mv;
		}
	}

	public ModelAndView buscarClientes(String nomeCliente, @RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page){  
		
		ModelAndView mv = new ModelAndView("cliente/clientes");
	    
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
		
    	if(nomeCliente != "") {
    		List<Cliente> clientes = clienteRepository.findByNomeContaining(nomeCliente);		
    		if(clientes.size() <= 0) {
    			Page<Cliente> cliente = clienteRepository.findAll(PageRequest.of(evalPage, evalPageSize, Sort.by("nomeCliente").ascending()));
    			PagerModel pager = new PagerModel(cliente.getTotalPages(),cliente.getNumber(),BUTTONS_TO_SHOW);
    			mv.addObject("selectedPageSize", evalPageSize);
    			mv.addObject("pageSizes", PAGE_SIZES);
    			mv.addObject("pager", pager);
    			mv.addObject("listarClientes", cliente);
    		} else {
    			mv.addObject("searchCliente", true);
    			mv.addObject("listarClientes", clientes);
    		}
    		mv.addObject("usuarioLogado", usuarioBean.getUsuario());
    		mv.addObject("quantidadeClientes", clienteRepository.totalClientes());
    		return mv;
    	}else {
    		mv = new ModelAndView("redirect:/clientes");
    		return mv;
    	}   	
	}

	public ModelAndView cadastrar() {	
		
		ModelAndView mv = null;
		if(usuarioBean.getUsuario().getTipoUsuario().getNomeTipo().equals("STUDIO")) {			
			mv = new ModelAndView("cliente/cadastrarCliente");   	
			mv.addObject("usuarioLogado", usuarioBean.getUsuario());
    		mv.addObject("msg", "");
    		return mv;
		}else {
			mv = new ModelAndView("redirect:/index");   	
    		return mv;
		}
	}
	
	public ModelAndView cadastrar(Cliente cliente, Endereco endereco) {		
		
		ModelAndView mv = new ModelAndView("cliente/cadastrarCliente");
		
		try {
			
			Cliente cCPF = new Cliente();
			Cliente cEmail = new Cliente();
			
			Cliente cNome = clienteRepository.findByNome(cliente.getNome());
			
			if(!cliente.getCpf().isEmpty()) {
				cCPF = clienteRepository.findByCpf(cliente.getCpf());
			}
			if(!cliente.getEmail().isEmpty()) {
				cEmail = clienteRepository.findByEmail(cliente.getEmail());
			}
			
			if(cNome != null) {
				log.warn("O nome informado já existe!");
				return mv = new ModelAndView("redirect:/cadastrarCliente");
			}else if (!cliente.getCpf().isEmpty() && cCPF != null) {
				log.warn("O CPF informado já existe!");
				return mv = new ModelAndView("redirect:/cadastrarCliente");
			}else if (!cliente.getEmail().isEmpty() && cEmail != null) {
				log.warn("O e-mail informado já existe!");
				return mv = new ModelAndView("redirect:/cadastrarCliente");
			}
						
			if(cliente.getDataNascimento() == null) {
				cliente.setDataNascimento(new Date());
			}
			
			cliente.setUsuario(usuarioBean.getUsuario());
			cliente.setDataCadastro(new Date());
			cliente.setEndereco(endereco);
			clienteRepository.save(cliente);
			enderecoRepository.save(endereco);
			
			log.info("\nCliente " + cliente.getClienteId() + " - " + cliente.getNome() + " cadastrado!");
			mv.addObject("usuarioLogado", usuarioBean.getUsuario());
			return mv;
			
		} catch (Exception e) {
			
			log.error("Ocorreu um erro. Causa: " + e.getMessage());
			mv.addObject("usuarioLogado", usuarioBean.getUsuario());
			return mv;
		}
	}

	public ModelAndView detalhesCliente(@PathVariable(value = "clienteId", required = false) long clienteId) {
		
		Cliente cliente = clienteRepository.findByClienteId(clienteId);
		ModelAndView mv = new ModelAndView("cliente/detalhesCliente");
		mv.addObject("pageSizeNavi",usuarioBean.getPageSize());
		mv.addObject("pageNavi",usuarioBean.getPage());
		mv.addObject("usuarioLogado", usuarioBean.getUsuario());	
		mv.addObject("quantidadeOS", ordemServicoRepository.totalOSCliente(clienteId));
		mv.addObject("cliente", cliente);
		return mv;
	}

	public String alterarCliente(@PathVariable(value = "clienteId", required = false) long clienteId, Cliente cliente) {	
		
		try {
			Cliente c = clienteRepository.findByClienteId(clienteId);
			Cliente cexists = clienteRepository.findByNome(cliente.getNome());
			if(cexists != null && !c.equals(cexists)) {
				log.warn("O cliente " + cliente.getNome() + " informado já existe!");
				return "redirect:/detalhesCliente/" + clienteId;
			}
			c.setNome(cliente.getNome());
			clienteRepository.save(c);
			log.info("Cliente " + cliente.getClienteId() + " - " + cliente.getNome() + " alterado!");
			return "redirect:/detalhesCliente/" + clienteId;
		} catch (Exception e) {
			log.info("alterar: " + e.getMessage());
			return "redirect:/cadastrarCliente";
		}
	}

	public String deletarCliente(@PathVariable(value = "clienteId", required = false) long clienteId) {
		
		Cliente cliente = clienteRepository.findByClienteId(clienteId);
		clienteRepository.delete(cliente);
		log.info("Cliente " + cliente.getNome() + " excluido com sucesso!");		
		return "redirect:/clientes";
	}
}