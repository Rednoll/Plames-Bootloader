package com.inwaiders.plames.bootloader.stages.license;

import java.util.Set;

import com.inwaiders.plames.PlamesBootloader;
import com.inwaiders.plames.api.module.Module;
import com.inwaiders.plames.bootloader.stages.Stage;
import com.inwaiders.plames.bootloader.stages.StageBase;

public class LicenseTestStage extends StageBase{
	
	public LicenseTestStage() {
		
	}
	
	public LicenseTestStage(Stage next) {
		super(next);
		
	}
	
	public void run(Set<Module> modules) {
		
		for(Module module : modules) {
			
			LicenseTestResult result = PlamesBootloader.VERIFICATION_STRATEGY.verificate(module);
		
			if(result.isBad()) {
				
				modules.remove(module);
				result.log(module);
			}
		}
		
		PlamesBootloader.LOGGER.info("License test stage complete!");
		
		nextStage.run(modules);
	}
}
