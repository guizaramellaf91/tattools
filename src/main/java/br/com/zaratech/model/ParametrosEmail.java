package br.com.zaratech.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "parametros_email", uniqueConstraints = {@UniqueConstraint(columnNames="emailFrom")})
public class ParametrosEmail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long parametroEmailId;
	@Column(nullable = false)
	private String emailFrom;
	@Column(nullable = false)
	private String emailFromName;
	@Column(nullable = false)
	private String emailSmtpUsername;
	@Column(nullable = false)
	private String emailSmtpPassword;
	@Column(nullable = false)
	private String emailSmtpHost;
	@Column(nullable = false)
	private int emailPort;
	@Column(nullable = false)
	private String emailSubject;
	@Column(nullable = false)
	private String configSet;
	@Column(nullable = false)
	private String urlEnvio;
	
	public Long getParametroEmailId() {
		return parametroEmailId;
	}
	public void setParametroEmailId(Long parametroEmailId) {
		this.parametroEmailId = parametroEmailId;
	}
	public String getEmailFrom() {
		return emailFrom;
	}
	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}
	public String getEmailFromName() {
		return emailFromName;
	}
	public void setEmailFromName(String emailFromName) {
		this.emailFromName = emailFromName;
	}
	public String getEmailSmtpUsername() {
		return emailSmtpUsername;
	}
	public void setEmailSmtpUsername(String emailSmtpUsername) {
		this.emailSmtpUsername = emailSmtpUsername;
	}
	public String getEmailSmtpPassword() {
		return emailSmtpPassword;
	}
	public void setEmailSmtpPassword(String emailSmtpPassword) {
		this.emailSmtpPassword = emailSmtpPassword;
	}
	public String getEmailSmtpHost() {
		return emailSmtpHost;
	}
	public void setEmailSmtpHost(String emailSmtpHost) {
		this.emailSmtpHost = emailSmtpHost;
	}
	public int getEmailPort() {
		return emailPort;
	}
	public void setEmailPort(int emailPort) {
		this.emailPort = emailPort;
	}
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public String getConfigSet() {
		return configSet;
	}
	public void setConfigSet(String configSet) {
		this.configSet = configSet;
	}
	public String getUrlEnvio() {
		return urlEnvio;
	}
	public void setUrlEnvio(String urlEnvio) {
		this.urlEnvio = urlEnvio;
	}
}