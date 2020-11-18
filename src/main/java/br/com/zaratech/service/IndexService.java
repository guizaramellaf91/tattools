package br.com.zaratech.service;

import br.com.zaratech.bean.Parametros;
import br.com.zaratech.bean.UsuarioBean;
import br.com.zaratech.controller.IndexController;
import br.com.zaratech.model.ParametrosSistema;
import br.com.zaratech.repository.ParametrosRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class IndexService {

    static Logger log = Logger.getLogger(IndexService.class);

    @Autowired
    private UsuarioBean usuarioBean;
    @Autowired
    private ParametrosRepository parametrosRepository;

    public String home() {
        if (usuarioBean != null) {
            return "redirect:/index";
        } else {
            return "redirect:/login";
        }
    }

    public ModelAndView login() {

        ModelAndView mv = new ModelAndView("login");
        mv.addObject("msg", "");
        return mv;
    }

    public ModelAndView index() {

        ModelAndView mv = new ModelAndView("index");

        if(usuarioBean.getUsuario() != null) {

            final ParametrosSistema modalInformativo = parametrosRepository.findByChave(Parametros.MODAL_INFORMATIVO);
            if(modalInformativo != null && modalInformativo.getChave() != null && modalInformativo.getValor().equals("1")) {
                mv.addObject("modalInformativo",true);
            }else {
                mv.addObject("modalInformativo",false);
            }
            mv.addObject("usuarioLogado", usuarioBean.getUsuario());
            return mv;
        }else {
            mv.addObject("msg", "");
            mv = new ModelAndView("login");
            return mv;
        }
    }

    public String isLogout() {

        return "logout";
    }

    public String logout() {

        log.info("Logout realizado!");
        return "logout";
    }
}