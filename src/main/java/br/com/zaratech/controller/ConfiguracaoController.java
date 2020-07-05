package br.com.zaratech.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.zaratech.bean.UsuarioBean;
import br.com.zaratech.model.ParametrosEmail;
import br.com.zaratech.model.ParametrosSistema;
import br.com.zaratech.model.TipoUsuario;
import br.com.zaratech.repository.ParametrosEmailRepository;
import br.com.zaratech.repository.ParametrosRepository;
import br.com.zaratech.repository.TipoUsuarioRepository;
import br.com.zaratech.security.UserPrivilegies;

@Controller
public class ConfiguracaoController {

	static Logger log = Logger.getLogger(ConfiguracaoController.class);

	@Autowired
	private UsuarioBean usuarioBean;
	@Autowired
	private ParametrosRepository parametrosRepository;
	@Autowired
	private ParametrosEmailRepository parametrosEmailRepository;
	@Autowired
	private TipoUsuarioRepository tipoUsuarioRepository;

	@RequestMapping(value = "configuracao", method = RequestMethod.GET)
	public ModelAndView configuracoes() {

		ModelAndView mv = new ModelAndView("configuracao/configuracao");

		try {
			if (UserPrivilegies.eAdmin(usuarioBean.getUsuario())) {

				List<ParametrosSistema> lsParametrosSistema = parametrosRepository.findAll();
				List<ParametrosEmail> lsParametrosEmail = parametrosEmailRepository.findAll();
				List<TipoUsuario> lsTipoUsuario = tipoUsuarioRepository.findAll();
				mv.addObject("lsParametrosSistema", lsParametrosSistema);
				mv.addObject("lsParametrosEmail", lsParametrosEmail);
				mv.addObject("lsTipoUsuario", lsTipoUsuario);
				mv.addObject("usuarioLogado", usuarioBean.getUsuario());
				return mv;

			} else {

				mv = new ModelAndView("redirect:/index");
				return mv;

			}
		} catch (Exception e) {

			return new ModelAndView("redirect:/index");
		}
	}

	@RequestMapping(value = "alterarConfiguracao", method = RequestMethod.POST)
	public ModelAndView alterarConfiguracao(Long parametroId, String chave, String valor, String descricao) {

		ModelAndView mv = new ModelAndView("configuracao/configuracao");

		if (UserPrivilegies.eAdmin(usuarioBean.getUsuario())) {

			ParametrosSistema parametrosSistema = parametrosRepository.findByParametroId(parametroId);
			parametrosSistema.setChave(chave);
			parametrosSistema.setValor(valor);
			parametrosSistema.setDescricao(descricao);
			parametrosRepository.save(parametrosSistema);
			log.info("Dados alterados no banco: " + parametroId + ";" + chave + ";" + valor + ";" + descricao);

			mv.addObject("usuarioLogado", usuarioBean.getUsuario());
			return mv;
			
		} else {
			
			mv = new ModelAndView("redirect:/index");
			return mv;
		}
	}

	@RequestMapping(value = "inserirParametro", method = RequestMethod.POST)
	public ModelAndView inserirParametro(String chave, String valor, String descricao) {

		ModelAndView mv = new ModelAndView("configuracao/configuracao");

		if (UserPrivilegies.eAdmin(usuarioBean.getUsuario())) {

			ParametrosSistema parametroSistema = parametrosRepository.findByChave(chave);
			if (parametroSistema == null) {
				parametroSistema = new ParametrosSistema();
				parametroSistema.setChave(chave);
				parametroSistema.setValor(valor);
				parametroSistema.setDescricao(descricao);
				parametrosRepository.save(parametroSistema);
				log.info("Dados inseridos no banco: " + parametroSistema.getParametroId() + ";"
						+ parametroSistema.getChave() + ";" + parametroSistema.getValor() + ";"
						+ parametroSistema.getDescricao());
			}
			mv.addObject("usuarioLogado", usuarioBean.getUsuario());
			return mv;
			
		} else {
			
			mv = new ModelAndView("redirect:/index");
			return mv;
		}
	}

	@RequestMapping(value = "alterarParametrosEmail", method = RequestMethod.POST)
	public ModelAndView alterarParametrosEmail(Long parametroEmailId, String emailFrom, String emailFromName,
			String emailSmtpUsername, String emailSmtpPassword, String emailSmtpHost, int emailPort,
			String emailSubject, String configSet, String urlEnvio) {

		ModelAndView mv = new ModelAndView("configuracao/configuracao");

		if (UserPrivilegies.eAdmin(usuarioBean.getUsuario())) {

			ParametrosEmail parametroEmail = parametrosEmailRepository.findByParametroEmailId(parametroEmailId);
			parametroEmail.setEmailFrom(emailFrom);
			parametroEmail.setEmailFromName(emailFromName);
			parametroEmail.setEmailSmtpUsername(emailSmtpUsername);
			parametroEmail.setEmailSmtpPassword(emailSmtpPassword);
			parametroEmail.setEmailSmtpHost(emailSmtpHost);
			parametroEmail.setEmailPort(emailPort);
			parametroEmail.setEmailSubject(emailSubject);
			parametroEmail.setConfigSet(configSet);
			parametroEmail.setUrlEnvio(urlEnvio);
			parametrosEmailRepository.save(parametroEmail);
			log.info("Dados de e-mail alterados no banco: " + parametroEmailId + ";" + emailFrom + ";" + emailFromName
					+ ";" + emailSmtpUsername + ";" + emailSmtpPassword + emailSmtpHost + ";" + emailPort + ";"
					+ emailSubject + ";" + configSet + ";" + urlEnvio);
			mv.addObject("usuarioLogado", usuarioBean.getUsuario());
			return mv;
			
		} else {
			
			mv = new ModelAndView("redirect:/index");
			return mv;
		}
	}

	@RequestMapping(value = "alterarDepartamento", method = RequestMethod.POST)
	public ModelAndView alterarDepartamento(Long parametroId, String chave, String valor, String descricao) {

		ModelAndView mv = new ModelAndView("configuracao/configuracao");

		if (UserPrivilegies.eAdmin(usuarioBean.getUsuario())) {

			ParametrosSistema parametrosSistema = parametrosRepository.findByParametroId(parametroId);
			parametrosSistema.setChave(chave);
			parametrosSistema.setValor(valor);
			parametrosSistema.setDescricao(descricao);
			parametrosRepository.save(parametrosSistema);
			log.info("Dados alterados no banco: " + parametroId + ";" + chave + ";" + valor + ";" + descricao);

			mv.addObject("usuarioLogado", usuarioBean.getUsuario());
			return mv;
			
		} else {
			
			mv = new ModelAndView("redirect:/index");
			return mv;
		}
	}
}