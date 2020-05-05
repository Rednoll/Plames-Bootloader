package enterprises.inwaiders.plames.bootloader.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ConfigInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if(request.getRequestURI().startsWith("/resources")) return true;
		
		System.out.println("req: "+request.getRequestURI());
		
		if(!request.getRequestURI().startsWith("/bootloader/config")) {
			
			response.sendRedirect("/bootloader/config");
		
			return false;
		}
	
		return true;
	}
}
