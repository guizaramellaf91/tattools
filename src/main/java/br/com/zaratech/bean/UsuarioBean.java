package br.com.zaratech.bean;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import br.com.zaratech.model.Usuario;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class UsuarioBean implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	static Logger log = Logger.getLogger(UsuarioBean.class);
	
	private Usuario usuario;
	private int pageSize;
	private int page;
		    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}