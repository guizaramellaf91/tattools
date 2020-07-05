package br.com.zaratech.service;

import java.util.Date;
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
import br.com.zaratech.model.PagerModel;
import br.com.zaratech.model.ParametrosSistema;
import br.com.zaratech.model.Produto;
import br.com.zaratech.model.Servico;
import br.com.zaratech.repository.ParametrosRepository;
import br.com.zaratech.repository.ProdutoRepository;
import br.com.zaratech.repository.ServicoRepository;

@Service
public class ProdutoServicoService {
	
	static Logger log = Logger.getLogger(ProdutoServicoService.class);
	
	private static final int BUTTONS_TO_SHOW = 3;
	private static final int INITIAL_PAGE = 0;
	private static final int[] PAGE_SIZES = {5, 10};
	
	@Autowired
    private UsuarioBean usuarioBean;
	@Autowired
	private ParametrosRepository parametrosRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private ServicoRepository servicoRepository;
	
	public ModelAndView produtoServico(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		
		ModelAndView mv = new ModelAndView("produtoServico/produtoServico");

		try {
			
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

			Page<Produto> lsProdutos = produtoRepository.findAll(PageRequest.of(evalPage, evalPageSize, Sort.by("descricao").ascending()));
			Page<Servico> lsServicos = servicoRepository.findAll(PageRequest.of(evalPage, evalPageSize, Sort.by("descricao").ascending()));
			PagerModel pager = new PagerModel(lsProdutos.getTotalPages(), lsProdutos.getNumber(), BUTTONS_TO_SHOW);
			PagerModel pager2 = new PagerModel(lsServicos.getTotalPages(), lsServicos.getNumber(), BUTTONS_TO_SHOW);
			mv.addObject("selectedPageSize", evalPageSize);
			mv.addObject("pageSizes", PAGE_SIZES);
			mv.addObject("pager", pager);
			mv.addObject("pager2", pager2);
			mv.addObject("msg", "");
			mv.addObject("usuarioLogado", usuarioBean.getUsuario());
			mv.addObject("lsProdutos", lsProdutos);
			mv.addObject("lsServicos", lsServicos);
			mv.addObject("quantidadeProdutos", produtoRepository.totalProdutos());
			mv.addObject("quantidadeServicos", servicoRepository.totalServicos());
			return mv;
		} catch (Exception e) {
			log.error(e.getMessage());
			mv = new ModelAndView("index");
			return mv;
		}
	}

	public ModelAndView detalhesProduto(@PathVariable(value = "produtoId", required = false) long produtoId) {
		
		try {
			Produto produto = produtoRepository.findByProdutoId(produtoId);
			ModelAndView mv = new ModelAndView("produtoServico/detalhesProduto");
			mv.addObject("usuarioLogado", usuarioBean.getUsuario());
			mv.addObject("produto", produto);
			return mv;
		} catch (Exception e) {
			return new ModelAndView("redirect:/index");
		}
	}
	
	public ModelAndView alterarProduto(Produto produto) {
		
		ModelAndView mv = new ModelAndView("redirect:../produtoServico");
		try {
			produto.setDataAlteracao(new Date());
			produtoRepository.save(produto);
			log.info("Produto ID - " + produto.getProdutoId() + " alterado!");
			return mv;
		} catch (Exception e) {
			return mv = new ModelAndView("redirect:../produtoServico");
		}
	}
	
	public ModelAndView detalhesServico(@PathVariable(value = "servicoId", required = false) long servicoId) {
		
		Servico servico = servicoRepository.findByServicoId(servicoId);
		ModelAndView mv = new ModelAndView("produtoServico/detalhesServico");
		mv.addObject("usuarioLogado", usuarioBean.getUsuario());
		mv.addObject("servico", servico);
		return mv;
	}

	public ModelAndView alterarServico(Servico servico) {
		
		ModelAndView mv = new ModelAndView("redirect:../produtoServico");
		try {
			servico.setDataAlteracao(new Date());
			servicoRepository.save(servico);
			log.info("Servico ID - " + servico.getServicoId() + " alterado!");
			return mv;
		} catch (Exception e) {
			return mv = new ModelAndView("redirect:../produtoServico");
		}
	}
	
	public ModelAndView novoProduto(Produto produto) {
		
		ModelAndView mv = new ModelAndView("redirect:/produtoServico");
		produtoRepository.save(produto);
		log.info("Novo produto inserido no banco!");
		mv.addObject("usuarioLogado", usuarioBean.getUsuario());
		return mv;
	}
	
	public ModelAndView novoServico(Servico servico) {
		
		ModelAndView mv = new ModelAndView("redirect:/produtoServico");
		
		servicoRepository.save(servico);
		log.info("Novo serviço inserido no banco!");
		mv.addObject("usuarioLogado", usuarioBean.getUsuario());
		return mv;
	}
}