package enterprises.inwaiders.plames;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import enterprises.inwaiders.plames.api.module.Module;
import enterprises.inwaiders.plames.api.module.ModuleRegistry;
import enterprises.inwaiders.plames.api.module.ModuleStatus;
import enterprises.inwaiders.plames.api.module.additions.ModulesContainer;
import enterprises.inwaiders.plames.bootloader.stages.Stage;
import enterprises.inwaiders.plames.bootloader.stages.StageBase;
import enterprises.inwaiders.plames.bootloader.stages.core.CoreInitStage;
import enterprises.inwaiders.plames.bootloader.stages.core.CorePostInitStage;
import enterprises.inwaiders.plames.bootloader.stages.core.CorePreInitStage;
import enterprises.inwaiders.plames.bootloader.stages.core.CoreSearchStage;
import enterprises.inwaiders.plames.bootloader.stages.duplicated.DuplicationCheckStage;
import enterprises.inwaiders.plames.bootloader.stages.duplicated.DuplicationResolveStrategy;
import enterprises.inwaiders.plames.bootloader.stages.duplicated.strategies.BaseDuplicationResolveStrategy;
import enterprises.inwaiders.plames.bootloader.stages.impl.ActivationStage;
import enterprises.inwaiders.plames.bootloader.stages.impl.ApplicationRegistrationStage;
import enterprises.inwaiders.plames.bootloader.stages.impl.InitStage;
import enterprises.inwaiders.plames.bootloader.stages.impl.LoadModulesSettingsStage;
import enterprises.inwaiders.plames.bootloader.stages.impl.PostInitStage;
import enterprises.inwaiders.plames.bootloader.stages.impl.PreInitStage;
import enterprises.inwaiders.plames.bootloader.stages.impl.RegistrationStage;
import enterprises.inwaiders.plames.bootloader.stages.license.LicenseTestStage;
import enterprises.inwaiders.plames.bootloader.stages.license.LicenseTestStrategy;
import enterprises.inwaiders.plames.bootloader.stages.license.strategies.BaseModuleLicenseTestStrategy;

@Configuration
@EnableCaching
@EnableWebMvc
@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class PlamesBootloader {
	
	public static Logger LOGGER = LoggerFactory.getLogger(PlamesBootloader.class);
	
	public static LicenseTestStrategy VERIFICATION_STRATEGY = new BaseModuleLicenseTestStrategy();
	
	public static DuplicationResolveStrategy DUPLICATION_STRATEGY = new BaseDuplicationResolveStrategy();
	
	public static Module CORE = null;
	
	public static ObjectMapper JSON = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	
	public static File DATA_FOLDER = new File("./data");
	
	public static Properties MAIN_PROPS = null;
	
	public static ApplicationContext CONTEXT = null;
	
	public static Environment ENV = null;
	
	public static void main(String[] args) {
		
		CONTEXT = SpringApplication.run(PlamesBootloader.class, args);
		ENV = CONTEXT.getEnvironment();
		
		bootload(Arrays.asList(args));
	}
	
	public static void bootload(List<String> args) {
		
		System.out.println("==========================================================================");
		System.out.println();
		System.out.println("   _ _          ___     ___      _ _     ╔═══╗ ╔╗   ╔══╗ ╔╗  ╔╗ ╔═══╗ ╔══╗\n" + 
				" /     \\      /    /  /    /   /     \\   ║╔═╗║ ║║   ║╔╗║ ║║  ║║ ║╔══╝ ║╔═╝\n" + 
				"|       |    /    /  /    /   |       |  ║╚═╝║ ║║   ║╚╝║ ║╚╗╔╝║ ║╚══╗ ║╚═╗\n" + 
				"|       |   /    /  /    /    |       |  ║╔══╝ ║║   ║╔╗║ ║╔╗╔╗║ ║╔══╝ ╚═╗║\n" + 
				" \\ _ _ /   /___ /  /___ /      \\ _ _ /   ║║    ║╚═╗ ║║║║ ║║╚╝║║ ║╚══╗ ╔═╝║\n" + 
				"                                         ╚╝    ╚══╝ ╚╝╚╝ ╚╝  ╚╝ ╚═══╝ ╚══╝");
		System.out.println();
		System.out.println("			:: Plames Bootloader 1V ::");
		System.out.println();
		System.out.println("==========================================================================");
		
		
		if(args.contains("--DataFolder: ")) {
			
			DATA_FOLDER = new File(args.get(args.indexOf("--DataFolder")+1));
		}
		
		File propertiesFile = new File(DATA_FOLDER, "main.properties");
		
		MAIN_PROPS = new Properties();
		
		if(propertiesFile.exists()) {
			
			try {
				
				MAIN_PROPS.load(new FileInputStream(propertiesFile));
			}
			catch(FileNotFoundException e) {
				
				e.printStackTrace();
			}
			catch(IOException e) {

				e.printStackTrace();
			}
		}
		
		if(Arrays.asList(ENV.getActiveProfiles()).contains("init")) {
			
			System.out.println("Please configure Plames on */bootloader/config page.");
			return;
		}
		
		ModulesContainer modules = ModuleRegistry.getModules().getNonDeepCopy();
		
		Stage startStage = getDefaultBootSequence();
	
		startStage.run(modules);
	}
	
	private static boolean validateMainProps(Properties props) {
		
		if(!props.containsKey("product_key")) {
			
			return false;
		}
		
		return true;
	}
	
	public static Stage getDefaultBootSequence() {
		
		Stage result = StageBase.EMPTY;
		
		result = (Stage) CONTEXT.getBean(CorePostInitStage.class, result);
		
		result = (Stage) CONTEXT.getBean(PostInitStage.class, result);
		
		result = (Stage) CONTEXT.getBean(InitStage.class, result);
		
		result = (Stage) CONTEXT.getBean(CoreInitStage.class, result);
		
		result = (Stage) CONTEXT.getBean(PreInitStage.class, result);
		
		result = new ApplicationRegistrationStage(result);
		
		result = (Stage) CONTEXT.getBean(CorePreInitStage.class, result);
		
		result = new CoreSearchStage(result);
		
		result = new ActivationStage(result);
		
		result = new LicenseTestStage(result);
		
		result = new DuplicationCheckStage(result);
		
		result = new LoadModulesSettingsStage(result);
		
		result = new RegistrationStage(result);
		
		return result;
	}
	
	public static void requestActivation(Module module) {
		
		if(module.getStatus() == ModuleStatus.INACTIVE || module.getStatus() == ModuleStatus.AWAITING_OFF) {
			
			module.setStatus(ModuleStatus.AWAITING_ON);
		}
	}
	
	public static void requestShutdown(Module module) {
		
		if(module.getStatus() == ModuleStatus.ACTIVE || module.getStatus() == ModuleStatus.AWAITING_ON) {
			
			module.setStatus(ModuleStatus.AWAITING_OFF);
		}
	}
	
	public static void saveModule(Module module) {
		
		File dataFile = new File(DATA_FOLDER, module.getId()+".data");
		
		try {
			
			if(!dataFile.exists()) {
				
				dataFile.getParentFile().mkdir();
				dataFile.createNewFile();
			}
			
			ObjectNode body = JSON.createObjectNode();
		
				body.set("status", JSON.valueToTree(module.getStatus()));
		
				
			JSON.writeValue(dataFile, body);
		}
		catch(JsonGenerationException e) {
		
			e.printStackTrace();
		}
		catch(JsonMappingException e) {
		
			e.printStackTrace();
		}
		catch(IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void loadModule(Module module) {
		
		File dataFile = new File(DATA_FOLDER, module.getId()+".data");
		
		if(dataFile.exists()) {
		
			try {
				
				ObjectNode node = JSON.readValue(dataFile, ObjectNode.class);
				
					if(node.has("status") && node.get("status").isTextual()) {
						
						module.setStatus(ModuleStatus.valueOf(node.get("status").asText()));
					}
			}
			catch (JsonParseException e) {
				
				e.printStackTrace();
			}
			catch (JsonMappingException e) {
				
				e.printStackTrace();
			}
			catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
}
