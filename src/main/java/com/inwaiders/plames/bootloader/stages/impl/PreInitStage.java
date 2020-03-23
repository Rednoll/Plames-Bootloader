package com.inwaiders.plames.bootloader.stages.impl;

import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.inwaiders.plames.PlamesBootloader;
import com.inwaiders.plames.api.module.Module;
import com.inwaiders.plames.api.module.ModuleStatus;
import com.inwaiders.plames.bootloader.stages.Stage;
import com.inwaiders.plames.bootloader.stages.StageBase;

@Component("PreInitStage")
@Scope("prototype")
public class PreInitStage extends StageBase{

	public PreInitStage() {
		
	}
	
	public PreInitStage(Stage next) {
		super(next);
		
	}
	
	@Override
	public void run(Set<Module> modules) {
	
		c2: while(true) {
			
			for(Module module : modules) {
				
				if(module == PlamesBootloader.CORE) continue;
				
				if(module.getStatus() != ModuleStatus.ACTIVE) continue;
				
				try {
					
					PlamesBootloader.LOGGER.info("Module "+module.getName()+"#"+module.getVersion()+" preinitialization begin!");
					
					module.preInit();
					
					PlamesBootloader.LOGGER.info("Module "+module.getName()+"#"+module.getVersion()+" successful preinitialized!");
				}
				catch(Exception e) {
					
					e.printStackTrace();
				
					modules.remove(module);
					continue c2;
				}
			}
			
			break;
		}
		
		PlamesBootloader.LOGGER.info("Pre initialization stage complete!");
		
		nextStage.run(modules);
	}
}
