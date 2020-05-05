package enterprises.inwaiders.plames.bootloader.web;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import enterprises.inwaiders.plames.PlamesBootloader;
import enterprises.inwaiders.plames.bootloader.utils.DatabasePlatformsRegistry;

@RestController
@RequestMapping("/bootloader/config")
public class BootloaderConfigRest {

	@GetMapping("/verify")
	public ResponseEntity<Boolean> verifyProductKey(String productKey) {
		
		//TODO
		Properties props = PlamesBootloader.MAIN_PROPS;
			props.setProperty("plames.product_key", productKey);
		
		PlamesBootloader.saveMainProps();
		
		return ResponseEntity.ok().body(true);
	}
	
	@GetMapping("/db/platforms")
	public List<String> dbPlatform() {
	
		//TODO
		return DatabasePlatformsRegistry.getPlatforms().stream().map(platform -> platform.getName()).collect(Collectors.toList()); 
	}
	
	@PostMapping("/db/data")
	public ResponseEntity<Boolean> dbData(String username, String password, String url, String platform) {
		
		Properties props = PlamesBootloader.PROD_APPLICATON_PROPS;
			props.setProperty("spring.datasource.username", username);
			props.setProperty("spring.datasource.password", password);
			props.setProperty("spring.datasource.url", url);
			props.setProperty("spring.datasource.jdbcUrl", url);
			props.setProperty("spring.datasource.platform", platform);
			props.setProperty("spring.jpa.database-platform", DatabasePlatformsRegistry.findByName(platform).getDriverClass());
		
		boolean valid = PlamesBootloader.validateDatabaseData();
		
		if(valid) {
			
			PlamesBootloader.saveProdApplicationProps();
		
			return ResponseEntity.ok().body(true);
		}
		else {
			
			return ResponseEntity.badRequest().body(false);
		}
	}
}
