package enterprises.inwaiders.plames.bootloader.web.modules.ajax;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import enterprises.inwaiders.plames.PlamesBootloader;
import enterprises.inwaiders.plames.api.module.Module;
import enterprises.inwaiders.plames.api.module.ModuleRegistry;

@RestController
@RequestMapping("web/controller/ajax/module")
public class ModuleWebAjax {

	@PostMapping("/active")
	public ResponseEntity activeToggle(@RequestBody JsonNode json) {
		
		if(!json.has("module") || !json.get("module").isNumber()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
		if(!json.has("active") || !json.get("active").isBoolean()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
	
		long moduleId = json.get("module").asLong();
		boolean active = json.get("active").asBoolean();
	
		Module module = ModuleRegistry.getById(moduleId);
		
		if(module != null) {
		
			if(active) {
				
				PlamesBootloader.requestActivation(module);
			}
			else {
				
				PlamesBootloader.requestShutdown(module);
			}
			
			PlamesBootloader.saveModule(module);
			
			return new ResponseEntity(HttpStatus.OK);
		}
		
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
}
