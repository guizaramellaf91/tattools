package br.com.zaratech.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zaratech.bean.Parametros;
import br.com.zaratech.model.ParametrosEmail;
import br.com.zaratech.model.ParametrosSistema;
import br.com.zaratech.repository.ParametrosEmailRepository;
import br.com.zaratech.repository.ParametrosRepository;
import br.com.zaratech.service.UsuarioService;

@Service
public class SendMail {
	
	static Logger log = Logger.getLogger(SendMail.class);
	
	@Autowired
	private ParametrosEmailRepository parametrosEmailRepository;
	@Autowired
	private ParametrosRepository parametrosRepository;
	@Autowired
	private UsuarioService usuarioService;
	        
	public void Enviar(String destinatario, int defineBody) throws UnsupportedEncodingException, MessagingException {
		
		final ParametrosSistema emailDeEnvio = parametrosRepository.findByChave(Parametros.EMAIL_DE_ENVIO);
		
		if(emailDeEnvio != null && emailDeEnvio.getChave() != null && emailDeEnvio.getValor() != null) {
			ParametrosEmail parametroEmail = parametrosEmailRepository.findByEmailFrom(emailDeEnvio.getValor());

			if (parametroEmail != null) {

			    String BODY_CADASTRO_USUARIO = String.join(
			    	    System.getProperty("line.separator"),
			    	    "<h1>Seja bem vindo ao <strong>Tattools</strong></h1>",
			    	    "<p>Seu cadastro foi realizado com sucesso, acesse e desfrute da ferramenta exclusiva para usuários do sistema Tattools.", 
			    	    "<a href='" + parametroEmail.getUrlEnvio() + "'><strong>Clique Aqui</strong></a>");
			    
			    String BODY_RECUPERACAO_SENHA = String.join(
			    	    System.getProperty("line.separator"),
			    	    "<h1>Recupere seu acesso ao <strong>Tattools</strong></h1>",
			    	    "<p>Clique na URL a seguir para recuperar seu acesso!", 
			    	    "<a href='" + parametroEmail.getUrlEnvio() + "recuperarAcesso?id=" + usuarioService.idRecuperacao + "'><strong>Clique Aqui</strong></a>");
				
				Properties props = System.getProperties();
				props.put("mail.transport.protocol", "smtp");
				props.put("mail.smtp.port", parametroEmail.getEmailPort());
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.auth", "true");
				Session session = Session.getDefaultInstance(props);
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(parametroEmail.getEmailFrom(), parametroEmail.getEmailFromName()));
				msg.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
				msg.setSubject(parametroEmail.getEmailSubject());
				if (defineBody == 0) {
					msg.setContent(BODY_CADASTRO_USUARIO, "text/html");
				} else {
					msg.setContent(BODY_RECUPERACAO_SENHA, "text/html");
				}
				msg.setHeader("X-SES-CONFIGURATION-SET", parametroEmail.getConfigSet());
				Transport transport = session.getTransport();
				try {
					log.info("Enviando E-mail...");
					transport.connect(parametroEmail.getEmailSmtpHost(), parametroEmail.getEmailSmtpUsername(),
							parametroEmail.getEmailSmtpPassword());
					transport.sendMessage(msg, msg.getAllRecipients());
					log.info("Email enviado para " + destinatario);
				} catch (Exception ex) {
					log.info("Nao foi possivel enviar o e-mail. Motivo: " + ex.getMessage());
				} finally {
					transport.close();
				}
			} else {
				log.error("Nao foram encontrados os parametros para envio de e-mail.");
			}
		} else {
			log.info("O envio de e-mail não está habilitado!");
		}
	}
}