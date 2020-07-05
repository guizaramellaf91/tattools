package br.com.zaratech.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames="login"),@UniqueConstraint(columnNames="email")})
public class Usuario implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long usuarioId;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String login;
	
	@Column(nullable = false)
	private String senha;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private boolean ativo;
		
	@Column(nullable = false)
	private long acessos;
	
	private Date ultimoAcesso;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "tipo_usuario_id")
	private TipoUsuario tipoUsuario;
			
	@Column(nullable = false)
	private BigDecimal comissaoDinheiro;
	
	@Column(nullable = false)
	private BigDecimal comissaoDebito;
	
	@Column(nullable = false)
	private BigDecimal comissaoCredito;
	
	@Column(nullable = false)
	private int quantidadeParcelas;
	
	@Column(nullable = false)
	private BigDecimal comissaoDinheiroAcimaDe;
	
	@Column(nullable = false)
	private BigDecimal comissaoDebitoAcimaDe;
	
	@Column(nullable = false)
	private BigDecimal comissaoCreditoAcimaDe;
	
	@Column(nullable = false)
	private BigDecimal valorComissaoAcimaDe;
	
	@Column(nullable = false)
	private BigDecimal comissaoProduto;
	
	@OneToMany
	private List<Role> roles;
	
	public Usuario() {}
	
	public long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
		
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) this.roles;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public long getAcessos() {
		return acessos;
	}

	public void setAcessos(long acessos) {
		this.acessos = acessos;
	}

	public Date getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(Date ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public BigDecimal getComissaoDinheiro() {
		return comissaoDinheiro;
	}

	public void setComissaoDinheiro(BigDecimal comissaoDinheiro) {
		this.comissaoDinheiro = comissaoDinheiro;
	}

	public BigDecimal getComissaoDebito() {
		return comissaoDebito;
	}

	public void setComissaoDebito(BigDecimal comissaoDebito) {
		this.comissaoDebito = comissaoDebito;
	}

	public BigDecimal getComissaoCredito() {
		return comissaoCredito;
	}

	public void setComissaoCredito(BigDecimal comissaoCredito) {
		this.comissaoCredito = comissaoCredito;
	}

	public int getQuantidadeParcelas() {
		return quantidadeParcelas;
	}
	
	public BigDecimal getComissaoDinheiroAcimaDe() {
		return comissaoDinheiroAcimaDe;
	}

	public void setComissaoDinheiroAcimaDe(BigDecimal comissaoDinheiroAcimaDe) {
		this.comissaoDinheiroAcimaDe = comissaoDinheiroAcimaDe;
	}

	public BigDecimal getComissaoDebitoAcimaDe() {
		return comissaoDebitoAcimaDe;
	}

	public void setComissaoDebitoAcimaDe(BigDecimal comissaoDebitoAcimaDe) {
		this.comissaoDebitoAcimaDe = comissaoDebitoAcimaDe;
	}

	public BigDecimal getComissaoCreditoAcimaDe() {
		return comissaoCreditoAcimaDe;
	}

	public void setComissaoCreditoAcimaDe(BigDecimal comissaoCreditoAcimaDe) {
		this.comissaoCreditoAcimaDe = comissaoCreditoAcimaDe;
	}

	public BigDecimal getValorComissaoAcimaDe() {
		return valorComissaoAcimaDe;
	}

	public void setValorComissaoAcimaDe(BigDecimal valorComissaoAcimaDe) {
		this.valorComissaoAcimaDe = valorComissaoAcimaDe;
	}

	public BigDecimal getComissaoProduto() {
		return comissaoProduto;
	}

	public void setComissaoProduto(BigDecimal comissaoProduto) {
		this.comissaoProduto = comissaoProduto;
	}

	public void setQuantidadeParcelas(int quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}
}