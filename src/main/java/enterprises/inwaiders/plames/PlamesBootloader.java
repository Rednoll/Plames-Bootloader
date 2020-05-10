package enterprises.inwaiders.plames;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.SystemUtils;
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
	public static File CONFIG_FOLDER = new File("./config");
	
	public static Properties MAIN_PROPS = null;
	
	public static Properties PROD_APPLICATON_PROPS = new Properties();
	
	public static ApplicationContext CONTEXT = null;
	
	public static Environment ENV = null;
	
	public static List<String> JVM_ARGS = null;
	public static List<String> LAUNCH_ARGS = null;
	
	public static void main(String[] args) {

		JVM_ARGS = ManagementFactory.getRuntimeMXBean().getInputArguments();
		
		List<String> listArgs = new ArrayList<>(Arrays.asList(args));
		
		LAUNCH_ARGS = new ArrayList<>(listArgs);
		
		File prodApplicationPropsFile = new File(CONFIG_FOLDER, "application-prod.properties");
		
		if(prodApplicationPropsFile.exists()) {
			
			try {
				
				PROD_APPLICATON_PROPS.load(new FileInputStream(prodApplicationPropsFile));
			
				listArgs.add("--spring.profiles.active=prod");
			}
			catch(FileNotFoundException e) {
				
				e.printStackTrace();
			}
			catch(IOException e) {
				
				e.printStackTrace();
			}
		}
		
		CONTEXT = SpringApplication.run(PlamesBootloader.class, listArgs.toArray(new String[0]));
		ENV = CONTEXT.getEnvironment();
		
		bootload(listArgs);
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
		
		if(!DATA_FOLDER.exists()) {
			
			DATA_FOLDER.mkdirs();
		}
		
		File propertiesFile = new File("main.properties");
		
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
	
	public static boolean validateDatabaseData() {
		
		String url = PROD_APPLICATON_PROPS.getProperty("spring.datasource.url");
		String user = PROD_APPLICATON_PROPS.getProperty("spring.datasource.username");
		String pass = PROD_APPLICATON_PROPS.getProperty("spring.datasource.password");
		
		Connection conn = null;
		
		try {
			
			conn = DriverManager.getConnection(url, user, pass);
		}
		catch(SQLException e) {
			
			e.printStackTrace();
			
			return false;
		}
		
		try {
			
			conn.createStatement().execute("CREATE TABLE TEST (id INTEGER not NULL, PRIMARY KEY ( id ))");
			conn.createStatement().execute("DROP TABLE TEST");
		}
		catch(SQLException e) {
			
			e.printStackTrace();
			
			return false;
		}
		
		return true;
	}
	
	private static boolean validateMainProps(Properties props) {
		
		if(!props.containsKey("plames.product_key")) {
			
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
	
	public static void reboot() {
		
		try {
			
			String command = null;
			
			if(SystemUtils.IS_OS_WINDOWS) {
				
				command = "cmd /c run.bat";
			}
			else if(SystemUtils.IS_OS_UNIX) {
				
				command = "run.sh";
			}
			
			ProcessBuilder builder = new ProcessBuilder(command);
				builder.directory(new File("./"));
			
			builder.start();
			
			System.exit(0);
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
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
	
	public static void saveMainProps() {
		
		File propertiesFile = new File("main.properties");
		
		try {
			
			if(!propertiesFile.exists()) {
				
				propertiesFile.createNewFile();
			}
			
			MAIN_PROPS.store(new FileOutputStream(propertiesFile), "");
		}
		catch(FileNotFoundException e) {
			
			e.printStackTrace();
		}
		catch(IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void saveProdApplicationProps() {
		
		File prodApplicationPropsFile = new File(CONFIG_FOLDER, "application-prod.properties");
		
		try {
			
			if(!prodApplicationPropsFile.exists()) {
				
				prodApplicationPropsFile.createNewFile();
			}
			
			PROD_APPLICATON_PROPS.store(new FileOutputStream(prodApplicationPropsFile), "");
		}
		catch(FileNotFoundException e) {
			
			e.printStackTrace();
		}
		catch(IOException e) {
			
			e.printStackTrace();
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
