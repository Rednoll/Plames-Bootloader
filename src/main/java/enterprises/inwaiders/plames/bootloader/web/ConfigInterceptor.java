package enterprises.inwaiders.plames.bootloader.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import enterprises.inwaiders.plames.PlamesBootloader;

public class ConfigInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if(PlamesBootloader.CONFIGURATION_REQUIRED) {
			
			System.out.println("request.getContextPath(): "+request.getContextPath());
			
			if(request.getContextPath().startsWith("/resources")) return true;
			
			if(!request.getRequestURI().startsWith("/bootloader/config")) {
				
				response.sendRedirect("/bootloader/config");
			
				return false;
			}
		}
		
		return true;
	}
}
