package enterprises.inwaiders.plames.bootloader.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bootloader")
public class BootloaderConfigPageController {

	@GetMapping("/config")
	public String mainPage(Model model) {
		
		return "config";
	}
	
	@GetMapping("/verify")
	public ResponseEntity<Boolean> verifyProductKey(@PathVariable String productKey) {
	
		//TODO
		return ResponseEntity.ok().body(true);
	}
}
