package enterprises.inwaiders.plames.bootloader.stages.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

import enterprises.inwaiders.plames.PlamesBootloader;
import enterprises.inwaiders.plames.api.module.Module;
import enterprises.inwaiders.plames.api.module.ModuleRegistry;
import enterprises.inwaiders.plames.bootloader.stages.Stage;
import enterprises.inwaiders.plames.bootloader.stages.StageBase;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

public class RegistrationStage extends StageBase{

	public RegistrationStage() {
		
	}
	
	public RegistrationStage(Stage next) {
		super(next);
		
	}
	
	@Override
	public void run(Set<Module> modules) {
		
		ScanResult scanResult = new ClassGraph().enableAllInfo().scan();
		ClassInfoList idataClasses = scanResult.getClassesImplementing(Module.class.getCanonicalName());
		List<Class<?>> coreModulesClasses = idataClasses.loadClasses();
		
		for(Class<?> clazz : coreModulesClasses) {
			
			if(Modifier.isAbstract(clazz.getModifiers())) continue;
			
			try {
				
				Method method = clazz.getDeclaredMethod("getInstance");
			
				Module module = (Module) method.invoke(null);
				
				PlamesBootloader.LOGGER.info("Found module "+module.getName()+"#"+module.getId());
				
				ModuleRegistry.register(module);
				
				modules.add(module);
			}
			catch(Exception e) {
				
				e.printStackTrace();
			}
		}
		
		PlamesBootloader.LOGGER.info("Registration modules stage complete!");
		
		nextStage.run(modules);
	}
}
