package enterprises.inwaiders.plames.bootloader.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Profile(value = "init")
@EnableWebSecurity
public class SpringSecurityInitConfig extends WebSecurityConfigurerAdapter {

	public SpringSecurityInitConfig() {
		super();
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        
		http	
			.csrf().disable();

		http
			.authorizeRequests().antMatchers("/**").permitAll();
	}
}
