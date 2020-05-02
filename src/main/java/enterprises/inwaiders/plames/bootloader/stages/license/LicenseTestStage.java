package enterprises.inwaiders.plames.bootloader.stages.license;

import java.util.Set;

import enterprises.inwaiders.plames.PlamesBootloader;
import enterprises.inwaiders.plames.api.module.Module;
import enterprises.inwaiders.plames.bootloader.stages.Stage;
import enterprises.inwaiders.plames.bootloader.stages.StageBase;

public class LicenseTestStage extends StageBase{
	
	public LicenseTestStage() {
		
	}
	
	public LicenseTestStage(Stage next) {
		super(next);
		
	}
	
	public void run(Set<Module> modules) {
		
		LicenseTestStrategy strategy = PlamesBootloader.VERIFICATION_STRATEGY;
		
		for(Module module : modules) {
			
			LicenseTestResult result = strategy.verificate(module);
		
			if(result.isBad()) {
				
				modules.remove(module);
				result.log(module);
			}
		}
		
		PlamesBootloader.LOGGER.info("License test stage complete!");
		
		nextStage.run(modules);
	}
}
