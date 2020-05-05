package enterprises.inwaiders.plames.bootloader.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import enterprises.inwaiders.plames.bootloader.utils.DatabaseTypesRegistry;

@Controller
@RequestMapping("/bootloader/config")
public class BootloaderConfigPageController {

	@GetMapping("")
	public String mainPage(Model model) {
		
		return "config";
	}
	
	@GetMapping("/verify")
	public ResponseEntity<Boolean> verifyProductKey(@PathVariable String productKey) {
	
		//TODO
		return ResponseEntity.ok().body(true);
	}
	
	@GetMapping("/db_type")
	public List<String> verifyProductKey() {
	
		//TODO
		return DatabaseTypesRegistry.getTypes().stream().map(type -> type.getName()).collect(Collectors.toList()); 
	}
}
