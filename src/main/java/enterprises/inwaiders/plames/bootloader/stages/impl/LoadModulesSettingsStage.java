package enterprises.inwaiders.plames.bootloader.stages.impl;

import java.util.Set;

import enterprises.inwaiders.plames.PlamesBootloader;
import enterprises.inwaiders.plames.api.module.Module;
import enterprises.inwaiders.plames.bootloader.stages.Stage;
import enterprises.inwaiders.plames.bootloader.stages.StageBase;

public class LoadModulesSettingsStage extends StageBase{

	public LoadModulesSettingsStage() {
		
	}
	
	public LoadModulesSettingsStage(Stage next) {
		super(next);
		
	}
	
	@Override
	public void run(Set<Module> modules) {
		
		for(Module module : modules) {
			
			PlamesBootloader.loadModule(module);
		}
		
		nextStage.run(modules);
	}
}
