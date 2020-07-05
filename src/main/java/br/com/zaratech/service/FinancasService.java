package br.com.zaratech.service;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.zaratech.bean.Parametros;
import br.com.zaratech.bean.UsuarioBean;
import br.com.zaratech.model.GestaoFinanceira;
import br.com.zaratech.model.OrdemServico;
import br.com.zaratech.model.PagerModel;
import br.com.zaratech.model.ParametrosSistema;
import br.com.zaratech.repository.ClienteRepository;
import br.com.zaratech.repository.EnderecoRepository;
import br.com.zaratech.repository.GestaoFinanceiraRepository;
import br.com.zaratech.repository.OrdemServicoRepository;
import br.com.zaratech.repository.ParametrosRepository;
import br.com.zaratech.repository.UsuarioRepository;

@Service
public class FinancasService {

	static Logger log = Logger.getLogger(FinancasService.class);

	private static final int BUTTONS_TO_SHOW = 3;
	private static final int INITIAL_PAGE = 0;
	
	@Autowired
    private UsuarioBean usuarioBean;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	@Autowired
	private GestaoFinanceiraRepository gestaoFinanceiraRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ParametrosRepository parametrosRepository;
	
	@RequestMapping(value = "/financas", method = RequestMethod.GET)
	public ModelAndView financas(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		
		ModelAndView mv = new ModelAndView("financas/financas");
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
			
			Page<OrdemServico> lsOrdemServico = ordemServicoRepository.buscarTodasValidas(PageRequest.of(evalPage, evalPageSize, Sort.by("ordemServicoId").descending()));
			Page<GestaoFinanceira> lsGestaoFinanceira = gestaoFinanceiraRepository.buscarTodasValidas(PageRequest.of(evalPage, evalPageSize, Sort.by("gestaoFinanceiraId").descending()));
			
			mv.addObject("usuarioLogado", usuarioBean.getUsuario());
			
			mv.addObject("administradoresCadastrados", usuarioRepository.totalAdministradores());
			mv.addObject("tatuadoresCadastrados", usuarioRepository.totalTatuadores());
			mv.addObject("osValidas", ordemServicoRepository.totalOSvalidas());
			mv.addObject("osInvalidas", ordemServicoRepository.totalOSinvalidas());
			mv.addObject("financeirosValidos", gestaoFinanceiraRepository.totalGestaoFinanceiraValidos());
			mv.addObject("financeirosInvalidos", gestaoFinanceiraRepository.totalGestaoFinanceiraInvalidos());
			mv.addObject("clientesCadastrados", clienteRepository.totalClientes());
			mv.addObject("enderecosCadastrados", enderecoRepository.totalEnderecos());
			
			mv.addObject("valorTotalGestaoFinanceiraValidas", gestaoFinanceiraRepository.valorTotalGestaoFinanceiraValidas());
			mv.addObject("valorTotalStudioValidos", gestaoFinanceiraRepository.valorTotalStudioValidos());
			mv.addObject("valorTotalTatuadorValidos", gestaoFinanceiraRepository.valorTotalTatuadorValidos());
			
			mv.addObject("valorTotalStudioReceberValidos", gestaoFinanceiraRepository.valorTotalStudioReceberValidos());
			mv.addObject("valorTotalTatuadorReceberValidos", gestaoFinanceiraRepository.valorTotalTatuadorReceberValidos());
			mv.addObject("valorTotalReceberValidos", gestaoFinanceiraRepository.valorTotalReceberValidos());
			
			mv.addObject("valorTotalStudioRecebidoValidos", gestaoFinanceiraRepository.valorTotalStudioRecebidoValidos());
			mv.addObject("valorTotalTatuadorRecebidoValidos", gestaoFinanceiraRepository.valorTotalTatuadorRecebidoValidos());
			mv.addObject("valorTotalRecebidoValidos", gestaoFinanceiraRepository.valorTotalRecebidoValidos());
			
			mv.addObject("lsOrdemServico", lsOrdemServico);
			PagerModel pager = new PagerModel(lsOrdemServico.getTotalPages(), lsOrdemServico.getNumber(), BUTTONS_TO_SHOW);
			mv.addObject("selectedPageSize", evalPageSize);
			mv.addObject("pageSize", evalPageSize);
			mv.addObject("pager", pager);
			
			mv.addObject("lsGestaoFinanceira", lsGestaoFinanceira);
			PagerModel pagerGestaoFinanceira = new PagerModel(lsOrdemServico.getTotalPages(), lsOrdemServico.getNumber(), BUTTONS_TO_SHOW);
			mv.addObject("selectedPageSizeGestaoFinanceira", evalPageSize);
			mv.addObject("pageSizeGestaoFinanceira", evalPageSize);
			mv.addObject("pagerGestaoFinanceira", pagerGestaoFinanceira);
			
			return mv;
		} catch (Exception e) {
			return new ModelAndView("redirect:/index");
		}
	}
}
