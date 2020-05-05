package enterprises.inwaiders.plames.bootloader.web;

import java.util.Arrays;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
//@Import(value= {SpringSecurityConfig.class})
public class SpringMvcConfig implements WebMvcConfigurer {

    @Autowired
    private Environment env;
    
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	
    	registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/");
    	registry.addResourceHandler("/data/**").addResourceLocations("file:data/");
    }
    
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		if(Arrays.asList(env.getActiveProfiles()).contains("init")) {
			
			registry.addInterceptor(new ConfigInterceptor()).addPathPatterns("/**").excludePathPatterns("/resources/**", "/bootloader/config/**");
		}
	}
    
	@Bean
	public ServletWebServerFactory servletContainer() {
		
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		
			String port = env.getProperty("server.http.port");
			
			if(port != null) {
			
				tomcat.addAdditionalTomcatConnectors(createStandardConnector(Integer.parseInt(env.getProperty("server.http.port"))));
			}
			
		return tomcat;
	}

	private Connector createStandardConnector(int port) {
		
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
			connector.setPort(port);
		
		return connector;
	}
}
