package enterprises.inwaiders.plames.bootloader.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Profile(value = "prod")
@EnableWebSecurity
public class SpringSecurityBootloaderProdConfig extends WebSecurityConfigurerAdapter {

	public SpringSecurityBootloaderProdConfig() {
		super();
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        
		http
			.authorizeRequests().antMatchers("/bootloader/reboot").permitAll();
	}
}
