package br.com.zaratech.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.zaratech.bean.Parametros;
import br.com.zaratech.bean.UsuarioBean;
import br.com.zaratech.model.Cliente;
import br.com.zaratech.model.GestaoFinanceira;
import br.com.zaratech.model.GestaoFinanceiraParcelasCredito;
import br.com.zaratech.model.GestaoFinanceiraSaques;
import br.com.zaratech.model.OrdemServico;
import br.com.zaratech.model.PagerModel;
import br.com.zaratech.model.ParametrosSistema;
import br.com.zaratech.model.Produto;
import br.com.zaratech.model.Servico;
import br.com.zaratech.repository.ClienteRepository;
import br.com.zaratech.repository.GestaoFinanceiraParcelasCreditoRepository;
import br.com.zaratech.repository.GestaoFinanceiraRepository;
import br.com.zaratech.repository.GestaoFinanceiraSaquesRepository;
import br.com.zaratech.repository.OrdemServicoRepository;
import br.com.zaratech.repository.ParametrosRepository;
import br.com.zaratech.repository.ProdutoRepository;
import br.com.zaratech.repository.ServicoRepository;
import br.com.zaratech.security.UserPrivilegies;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class OrdemServicoService {

	static Logger log = Logger.getLogger(OrdemServicoService.class);
	
	private static final int BUTTONS_TO_SHOW = 3;
	private static final int INITIAL_PAGE = 0;
	
	@Autowired
    private UsuarioBean usuarioBean;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private ServicoRepository servicoRepository;
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	@Autowired
	private GestaoFinanceiraRepository gestaoFinanceiraRepository;
	@Autowired
	private GestaoFinanceiraSaquesRepository gestaoFinanceiraSaquesRepository;
	@Autowired
	private GestaoFinanceiraParcelasCreditoRepository gestaoFinanceiraParcelasCreditoRepository;
	@Autowired
	private ParametrosRepository parametrosRepository;
	
	public ModelAndView ordemServico(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		
		ModelAndView mv = new ModelAndView("ordemServico/ordemServico");

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

			Page<OrdemServico> lsOrdemServico;
			if(usuarioBean.getUsuario().getTipoUsuario().getNomeTipo().equals(Parametros.STUDIO)) {
				lsOrdemServico = ordemServicoRepository.buscarTodasValidas(PageRequest.of(evalPage, evalPageSize, Sort.by("ordemServicoId").descending()));
			}else {
				lsOrdemServico = ordemServicoRepository.buscarTodasValidasPorUsuario(usuarioBean.getUsuario(), PageRequest.of(evalPage, evalPageSize, Sort.by("ordemServicoId").descending()));
			}
			PagerModel pager = new PagerModel(lsOrdemServico.getTotalPages(), lsOrdemServico.getNumber(), BUTTONS_TO_SHOW);
			mv.addObject("selectedPageSize", evalPageSize);
			mv.addObject("pageSizes", evalPageSize);
			mv.addObject("pager", pager);
			mv.addObject("usuarioLogado", usuarioBean.getUsuario());
			mv.addObject("lsOrdemServico", lsOrdemServico);
			try {
				if(usuarioBean.getUsuario().getTipoUsuario().getNomeTipo().equals(Parametros.STUDIO)) {
					mv.addObject("valorTotalOSs", ordemServicoRepository.valorTotalOS());
					mv.addObject("quantidadeOS", ordemServicoRepository.totalOSvalidas());
				}else {
					mv.addObject("valorTotalOSs", ordemServicoRepository.valorTotalOSUsuario(usuarioBean.getUsuario()));
					mv.addObject("quantidadeOS", ordemServicoRepository.totalOSUsuario(usuarioBean.getUsuario()));
				}
			} catch (Exception e) {
				mv.addObject("valorTotalOSs", 0);
				mv.addObject("quantidadeOS", ordemServicoRepository.totalOSUsuario(usuarioBean.getUsuario()));
			}			
			return mv;
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/index");
		}	
	}

	public ModelAndView buscaOS(String nOS, @RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		
		ModelAndView mv = new ModelAndView("ordemServico/ordemServico");
	    
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
			
	    	if(nOS != null) {
	    		List<OrdemServico> lsOrdemServico = ordemServicoRepository.buscarPorOrdemServicoId(Long.valueOf(nOS));		
	    		if(lsOrdemServico.size() <= 0) {
	    			Page<OrdemServico> ordemServico;
	    			if(usuarioBean.getUsuario().getTipoUsuario().getNomeTipo().equals(Parametros.STUDIO)) {
	    				ordemServico = ordemServicoRepository.buscarTodasValidas(PageRequest.of(evalPage, evalPageSize, Sort.by("ordemServicoId").descending()));
	    			}else {
	    				ordemServico = ordemServicoRepository.buscarTodasValidasPorUsuario(usuarioBean.getUsuario(), PageRequest.of(evalPage, evalPageSize, Sort.by("ordemServicoId").descending()));
	    			}
	    			PagerModel pager = new PagerModel(ordemServico.getTotalPages(),ordemServico.getNumber(),BUTTONS_TO_SHOW);
	    			mv.addObject("selectedPageSize", evalPageSize);
	    			mv.addObject("pageSizes", evalPageSize);
	    			mv.addObject("pager", pager);
	    			mv.addObject("lsOrdemServico", ordemServico);
	    		} else {
	    			mv.addObject("searchOS", true);
	    			mv.addObject("lsOrdemServico", lsOrdemServico);
	    		}
	    		mv.addObject("usuarioLogado", usuarioBean.getUsuario());
				try {
					if(usuarioBean.getUsuario().getTipoUsuario().getNomeTipo().equals(Parametros.STUDIO)) {
						mv.addObject("valorTotalOSs", ordemServicoRepository.valorTotalOS());
						mv.addObject("quantidadeOS", ordemServicoRepository.totalOSvalidas());
					}else {
						mv.addObject("valorTotalOSs", ordemServicoRepository.valorTotalOSUsuario(usuarioBean.getUsuario()));
						mv.addObject("quantidadeOS", ordemServicoRepository.totalOSUsuario(usuarioBean.getUsuario()));
					}
				} catch (Exception e) {
					mv.addObject("valorTotalOSs", 0);
					mv.addObject("quantidadeOS", ordemServicoRepository.totalOSUsuario(usuarioBean.getUsuario()));
				}
	    		return mv;
	    	}else {
	    		return new ModelAndView("redirect:/ordemServico");
	    	}  
		} catch (Exception e) {
			return new ModelAndView("redirect:/ordemServico");
		}
	}
	
	public ModelAndView inativarOS(long ordemServicoId) {
		
		ModelAndView mv = new ModelAndView("redirect:/ordemServico");
		mv.addObject("usuarioLogado", usuarioBean.getUsuario());
		
		try {
			OrdemServico ordemServico = ordemServicoRepository.findByOrdemServicoId(ordemServicoId);
			GestaoFinanceira gestaoFinanceira = gestaoFinanceiraRepository.buscaPorOrdemServicoId(ordemServicoId);
			ordemServico.setValida(false);
			gestaoFinanceira.setValida(false);
			ordemServicoRepository.save(ordemServico);
			log.info("OS " + ordemServico.getOrdemServicoId() + " inativada pelo usuario " + usuarioBean.getUsuario().getNome());
			log.info("Gestao Financeira " + gestaoFinanceira.getGestaoFinanceiraId() + " inativada pelo usuario " + usuarioBean.getUsuario().getNome());
			return mv;
		} catch(Exception e) {
			log.error("Ocorreu um erro. Causa: " + e.getMessage());
			return mv;
		}	
	}
	
 	public ModelAndView detalhesOS(long ordemServicoId) {
		
		ModelAndView mv = new ModelAndView("ordemServico/detalhesOS");
		mv.addObject("usuarioLogado", usuarioBean.getUsuario());
		
		try {
			OrdemServico ordemServico = ordemServicoRepository.findByOrdemServicoId(ordemServicoId);
			mv.addObject("ordemServico", ordemServico);
			GestaoFinanceira gestaoFinanceira = gestaoFinanceiraRepository.buscaPorOrdemServicoId(ordemServico.getOrdemServicoId());			
			if(gestaoFinanceira != null) {
				mv.addObject("gestaoFinanceira", gestaoFinanceira.getGestaoFinanceiraId());
			}else {
				mv.addObject("gestaoFinanceira", "N/A");
				ordemServico.setValida(false);
				ordemServicoRepository.save(ordemServico);
				log.error("OS " + ordemServico.getOrdemServicoId() + " invalida! Nao possui registro financeiro vinculado");
				return mv = new ModelAndView("redirect:/ordemServico");
			}
			return mv;
		} catch(Exception e) {
			log.error("Ocorreu um erro. Causa: " + e.getMessage());
			return mv = new ModelAndView("redirect:/ordemServico");
		}		
	}
	
	public ModelAndView novaOS() {
		
		ModelAndView mv = new ModelAndView("ordemServico/novaOS");
		ParametrosSistema adminGeraOS = parametrosRepository.findByChave(Parametros.ADMIN_GERA_OS);
		
		if(UserPrivilegies.eTatuador(usuarioBean.getUsuario()) || adminGeraOS != null && adminGeraOS.getValor().equals(Parametros.ATIVO)) {
			try {
				
				ArrayList<Integer> parcelas = new ArrayList<Integer>();
				int parcelasReal = 0;
				for(int i = 0; i < usuarioBean.getUsuario().getQuantidadeParcelas(); i++) {
					parcelasReal++;
					parcelas.add(parcelasReal);
				}
				
				List<Cliente> lsCliente = clienteRepository.findAll();
				List<Produto> lsProduto = produtoRepository.findProdutosAtivos();
				List<Servico> lsServico = servicoRepository.findServicosAtivos();
				
				mv.addObject("valorSaquePendente", gestaoFinanceiraSaquesRepository.valorSaquesPendentes(usuarioBean.getUsuario().getUsuarioId()));
				mv.addObject("parcelasUsuario", parcelas);
				mv.addObject("lsCliente", lsCliente);
				mv.addObject("lsProduto", lsProduto);
				mv.addObject("lsServico", lsServico);
				mv.addObject("usuarioLogado", usuarioBean.getUsuario());
				
				return mv;
			} catch(Exception e) {
				log.error("Ocorreu um erro. Causa: " + e.getMessage());
				return mv = new ModelAndView("redirect:/index");
			}
		} else {
			log.warn("Esta inativo ou nao existe parametro definido para administrador gerar OS.");
			return mv = new ModelAndView("redirect:/ordemServico");
		}
	}
	
	public ModelAndView gerarOrdemServico(OrdemServico ordemServico, Long produtoId, Long servicoId, Long totalParcelasCredito, BigDecimal saqueTatuador) {

		ModelAndView mv = new ModelAndView("redirect:/ordemServico");
		mv.addObject("usuarioLogado", usuarioBean.getUsuario());
		GestaoFinanceira gestaoFinanceira = new GestaoFinanceira();
		GestaoFinanceiraSaques gestaoFinanceiraSaques = new GestaoFinanceiraSaques();
		
		try {
			
			Produto produto;
			Servico servico;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateCadastroOS = simpleDateFormat.format(ordemServico.getDataCadastro());	
			
			if(saqueTatuador == null) {
				saqueTatuador = BigDecimal.ZERO;
			}
			
			if(totalParcelasCredito == null) {
				totalParcelasCredito = (long) 0;
			} 
				
			if(produtoId != null) {
				produto = produtoRepository.findByProdutoId(produtoId);
				ordemServico.setProduto(produto);
			}
			if(servicoId != null) {
				servico = servicoRepository.findByServicoId(servicoId);
				ordemServico.setServico(servico);
			}
			
			if(ordemServico.getDescricao().isEmpty()) {
				ordemServico.setDescricao("OS-" + usuarioBean.getUsuario().getLogin() + "-" + dateCadastroOS);
			}
			
			ordemServico.setUsuario(usuarioBean.getUsuario());
			ordemServico.setDataCadastro(ordemServico.getDataCadastro());
			ordemServico.setDataAlteracao(new Date());
			ordemServico.setValida(true);
			
			ordemServico.setTotalDinheiro(ordemServico.getTotalDinheiro() == null ? new BigDecimal(0.00) : ordemServico.getTotalDinheiro());
			ordemServico.setTotalDebito(ordemServico.getTotalDebito() == null ? new BigDecimal(0.00) : ordemServico.getTotalDebito());
			ordemServico.setTotalCredito(ordemServico.getTotalCredito() == null ? new BigDecimal(0.00) : ordemServico.getTotalCredito());
			ordemServico.setValorProduto(ordemServico.getValorProduto() == null ? new BigDecimal(0.00) : ordemServico.getValorProduto());
			ordemServico.setValorServico(ordemServico.getValorServico() == null ? new BigDecimal(0.00) : ordemServico.getValorServico());
			
			if(totalParcelasCredito == 1 && ordemServico.getTotalCredito().equals(BigDecimal.ZERO)) {
				totalParcelasCredito = (long) 0;
			}
			
			ordemServico.setTotalParcelasCredito(totalParcelasCredito);			
			ordemServicoRepository.save(ordemServico);
			
			gestaoFinanceira.setOrdemServico(ordemServico);
			gestaoFinanceira.setValorTotal(ordemServico.getValorTotal());
			gestaoFinanceira.setDataCadastro(ordemServico.getDataCadastro());
			gestaoFinanceira.setDataAlteracao(ordemServico.getDataAlteracao());
			gestaoFinanceira.setDescricao("OS REFERENCIA: " + ordemServico.getOrdemServicoId());
			gestaoFinanceira.setCliente(ordemServico.getCliente());
			gestaoFinanceira.setUsuario(usuarioBean.getUsuario());
			gestaoFinanceira.setValida(true);
						
			gestaoFinanceira.setPercentualAplicadoStudioDinheiro(new BigDecimal(1).subtract(usuarioBean.getUsuario().getComissaoDinheiro()));
			gestaoFinanceira.setPercentualAplicadoTatuadorDinheiro(usuarioBean.getUsuario().getComissaoDinheiro());
			
			gestaoFinanceira.setPercentualAplicadoStudioDebito(new BigDecimal(1).subtract(usuarioBean.getUsuario().getComissaoDebito()));
			gestaoFinanceira.setPercentualAplicadoTatuadorDebito(usuarioBean.getUsuario().getComissaoDebito());
			
			gestaoFinanceira.setPercentualAplicadoStudioCredito(new BigDecimal(1).subtract(usuarioBean.getUsuario().getComissaoCredito()));
			gestaoFinanceira.setPercentualAplicadoTatuadorCredito(usuarioBean.getUsuario().getComissaoCredito());
			
			gestaoFinanceira.setPercentualAplicadoStudioProduto(new BigDecimal(1).subtract(usuarioBean.getUsuario().getComissaoProduto()));
			gestaoFinanceira.setPercentualAplicadoTatuadorProduto(usuarioBean.getUsuario().getComissaoProduto());
			
			gestaoFinanceira.setTotalComissaoStudioProduto(ordemServico.getValorProduto().multiply(gestaoFinanceira.getPercentualAplicadoStudioProduto()).setScale(2, BigDecimal.ROUND_HALF_UP));
			gestaoFinanceira.setTotalComissaoTatuadorProduto(ordemServico.getValorProduto().multiply(gestaoFinanceira.getPercentualAplicadoTatuadorProduto()).setScale(2, BigDecimal.ROUND_HALF_UP));
								
			int formasPagamento=0;
			if(!ordemServico.getTotalDinheiro().equals(BigDecimal.ZERO)) {
				formasPagamento++;
			}
			if(!ordemServico.getTotalDebito().equals(BigDecimal.ZERO)) {	
				formasPagamento++;
			}
			if(!ordemServico.getTotalCredito().equals(BigDecimal.ZERO)) {
				formasPagamento++;
			}
			
			log.info("\nQuantidade de formas de pagamento: " + formasPagamento);
						
			if(produtoId != null && servicoId != null) {
				
				if(!ordemServico.getTotalDinheiro().equals(BigDecimal.ZERO)) {
						
					try {
						gestaoFinanceira.setTotalComissaoStudioDinheiro(ordemServico.getTotalDinheiro()
								.subtract(ordemServico.getValorProduto().divide(BigDecimal.valueOf(formasPagamento), RoundingMode.HALF_UP))
								.multiply(gestaoFinanceira.getPercentualAplicadoStudioDinheiro()).setScale(2, BigDecimal.ROUND_HALF_UP));
						
						gestaoFinanceira.setTotalComissaoTatuadorDinheiro(ordemServico.getTotalDinheiro()
								.subtract(ordemServico.getValorProduto().divide(BigDecimal.valueOf(formasPagamento), RoundingMode.HALF_UP))
								.multiply(gestaoFinanceira.getPercentualAplicadoTatuadorDinheiro()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
						
						gestaoFinanceira.setTotalComissaoStudioDinheiro(gestaoFinanceira.getTotalComissaoStudioDinheiro()
								.add(gestaoFinanceira.getTotalComissaoStudioProduto()
										.divide(BigDecimal.valueOf(formasPagamento), BigDecimal.ROUND_HALF_UP)));
						
						gestaoFinanceira.setTotalComissaoTatuadorDinheiro(gestaoFinanceira.getTotalComissaoTatuadorDinheiro()
								.add(gestaoFinanceira.getTotalComissaoTatuadorProduto()
										.divide(BigDecimal.valueOf(formasPagamento), BigDecimal.ROUND_HALF_DOWN)));
					} catch (Exception e) {					
						log.error("DINHEIRO: " + e.getMessage());
					}
				} 
				
				if(!ordemServico.getTotalDebito().equals(BigDecimal.ZERO)) {
					
					try {
						gestaoFinanceira.setTotalComissaoStudioDebito(ordemServico.getTotalDebito()
								.subtract(ordemServico.getValorProduto().divide(BigDecimal.valueOf(formasPagamento), RoundingMode.HALF_UP))
								.multiply(gestaoFinanceira.getPercentualAplicadoStudioDebito()).setScale(2, BigDecimal.ROUND_HALF_UP));
						
						gestaoFinanceira.setTotalComissaoTatuadorDebito(ordemServico.getTotalDebito()
								.subtract(ordemServico.getValorProduto().divide(BigDecimal.valueOf(formasPagamento), RoundingMode.HALF_UP))
								.multiply(gestaoFinanceira.getPercentualAplicadoTatuadorDebito()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
						
						gestaoFinanceira.setTotalComissaoStudioDebito(gestaoFinanceira.getTotalComissaoStudioDebito()
								.add(gestaoFinanceira.getTotalComissaoStudioProduto()
										.divide(BigDecimal.valueOf(formasPagamento), BigDecimal.ROUND_HALF_UP)));
						
						gestaoFinanceira.setTotalComissaoTatuadorDebito(gestaoFinanceira.getTotalComissaoTatuadorDebito()
								.add(gestaoFinanceira.getTotalComissaoTatuadorProduto()
										.divide(BigDecimal.valueOf(formasPagamento), BigDecimal.ROUND_HALF_DOWN)));
					} catch (Exception e) {
						log.error("DEBITO: " + e.getMessage());
					}
				} 
				
				if(!ordemServico.getTotalCredito().equals(BigDecimal.ZERO)) {
					
					try {
						gestaoFinanceira.setTotalComissaoStudioCredito(ordemServico.getTotalCredito()
								.subtract(ordemServico.getValorProduto().divide(BigDecimal.valueOf(formasPagamento), RoundingMode.HALF_UP))
								.multiply(gestaoFinanceira.getPercentualAplicadoStudioCredito()).setScale(2, BigDecimal.ROUND_HALF_UP));
						
						gestaoFinanceira.setTotalComissaoTatuadorCredito(ordemServico.getTotalCredito()
								.subtract(ordemServico.getValorProduto().divide(BigDecimal.valueOf(formasPagamento), RoundingMode.HALF_UP))
								.multiply(gestaoFinanceira.getPercentualAplicadoTatuadorCredito()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
						
						gestaoFinanceira.setTotalComissaoStudioCredito(gestaoFinanceira.getTotalComissaoStudioCredito()
								.add(gestaoFinanceira.getTotalComissaoStudioProduto()
										.divide(BigDecimal.valueOf(formasPagamento), BigDecimal.ROUND_HALF_UP)));
						
						gestaoFinanceira.setTotalComissaoTatuadorCredito(gestaoFinanceira.getTotalComissaoTatuadorCredito()
								.add(gestaoFinanceira.getTotalComissaoTatuadorProduto()
										.divide(BigDecimal.valueOf(formasPagamento), BigDecimal.ROUND_HALF_DOWN)));
					} catch (Exception e) {
						log.error("CREDITO: " + e.getMessage());
					}
				}
			} else if (produtoId != null) {
								
				if(!ordemServico.getTotalDinheiro().equals(BigDecimal.ZERO)) {
					
					gestaoFinanceira.setTotalComissaoStudioDinheiro(ordemServico.getTotalDinheiro().multiply(gestaoFinanceira.getPercentualAplicadoStudioProduto()).setScale(2, BigDecimal.ROUND_HALF_UP));
					gestaoFinanceira.setTotalComissaoTatuadorDinheiro(ordemServico.getTotalDinheiro().multiply(gestaoFinanceira.getPercentualAplicadoTatuadorProduto()).setScale(2, BigDecimal.ROUND_HALF_UP));				
				} 
				
				if(!ordemServico.getTotalDebito().equals(BigDecimal.ZERO)) {
					
					gestaoFinanceira.setTotalComissaoStudioDebito(ordemServico.getTotalDebito().multiply(gestaoFinanceira.getPercentualAplicadoStudioProduto()).setScale(2, BigDecimal.ROUND_HALF_UP));
					gestaoFinanceira.setTotalComissaoTatuadorDebito(ordemServico.getTotalDebito().multiply(gestaoFinanceira.getPercentualAplicadoTatuadorProduto()).setScale(2, BigDecimal.ROUND_HALF_UP));					
				} 
				
				if(!ordemServico.getTotalCredito().equals(BigDecimal.ZERO)) {
					
					gestaoFinanceira.setTotalComissaoStudioCredito(ordemServico.getTotalCredito().multiply(gestaoFinanceira.getPercentualAplicadoStudioProduto()).setScale(2, BigDecimal.ROUND_HALF_UP));
					gestaoFinanceira.setTotalComissaoTatuadorCredito(ordemServico.getTotalCredito().multiply(gestaoFinanceira.getPercentualAplicadoTatuadorProduto()).setScale(2, BigDecimal.ROUND_HALF_UP));									
				}
				
			} else if (servicoId != null) {
								
				if (usuarioBean.getUsuario().getValorComissaoAcimaDe() != null && gestaoFinanceira.getValorTotal().compareTo(usuarioBean.getUsuario().getValorComissaoAcimaDe()) == 1) {
									
					if(!ordemServico.getTotalDinheiro().equals(BigDecimal.ZERO)) {
						
						gestaoFinanceira.setPercentualAplicadoStudioDinheiro(new BigDecimal(1).subtract(usuarioBean.getUsuario().getComissaoDinheiroAcimaDe()));
						gestaoFinanceira.setPercentualAplicadoTatuadorDinheiro(usuarioBean.getUsuario().getComissaoDinheiroAcimaDe());
						gestaoFinanceira.setTotalComissaoStudioDinheiro(ordemServico.getTotalDinheiro().multiply(gestaoFinanceira.getPercentualAplicadoStudioDinheiro()).setScale(2, BigDecimal.ROUND_HALF_UP));
						gestaoFinanceira.setTotalComissaoTatuadorDinheiro(ordemServico.getTotalDinheiro().multiply(gestaoFinanceira.getPercentualAplicadoTatuadorDinheiro()).setScale(2, BigDecimal.ROUND_HALF_UP));
					} 
					
					if(!ordemServico.getTotalDebito().equals(BigDecimal.ZERO)) {
						
						gestaoFinanceira.setPercentualAplicadoStudioDebito(new BigDecimal(1).subtract(usuarioBean.getUsuario().getComissaoDebitoAcimaDe()));
						gestaoFinanceira.setPercentualAplicadoTatuadorDebito(usuarioBean.getUsuario().getComissaoDebitoAcimaDe());
						gestaoFinanceira.setTotalComissaoStudioDebito(ordemServico.getTotalDebito().multiply(gestaoFinanceira.getPercentualAplicadoStudioDebito()).setScale(2, BigDecimal.ROUND_HALF_UP));
						gestaoFinanceira.setTotalComissaoTatuadorDebito(ordemServico.getTotalDebito().multiply(gestaoFinanceira.getPercentualAplicadoTatuadorDebito()).setScale(2, BigDecimal.ROUND_HALF_UP));
					} 
					
					if(!ordemServico.getTotalCredito().equals(BigDecimal.ZERO)) {
						
						gestaoFinanceira.setPercentualAplicadoStudioCredito(new BigDecimal(1).subtract(usuarioBean.getUsuario().getComissaoCreditoAcimaDe()));
						gestaoFinanceira.setPercentualAplicadoTatuadorCredito(usuarioBean.getUsuario().getComissaoCreditoAcimaDe());
						gestaoFinanceira.setTotalComissaoStudioCredito(ordemServico.getTotalCredito().multiply(gestaoFinanceira.getPercentualAplicadoStudioCredito()).setScale(2, BigDecimal.ROUND_HALF_UP));
						gestaoFinanceira.setTotalComissaoTatuadorCredito(ordemServico.getTotalCredito().multiply(gestaoFinanceira.getPercentualAplicadoTatuadorCredito()).setScale(2, BigDecimal.ROUND_HALF_UP));
					}

					
				} else {
					
					if(!ordemServico.getTotalDinheiro().equals(BigDecimal.ZERO)) {
						
						gestaoFinanceira.setTotalComissaoStudioDinheiro(ordemServico.getTotalDinheiro().multiply(gestaoFinanceira.getPercentualAplicadoStudioDinheiro()).setScale(2, BigDecimal.ROUND_HALF_UP));
						gestaoFinanceira.setTotalComissaoTatuadorDinheiro(ordemServico.getTotalDinheiro().multiply(gestaoFinanceira.getPercentualAplicadoTatuadorDinheiro()).setScale(2, BigDecimal.ROUND_HALF_UP));
					} 
					
					if(!ordemServico.getTotalDebito().equals(BigDecimal.ZERO)) {
						
						gestaoFinanceira.setTotalComissaoStudioDebito(ordemServico.getTotalDebito().multiply(gestaoFinanceira.getPercentualAplicadoStudioDebito()).setScale(2, BigDecimal.ROUND_HALF_UP));
						gestaoFinanceira.setTotalComissaoTatuadorDebito(ordemServico.getTotalDebito().multiply(gestaoFinanceira.getPercentualAplicadoTatuadorDebito()).setScale(2, BigDecimal.ROUND_HALF_UP));
					} 
					
					if(!ordemServico.getTotalCredito().equals(BigDecimal.ZERO)) {
						
						gestaoFinanceira.setTotalComissaoStudioCredito(ordemServico.getTotalCredito().multiply(gestaoFinanceira.getPercentualAplicadoStudioCredito()).setScale(2, BigDecimal.ROUND_HALF_UP));
						gestaoFinanceira.setTotalComissaoTatuadorCredito(ordemServico.getTotalCredito().multiply(gestaoFinanceira.getPercentualAplicadoTatuadorCredito()).setScale(2, BigDecimal.ROUND_HALF_UP));
					}		
				}				
			}
			
			gestaoFinanceira.setTotalComissaoStudioDinheiro(gestaoFinanceira.getTotalComissaoStudioDinheiro() != null ? gestaoFinanceira.getTotalComissaoStudioDinheiro() : BigDecimal.ZERO);
			gestaoFinanceira.setTotalComissaoStudioDebito(gestaoFinanceira.getTotalComissaoStudioDebito() != null ? gestaoFinanceira.getTotalComissaoStudioDebito() : BigDecimal.ZERO);
			gestaoFinanceira.setTotalComissaoStudioCredito(gestaoFinanceira.getTotalComissaoStudioCredito() != null ? gestaoFinanceira.getTotalComissaoStudioCredito() : BigDecimal.ZERO);
			
			gestaoFinanceira.setTotalComissaoTatuadorDinheiro(gestaoFinanceira.getTotalComissaoTatuadorDinheiro() != null ? gestaoFinanceira.getTotalComissaoTatuadorDinheiro() : BigDecimal.ZERO);
			gestaoFinanceira.setTotalComissaoTatuadorDebito(gestaoFinanceira.getTotalComissaoTatuadorDebito() != null ? gestaoFinanceira.getTotalComissaoTatuadorDebito() : BigDecimal.ZERO);
			gestaoFinanceira.setTotalComissaoTatuadorCredito(gestaoFinanceira.getTotalComissaoTatuadorCredito() != null ? gestaoFinanceira.getTotalComissaoTatuadorCredito() : BigDecimal.ZERO);
			
			gestaoFinanceira.setTotalComissaoStudio(gestaoFinanceira.getTotalComissaoStudioDinheiro().add(gestaoFinanceira.getTotalComissaoStudioDebito()).add(gestaoFinanceira.getTotalComissaoStudioCredito()));
			gestaoFinanceira.setTotalComissaoTatuador(gestaoFinanceira.getTotalComissaoTatuadorDinheiro().add(gestaoFinanceira.getTotalComissaoTatuadorDebito()).add(gestaoFinanceira.getTotalComissaoTatuadorCredito()));
								
			log.info("\n\n[NOVA ORDEM DE SERVICO] - ID: " + ordemServico.getOrdemServicoId() +
			"\nUsuario: " + usuarioBean.getUsuario().getNome() + 
			"\n" +
			"\n[DADOS FINANCEIROS]: " +
			"\n" +
			"\nValor DINHEIRO: " + ordemServico.getTotalDinheiro() +
			"\nValor DEBITO: " + ordemServico.getTotalDebito() +
			"\nValor CREDITO: " + ordemServico.getTotalCredito() +
			"\nParcelas CREDITO: " + totalParcelasCredito +
			"\n" +
			"\nValor SERVICO: " + ordemServico.getValorServico() +
			"\nValor PRODUTO: " + ordemServico.getValorProduto() +
			"\nValor TOTAL: " + ordemServico.getValorTotal() +
			"\nValor SAQUE: " +  saqueTatuador +
			"\n" +
			"\n[STUDIO]" +
			"\n% Studio (produto): " + gestaoFinanceira.getPercentualAplicadoStudioProduto() +
			"\n% Studio (dinheiro): " + gestaoFinanceira.getPercentualAplicadoStudioDinheiro() +
			"\n% Studio (debito): " + gestaoFinanceira.getPercentualAplicadoStudioDebito() +
			"\n% Studio (credito): " + gestaoFinanceira.getPercentualAplicadoStudioCredito() +
			"\n" +
			"\nValor Studio (produto): " + gestaoFinanceira.getTotalComissaoStudioProduto() +
			"\nValor Studio (dinheiro): " + gestaoFinanceira.getTotalComissaoStudioDinheiro() +
			"\nValor Studio (debito): " + gestaoFinanceira.getTotalComissaoStudioDebito() +
			"\nValor Studio (credito): " + gestaoFinanceira.getTotalComissaoStudioCredito() +
			"\nTotal Studio: " + gestaoFinanceira.getTotalComissaoStudio() +
			"\n" +
			"\n[TATUADOR]" +
			"\n% Tatuador (produto): " + gestaoFinanceira.getPercentualAplicadoTatuadorProduto() +
			"\n% Tatuador (dinheiro): " + gestaoFinanceira.getPercentualAplicadoTatuadorDinheiro() +
			"\n% Tatuador (debito): " + gestaoFinanceira.getPercentualAplicadoTatuadorDebito() +
			"\n% Tatuador (credito): " + gestaoFinanceira.getPercentualAplicadoTatuadorCredito() +
			"\n" +
			"\nValor Tatuador (produto): " + gestaoFinanceira.getTotalComissaoTatuadorProduto() +
			"\nValor Tatuador (dinheiro): " + gestaoFinanceira.getTotalComissaoTatuadorDinheiro() +
			"\nValor Tatuador (debito): " + gestaoFinanceira.getTotalComissaoTatuadorDebito() +
			"\nValor Tatuador (credito): " + gestaoFinanceira.getTotalComissaoTatuadorCredito() +
			"\nTotal Tatuador: " + gestaoFinanceira.getTotalComissaoTatuador() +
			"\n");
								
			gestaoFinanceiraRepository.save(gestaoFinanceira);
			
			int quantidadeParcelas = 0;
			
			if (ordemServico.getTotalDinheiro() != null && !ordemServico.getTotalDinheiro().equals(BigDecimal.ZERO)) {
				
				quantidadeParcelas++;
												
				gestaoFinanceira.setTotalReceberStudioDinheiro(gestaoFinanceira.getTotalComissaoStudioDinheiro());
				gestaoFinanceira.setTotalReceberTatuadorDinheiro(gestaoFinanceira.getTotalComissaoTatuadorDinheiro());
								
				if(saqueTatuador != null && saqueTatuador.compareTo(BigDecimal.ZERO) > 0) {
					
					log.info("\nUsuario: " + usuarioBean.getUsuario().getNome() + 
							"\nSaque Efetuado [Tatuador]: " + saqueTatuador + 
							"\nDinheiro Recebido [Tatuador]: " + gestaoFinanceira.getTotalComissaoTatuadorDinheiro());
					
					gestaoFinanceiraSaques = new GestaoFinanceiraSaques();
					gestaoFinanceiraSaques.setGestaoFinanceira(gestaoFinanceira);
					gestaoFinanceiraSaques.setOrdemServico(ordemServico);
					gestaoFinanceiraSaques.setUsuario(usuarioBean.getUsuario());
					gestaoFinanceiraSaques.setValorSaque(saqueTatuador);
					gestaoFinanceiraSaques.setPendente(false);
					gestaoFinanceira.setValorSaque(saqueTatuador);
					
					BigDecimal diferenca = BigDecimal.ZERO;
					
					if(gestaoFinanceira.getValorSaque().compareTo(gestaoFinanceira.getTotalComissaoTatuadorDinheiro()) == 1) {
						diferenca = gestaoFinanceira.getTotalComissaoTatuadorDinheiro().subtract(gestaoFinanceira.getValorSaque()).abs();
					}
					
					log.info("\nDiferenca de valor entre dinheiro recebido x saque: " + diferenca);
					if(diferenca.compareTo(BigDecimal.ZERO) > 0) {
						
						gestaoFinanceiraSaques.setValorSaque(diferenca);
						gestaoFinanceiraSaques.setPendente(true);
					} 
					gestaoFinanceiraSaquesRepository.save(gestaoFinanceiraSaques);
				} 
			} 
						
			if (ordemServico.getTotalDebito() != null && !ordemServico.getTotalDebito().equals(BigDecimal.ZERO)) {
				
				quantidadeParcelas++;
																
				gestaoFinanceira.setTotalReceberStudioDebito(gestaoFinanceira.getTotalComissaoStudioDebito());
				gestaoFinanceira.setTotalReceberTatuadorDebito(gestaoFinanceira.getTotalComissaoTatuadorDebito());
				
			} 
						
			if (ordemServico.getTotalCredito() != null && !ordemServico.getTotalCredito().equals(BigDecimal.ZERO)) {
				
				quantidadeParcelas++;
								
				gestaoFinanceira.setTotalReceberTatuadorCredito(gestaoFinanceira.getTotalComissaoTatuadorCredito());
				gestaoFinanceira.setTotalReceberStudioCredito(gestaoFinanceira.getTotalComissaoStudioCredito());
								
				GestaoFinanceiraParcelasCredito gestaoFinanceiraParcelasCredito;
				
				BigDecimal valorParcelaCredito;
				BigDecimal valorParcelaCreditoStudio;
				BigDecimal valorParcelaCreditoTatuador;
									     				
				try {
					
					valorParcelaCredito = gestaoFinanceira.getTotalReceberStudioCredito().add(gestaoFinanceira.getTotalReceberTatuadorCredito()).divide(BigDecimal.valueOf(totalParcelasCredito));
					valorParcelaCreditoStudio = gestaoFinanceira.getTotalReceberStudioCredito().divide(BigDecimal.valueOf(totalParcelasCredito));
					valorParcelaCreditoTatuador = gestaoFinanceira.getTotalReceberTatuadorCredito().divide(BigDecimal.valueOf(totalParcelasCredito));
					
				} catch (Exception e) {
					
					log.info("arredondamento de parcela devido a divisao do valor x numero de parcelas.");
					valorParcelaCredito = gestaoFinanceira.getTotalReceberStudioCredito().add(gestaoFinanceira.getTotalReceberTatuadorCredito()).divide(BigDecimal.valueOf(totalParcelasCredito), RoundingMode.HALF_UP);
					valorParcelaCreditoStudio = gestaoFinanceira.getTotalReceberStudioCredito().divide(BigDecimal.valueOf(totalParcelasCredito), RoundingMode.HALF_UP);
					valorParcelaCreditoTatuador = gestaoFinanceira.getTotalReceberTatuadorCredito().divide(BigDecimal.valueOf(totalParcelasCredito), RoundingMode.HALF_UP);
					
				}
								
				LocalDate dataAtual = LocalDate.parse(String.valueOf(dateCadastroOS));
				int meses = 0;
				
				for(int i = 0; i < totalParcelasCredito; i++) {			
					meses++;
					gestaoFinanceiraParcelasCredito = new GestaoFinanceiraParcelasCredito();
					gestaoFinanceiraParcelasCredito.setGestaoFinanceira(gestaoFinanceira);
					gestaoFinanceiraParcelasCredito.setDataVencimento(dataAtual.plusMonths(meses));
					gestaoFinanceiraParcelasCredito.setRecebido(false);
					gestaoFinanceiraParcelasCredito.setValorParcela(valorParcelaCredito);
					gestaoFinanceiraParcelasCredito.setValorParcelaStudio(valorParcelaCreditoStudio);
					gestaoFinanceiraParcelasCredito.setValorParcelaTatuador(valorParcelaCreditoTatuador);
					gestaoFinanceiraParcelasCreditoRepository.save(gestaoFinanceiraParcelasCredito);
				}
			} 
						
			gestaoFinanceira.setTotalRecebidoStudio(gestaoFinanceira.getTotalRecebidoStudioDinheiro().add(gestaoFinanceira.getTotalRecebidoStudioDebito().add(gestaoFinanceira.getTotalRecebidoStudioCredito())));
			gestaoFinanceira.setTotalRecebidoTatuador(gestaoFinanceira.getTotalRecebidoTatuadorDinheiro().add(gestaoFinanceira.getTotalRecebidoTatuadorDebito().add(gestaoFinanceira.getTotalRecebidoTatuadorCredito())));
			gestaoFinanceira.setTotalReceberStudio(gestaoFinanceira.getTotalReceberStudioDinheiro().add(gestaoFinanceira.getTotalReceberStudioDebito().add(gestaoFinanceira.getTotalReceberStudioCredito())));
			gestaoFinanceira.setTotalReceberTatuador(gestaoFinanceira.getTotalReceberTatuadorDinheiro().add(gestaoFinanceira.getTotalReceberTatuadorDebito().add(gestaoFinanceira.getTotalReceberTatuadorCredito())));
			
			gestaoFinanceiraRepository.save(gestaoFinanceira);
			
			log.info("\n-----------------------------------------------------------------------------" +
			"\nOS CADASTRADOS COM SUCESSO" +
		    "\nOS ID: " + ordemServico.getOrdemServicoId() + " cadastrada com sucesso!" +
			"\nFinanceiro ID: " + gestaoFinanceira.getGestaoFinanceiraId() +
			"\nUsuario: " + usuarioBean.getUsuario().getNome() + 
			"\nCliente ID: " + ordemServico.getCliente().getClienteId() + 
			"\nQtde forma(s) de pagamento: " + quantidadeParcelas +
			"\n-----------------------------------------------------------------------------");
						
		} catch (Exception e) {
			
			gestaoFinanceiraRepository.delete(gestaoFinanceira);
			ordemServicoRepository.delete(ordemServico);
			gestaoFinanceiraSaquesRepository.delete(gestaoFinanceiraSaques);
			log.info("Financeiro ID: " + gestaoFinanceira.getGestaoFinanceiraId() + " deletado!");
			log.info("Ordem de Servico ID: " + ordemServico.getOrdemServicoId() + " deletado!");
			log.info("Saque ID: " + gestaoFinanceiraSaques.getGestaoFinanceiraSaqueId() + " deletado!");
			log.error("Ocorreu um erro. Causa: " + e.getMessage() + "\nDetails: ");
			e.printStackTrace();
		}	
		return mv;
	}

	public void imprimirOS(long ordemServicoId, HttpServletResponse response) {
		
		try {
			
			OrdemServico ordemServico = ordemServicoRepository.findByOrdemServicoId(ordemServicoId);
			
			if (ordemServico != null) {

				String autorizaFotos, possuiHepatite, possuiAlergias, possuiDiabetes, tomaAnticoagulante;
				if(ordemServico.getCliente().isAutorizaFotos()) {autorizaFotos="Sim";}else {autorizaFotos="Não";};
				if(ordemServico.getCliente().isPossuiHepatite() ) {possuiHepatite="Sim";}else {possuiHepatite="Não";};
				if(ordemServico.getCliente().isPossuiAlergias() ) {possuiAlergias="Sim";}else {possuiAlergias="Não";};
				if(ordemServico.getCliente().isPossuiDiabetes() ) {possuiDiabetes="Sim";}else {possuiDiabetes="Não";};
				if(ordemServico.getCliente().isTomaAnticoagulante() ) {tomaAnticoagulante="Sim";}else {tomaAnticoagulante="Não";};
				
				String descricao = "Não recomendamos e não garantimos o retoque em tatuagens no dedo." + 
						"\nGarantimos que todo o material usado é de procedência segura, esterelizado e estão de acordo com as normas sanitárias." + 
						"\nAfirmo serem verdadeiras todas as informações e estar em perfeita saúde mental e psicológica." + 
						"\nAssino e autorizo " + ordemServico.getUsuario().getNome() + " a execução do procedimento, onde todos os detalhes foram devidamente determinados por mim." + 
						"\nAutorizo a utilização das fotos do procedimento em mídias sociais e propagandas do Studio D.r Tattoo." + 
						"\nAssumo a responsabilidade de cuidar da tatuagem ou do piercing conforme instruções concebidas pelo Studio.";
				
				HashMap<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("dataCadastro", ordemServico.getDataCadastro());
				parametros.put("ordemServicoId", ordemServico.getOrdemServicoId());
				parametros.put("usuario", ordemServico.getUsuario().getNome());
				parametros.put("cliente", ordemServico.getCliente().getNome());
				parametros.put("celular", ordemServico.getCliente().getCelular());
				parametros.put("email", ordemServico.getCliente().getEmail());
				parametros.put("descricao", descricao);
				parametros.put("tipoSangue", ordemServico.getCliente().getTipoSangue());
				parametros.put("autorizaFotos", autorizaFotos);
				parametros.put("possuiHepatite", possuiHepatite);
				parametros.put("possuiAlergias", possuiAlergias);
				parametros.put("possuiDiabetes", possuiDiabetes);
				parametros.put("tomaAnticoagulante", tomaAnticoagulante);
				
				if(ordemServico.getProduto() != null) {
					parametros.put("produto", ordemServico.getProduto().getDescricao());
				}else {
					parametros.put("produto", "N/A");
				}
				
				if(ordemServico.getServico() != null) {
					parametros.put("servico", ordemServico.getServico().getDescricao());
				}else {
					parametros.put("servico", "N/A");
				}
				
				parametros.put("totalDinheiro", ordemServico.getTotalDinheiro());
				parametros.put("totalDebito", ordemServico.getTotalDebito());
				parametros.put("totalCredito", ordemServico.getTotalCredito());
				parametros.put("valorProduto", ordemServico.getValorProduto());
				parametros.put("valorServico", ordemServico.getValorServico());
				parametros.put("valorTotal", ordemServico.getValorTotal());
						
				InputStream jasperStream = this.getClass().getResourceAsStream("/relatorios/reportOS.jasper");
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource(1));
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "inline; filename=reportOS.pdf");
				final OutputStream outStream = response.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
				
				log.info("O relatorio foi gerado com sucesso!");
			}
		} catch (Exception e) {
			log.error("Ocorreu um erro. Causa: " + e.getMessage());
		}
	}	
}