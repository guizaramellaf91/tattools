package br.com.zaratech.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/usuario/**").hasRole("ADMIN")
        .antMatchers("/anonymous*").anonymous()
        .antMatchers("/login*").permitAll()
        .antMatchers("/cadastrarUsuarioAvulso*").permitAll()
        .antMatchers("/esqueciMinhaSenha*").permitAll()
        .antMatchers("/recuperarAcesso*").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/index", false)
        .failureUrl("/login?error")
        .and()
        .logout()
        .logoutUrl("/logout");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/css/**", "/img/**", "/js/**");
	}
}
