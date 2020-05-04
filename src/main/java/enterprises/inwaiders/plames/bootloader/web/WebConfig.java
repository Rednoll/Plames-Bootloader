package enterprises.inwaiders.plames.bootloader.web;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private Environment env;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		if(Arrays.asList(env.getActiveProfiles()).contains("init")) {
			
			registry.addInterceptor(new ConfigInterceptor());
		}
	}
}
