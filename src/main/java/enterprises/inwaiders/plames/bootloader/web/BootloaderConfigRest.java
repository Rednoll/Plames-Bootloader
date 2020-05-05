package enterprises.inwaiders.plames.bootloader.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import enterprises.inwaiders.plames.bootloader.utils.DatabaseTypesRegistry;

@RestController
@RequestMapping("/bootloader/config")
public class BootloaderConfigRest {

	@GetMapping("/verify")
	public ResponseEntity<Boolean> verifyProductKey(String productKey) {
	
		//TODO
		return ResponseEntity.ok().body(true);
	}
	
	@GetMapping("/db/types")
	public List<String> dbType() {
	
		//TODO
		return DatabaseTypesRegistry.getTypes().stream().map(type -> type.getName()).collect(Collectors.toList()); 
	}
	
	@PostMapping("/db/data")
	public ResponseEntity<Boolean> dbData(DatabaseData data) {
		
		return ResponseEntity.ok().body(true);
	}
	
	public static class DatabaseData {
		
		public String username;
		public String password;
		public String url;
		public String type;
	}
}
