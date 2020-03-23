package com.inwaiders.plames;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inwaiders.plames.api.module.Module;
import com.inwaiders.plames.api.module.ModuleRegistry;
import com.inwaiders.plames.api.module.ModuleStatus;
import com.inwaiders.plames.api.module.additions.ModulesContainer;
import com.inwaiders.plames.bootloader.stages.Stage;
import com.inwaiders.plames.bootloader.stages.StageBase;
import com.inwaiders.plames.bootloader.stages.core.CoreInitStage;
import com.inwaiders.plames.bootloader.stages.core.CorePostInitStage;
import com.inwaiders.plames.bootloader.stages.core.CorePreInitStage;
import com.inwaiders.plames.bootloader.stages.core.CoreSearchStage;
import com.inwaiders.plames.bootloader.stages.duplicated.DuplicationCheckStage;
import com.inwaiders.plames.bootloader.stages.duplicated.DuplicationResolveStrategy;
import com.inwaiders.plames.bootloader.stages.duplicated.strategies.BaseDuplicationResolveStrategy;
import com.inwaiders.plames.bootloader.stages.impl.ActivationStage;
import com.inwaiders.plames.bootloader.stages.impl.ApplicationRegistrationStage;
import com.inwaiders.plames.bootloader.stages.impl.InitStage;
import com.inwaiders.plames.bootloader.stages.impl.LoadModulesSettingsStage;
import com.inwaiders.plames.bootloader.stages.impl.PostInitStage;
import com.inwaiders.plames.bootloader.stages.impl.PreInitStage;
import com.inwaiders.plames.bootloader.stages.impl.RegistrationStage;
import com.inwaiders.plames.bootloader.stages.license.LicenseTestStage;
import com.inwaiders.plames.bootloader.stages.license.LicenseTestStrategy;
import com.inwaiders.plames.bootloader.stages.license.strategies.BaseLicenseTestStrategy;

@Configuration
@EnableCaching
@EnableWebMvc
@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class PlamesBootloader {
	
	public static Logger LOGGER = LoggerFactory.getLogger(PlamesBootloader.class);
	
	public static LicenseTestStrategy VERIFICATION_STRATEGY = new BaseLicenseTestStrategy();
	
	public static DuplicationResolveStrategy DUPLICATION_STRATEGY = new BaseDuplicationResolveStrategy();
	
	public static Module CORE = null;
	
	public static ObjectMapper JSON = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	
	public static File DATA_FOLDER = new File("./data");
	
	public static void main(String[] args) {
		
		SpringApplication.run(PlamesBootloader.class, args);
		
		bootload(Arrays.asList(args));
	}
	
	public static void bootload(List<String> args) {
		
		if(args.contains("--DataFolder: ")) {
			
			DATA_FOLDER = new File(args.get(args.indexOf("--DataFolder")+1));
		}
		
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
		
		ModulesContainer modules = ModuleRegistry.getModules().getNonDeepCopy();
		
		Stage startStage = getDefaultBootSequence();
	
		startStage.run(modules);
	}
	
	public static Stage getDefaultBootSequence() {
		
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		
		Stage result = StageBase.EMPTY;
		
		result = (Stage) context.getBean(CorePostInitStage.class, result);
		
		result = (Stage) context.getBean(PostInitStage.class, result);
		
		result = (Stage) context.getBean(InitStage.class, result);
		
		result = (Stage) context.getBean(CoreInitStage.class, result);
		
		result = (Stage) context.getBean(PreInitStage.class, result);
		
		result = new ApplicationRegistrationStage(result);
		
		result = (Stage) context.getBean(CorePreInitStage.class, result);
		
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
