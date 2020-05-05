package enterprises.inwaiders.plames.bootloader.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

@Service
public class SpringJaksonConfig {
	
	@Autowired
	private ObjectMapper mapper;
	
	@PostConstruct
	private void postConstruct() {
		
		VisibilityChecker visChecker = mapper.getSerializationConfig().getDefaultVisibilityChecker();
			visChecker.withFieldVisibility(Visibility.ANY);
		
		mapper.setVisibility(visChecker);
	}
}
