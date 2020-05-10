package enterprises.inwaiders.plames.bootloader.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import enterprises.inwaiders.plames.PlamesBootloader;

@RestController
@RequestMapping("/bootloader")
public class BootloaderController {

	@GetMapping("/reboot")
	public void reboot() {
	
		new Thread(()-> PlamesBootloader.reboot()).start();
	}
	
	@GetMapping("/time")
	public long getTime() {
		
		return System.currentTimeMillis();
	}
}
