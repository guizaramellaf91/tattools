package br.com.zaratech.security;

import br.com.zaratech.model.Usuario;

public class UserPrivilegies {

	public static boolean eAdmin(Usuario usuario) {
		try {
			if (usuario.getLogin().equals("admin") || usuario.getLogin().equals("master") && usuario.getTipoUsuario().getNomeTipo().equals("STUDIO")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean eStudio(Usuario usuario) {
		try {
			if (usuario.getTipoUsuario().getNomeTipo().equals("STUDIO")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean eTatuador(Usuario usuario) {
		try {
			if (usuario.getTipoUsuario().getNomeTipo().equals("TATUADOR")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}