package br.com.zaratech.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.zaratech.bean.Parametros;
import br.com.zaratech.bean.UsuarioBean;
import br.com.zaratech.model.GestaoFinanceira;
import br.com.zaratech.model.GestaoFinanceiraParcelasCredito;
import br.com.zaratech.model.PagerModel;
import br.com.zaratech.model.ParametrosSistema;
import br.com.zaratech.repository.GestaoFinanceiraParcelasCreditoRepository;
import br.com.zaratech.repository.GestaoFinanceiraRepository;
import br.com.zaratech.repository.GestaoFinanceiraSaquesRepository;
import br.com.zaratech.repository.ParametrosRepository;

@Service
public class GestaoFinanceiraService {
	
	static Logger log = Logger.getLogger(GestaoFinanceiraService.class);
	
	@Autowired
    private UsuarioBean usuarioBean;
	@Autowired
	private ParametrosRepository parametrosRepository;
	@Autowired
	private GestaoFinanceiraRepository gestaoFinanceiraRepository;
	@Autowired
	private GestaoFinanceiraParcelasCreditoRepository gestaoFinanceiraParcelasCreditoRepository;
	@Autowired
	private GestaoFinanceiraSaquesRepository gestaoFinanceiraSaquesRepository;
	
	private static final int BUTTONS_TO_SHOW = 3;
	private static final int INITIAL_PAGE = 0;
	
	public ModelAndView gestaoFinanceira(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		
		ModelAndView mv = new ModelAndView("gestaoFinanceira/gestaoFinanceira");

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

			Page<GestaoFinanceira> lsGestaoFinanceira = gestaoFinanceiraRepository.buscarTodasValidas(PageRequest.of(evalPage, evalPageSize));
			PagerModel pager = new PagerModel(lsGestaoFinanceira.getTotalPages(), lsGestaoFinanceira.getNumber(), BUTTONS_TO_SHOW);
			mv.addObject("selectedPageSize", evalPageSize);
			mv.addObject("pageSizes", evalPageSize);
			mv.addObject("pager", pager);
			mv.addObject("usuarioLogado", usuarioBean.getUsuario());
			mv.addObject("lsGestaoFinanceira", lsGestaoFinanceira);
			mv.addObject("quantidadeGestaoFinanceira", gestaoFinanceiraRepository.totalGestaoFinanceiraValidos());
			try {
				Long valorTotalGestaoFinanceira = gestaoFinanceiraRepository.valorTotalOS();
				mv.addObject("valorTotalGestaoFinanceira", valorTotalGestaoFinanceira);
			} catch (Exception e) {
				mv.addObject("valorTotalGestaoFinanceira", 0);
			}
			return mv;
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ModelAndView("redirect:/index");
		}	
	}
	
 	public ModelAndView detalhesGestaoFinanceiraOS(long ordemServicoId) {
		
		ModelAndView mv = new ModelAndView("gestaoFinanceira/detalhesGestaoFinanceira");
		try {
			
			GestaoFinanceira gestaoFinanceira = gestaoFinanceiraRepository.buscaPorOrdemServicoId(ordemServicoId);
			List<GestaoFinanceiraParcelasCredito> gestaoFinanceiraParcelasCredito = gestaoFinanceiraParcelasCreditoRepository.buscaPorGestaoFinanceiraId(gestaoFinanceira);
			
			mv.addObject("pageSizeNavi",usuarioBean.getPageSize());
			mv.addObject("pageNavi",usuarioBean.getPage());
			mv.addObject("valorSaque", gestaoFinanceiraSaquesRepository.valorSaqueGestaoFinanceira(gestaoFinanceira.getGestaoFinanceiraId()));
			mv.addObject("valorSaqueExcedido", gestaoFinanceiraSaquesRepository.valorSaqueGestaoFinanceiraExcedido(gestaoFinanceira.getGestaoFinanceiraId()));
			mv.addObject("usuarioLogado", usuarioBean.getUsuario());			
			mv.addObject("gestaoFinanceira", gestaoFinanceira);
			mv.addObject("gestaoFinanceiraParcelasCredito", gestaoFinanceiraParcelasCredito);
			return mv;
		} catch(Exception e) {
			log.error("Ocorreu um erro. Causa: " + e.getMessage());
			return new ModelAndView("redirect:/index");
		}
	}
 	
	public ModelAndView efetuarRecebimentoGestaoFinanceira(long gestaoFinanceiraId, boolean recebeDinheiro, boolean recebeDebito, boolean recebeCredito) {
		
		ModelAndView mv = new ModelAndView("redirect:/index");
		try {
			
			GestaoFinanceira gestaoFinanceira = gestaoFinanceiraRepository.buscaPorGestaoFinanceiraId(gestaoFinanceiraId);
			List<GestaoFinanceiraParcelasCredito> lsGestaoFinanceiraParcelasCredito = gestaoFinanceiraParcelasCreditoRepository.buscaPorGestaoFinanceiraId(gestaoFinanceira);
			GestaoFinanceiraParcelasCredito gestaoFinanceiraParcelasCredito;
			
			LocalDate dataAtual = LocalDate.now();
			
			for (int i = 0; lsGestaoFinanceiraParcelasCredito.size() > i; i++) {

				log.info("\nParcela de credito detectada!"
				+ "\nParcela ID: " + lsGestaoFinanceiraParcelasCredito.get(i).getGestaoFinanceiraParcelaCreditoId()
				+ "\nValor: " + lsGestaoFinanceiraParcelasCredito.get(i).getValorParcela()
				+ "\nData Atual: " + dataAtual 
				+ "\nVencimento da Parcela: " + lsGestaoFinanceiraParcelasCredito.get(i).getDataVencimento() 
				+ "\n");

				if (dataAtual.equals(lsGestaoFinanceiraParcelasCredito.get(i).getDataVencimento())) {
					
					log.info("\nA data atual e igual a data do vencimento. O registro pode ser recebido!");
					gestaoFinanceiraParcelasCredito = lsGestaoFinanceiraParcelasCredito.get(i);
					gestaoFinanceiraParcelasCredito.setRecebido(true);
					gestaoFinanceiraParcelasCreditoRepository.save(gestaoFinanceiraParcelasCredito);
					log.info("Parcela recebida!");
					
				} else if (dataAtual.isBefore(lsGestaoFinanceiraParcelasCredito.get(i).getDataVencimento())) {
					
					log.warn("\nA data atual e inferior a data do vencimento. O registro nao pode ser recebido!");
					
				} else if (dataAtual.isAfter(lsGestaoFinanceiraParcelasCredito.get(i).getDataVencimento())) {
					
					log.info("\nA data atual e superior a data do vencimento. O registro pode ser recebido!");
					gestaoFinanceiraParcelasCredito = lsGestaoFinanceiraParcelasCredito.get(i);
					gestaoFinanceiraParcelasCredito.setRecebido(true);
					gestaoFinanceiraParcelasCreditoRepository.save(gestaoFinanceiraParcelasCredito);
					log.info("Parcela recebida!");
				}
			}
									
			if(gestaoFinanceira.getTotalReceberStudio().compareTo(BigDecimal.ZERO) == 1) {
				
				if(recebeDinheiro) {
					
					gestaoFinanceira.setTotalRecebidoStudioDinheiro(gestaoFinanceira.getTotalReceberStudioDinheiro());
					gestaoFinanceira.setTotalReceberStudioDinheiro(BigDecimal.ZERO);				
					gestaoFinanceira.setTotalRecebidoStudio(gestaoFinanceira.getTotalRecebidoStudio().add(gestaoFinanceira.getTotalRecebidoStudioDinheiro()));
					gestaoFinanceira.setTotalReceberStudio(gestaoFinanceira.getTotalReceberStudio().subtract(gestaoFinanceira.getTotalRecebidoStudioDinheiro()));					
					gestaoFinanceira.setStudioDinheiroRecebido(true);
				}
				if(recebeDebito) {
					
					gestaoFinanceira.setTotalRecebidoStudioDebito(gestaoFinanceira.getTotalReceberStudioDebito());
					gestaoFinanceira.setTotalReceberStudioDebito(BigDecimal.ZERO);					
					gestaoFinanceira.setTotalRecebidoStudio(gestaoFinanceira.getTotalRecebidoStudio().add(gestaoFinanceira.getTotalRecebidoStudioDebito()));
					gestaoFinanceira.setTotalReceberStudio(gestaoFinanceira.getTotalReceberStudio().subtract(gestaoFinanceira.getTotalRecebidoStudioDebito()));					
					gestaoFinanceira.setStudioDebitoRecebido(true);					
				}
				if(recebeCredito) {
										
					gestaoFinanceira.setTotalRecebidoStudioCredito(gestaoFinanceira.getTotalReceberStudioCredito());
					gestaoFinanceira.setTotalReceberStudioCredito(BigDecimal.ZERO);				
					gestaoFinanceira.setTotalRecebidoStudio(gestaoFinanceira.getTotalRecebidoStudio().add(gestaoFinanceira.getTotalRecebidoStudioCredito()));
					gestaoFinanceira.setTotalReceberStudio(gestaoFinanceira.getTotalReceberStudio().subtract(gestaoFinanceira.getTotalRecebidoStudioCredito()));					
					gestaoFinanceira.setStudioCreditoRecebido(true);					
				}
			}
			
			if(gestaoFinanceira.getTotalReceberTatuador().compareTo(BigDecimal.ZERO) == 1) {
				
				if(recebeDinheiro) {
					
					gestaoFinanceira.setTotalRecebidoTatuadorDinheiro(gestaoFinanceira.getTotalReceberTatuadorDinheiro());
					gestaoFinanceira.setTotalReceberTatuadorDinheiro(BigDecimal.ZERO);
					gestaoFinanceira.setTotalRecebidoTatuador(gestaoFinanceira.getTotalRecebidoTatuador().add(gestaoFinanceira.getTotalRecebidoTatuadorDinheiro()));
					gestaoFinanceira.setTotalReceberTatuador(gestaoFinanceira.getTotalReceberTatuador().subtract(gestaoFinanceira.getTotalRecebidoTatuadorDinheiro()));
					gestaoFinanceira.setTatuadorDinheiroRecebido(true);
				}
				if(recebeDebito) {
					
					gestaoFinanceira.setTotalRecebidoTatuadorDebito(gestaoFinanceira.getTotalReceberTatuadorDebito());
					gestaoFinanceira.setTotalReceberTatuadorDebito(BigDecimal.ZERO);
					gestaoFinanceira.setTotalRecebidoTatuador(gestaoFinanceira.getTotalRecebidoTatuador().add(gestaoFinanceira.getTotalRecebidoTatuadorDebito()));
					gestaoFinanceira.setTotalReceberTatuador(gestaoFinanceira.getTotalReceberTatuador().subtract(gestaoFinanceira.getTotalRecebidoTatuadorDebito()));
					gestaoFinanceira.setTatuadorDebitoRecebido(true);
				}
				if(recebeCredito) {
					
					gestaoFinanceira.setTotalRecebidoTatuadorCredito(gestaoFinanceira.getTotalReceberTatuadorCredito());
					gestaoFinanceira.setTotalReceberTatuadorCredito(BigDecimal.ZERO);
					gestaoFinanceira.setTotalRecebidoTatuador(gestaoFinanceira.getTotalRecebidoTatuador().add(gestaoFinanceira.getTotalRecebidoTatuadorCredito()));
					gestaoFinanceira.setTotalReceberTatuador(gestaoFinanceira.getTotalReceberTatuador().subtract(gestaoFinanceira.getTotalRecebidoTatuadorCredito()));
					gestaoFinanceira.setTatuadorCreditoRecebido(true);
				}
			}
			
			log.info("\n== Recebimento de Valores =="
			+ "\n" 
			+ "\nValor Recebido Tatuador (dinheiro): " + gestaoFinanceira.getTotalRecebidoTatuadorDinheiro()
			+ "\nValor Recebido Tatuador (debito): " + gestaoFinanceira.getTotalRecebidoTatuadorDebito()
			+ "\nValor Recebido Tatuador (credito): " + gestaoFinanceira.getTotalRecebidoTatuadorCredito()
			+ "\n"
			+ "\nValor Recebido Tatuador: " + gestaoFinanceira.getTotalRecebidoTatuador()
			+ "\nValor a Receber Tatuador: " + gestaoFinanceira.getTotalReceberTatuador()
			+ "\n"
			+ "\nValor Recebido Studio (dinheiro): " + gestaoFinanceira.getTotalRecebidoStudioDinheiro()
			+ "\nValor Recebido Studio (debito): " + gestaoFinanceira.getTotalRecebidoStudioDebito()
			+ "\nValor Recebido Studio (credito): " + gestaoFinanceira.getTotalRecebidoStudioCredito()				
			+ "\n"
			+ "\nValor Recebido Studio: " + gestaoFinanceira.getTotalRecebidoStudio()
			+ "\nValor a Receber Studio: " + gestaoFinanceira.getTotalReceberStudio()
			+ "\n");
			
			gestaoFinanceiraRepository.save(gestaoFinanceira);
			log.info("Dados financeiros atualizados!");
			return mv;
		}catch(Exception e) {
			log.error("efetuarRecebimentoGestaoFinanceira: " + e.getMessage());
			return mv;
		}
	}
}
