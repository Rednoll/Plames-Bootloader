package enterprises.inwaiders.plames.bootloader.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bootloader")
public class BootloaderConfigPageController {

	@GetMapping("/config")
	public String mainPage(Model model) {
		
		return "config";
	}
}
