package enterprises.inwaiders.plames.bootloader.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Profile(value = "init")
@EnableWebSecurity
@Order(Ordered.LOWEST_PRECEDENCE)
public class SpringSecurityBootloaderInitConfig extends WebSecurityConfigurerAdapter {

	public SpringSecurityBootloaderInitConfig() {
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
