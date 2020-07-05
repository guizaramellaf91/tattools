package br.com.zaratech.tattools;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

// nohup /dados/jdk8/bin/java -jar /dados/VendaWebRestParceiros/ws-web-rest.jar nohup.out & tail -f nohup.out

@SpringBootApplication
@EnableAutoConfiguration
@Repository("br.com.zaratech.repository")
@ComponentScan(basePackages = {"br.com.zaratech"})
@EntityScan("br.com.zaratech.model")
@PropertySources({@PropertySource("application.properties")})
@EnableJpaRepositories(basePackages = {"br.com.zaratech.repository","br.com.zaratech.security"})
public class TattoolsApplication {
	
	static Logger log = Logger.getLogger(TattoolsApplication.class);
		
	public static void main(String[] args) {
		SpringApplication.run(TattoolsApplication.class, args);
		//log.info(":" + new BCryptPasswordEncoder().encode("admin") + ":");
	}
}