package com.inwaiders.plames.bootloader.stages.impl;

import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.inwaiders.plames.PlamesBootloader;
import com.inwaiders.plames.api.module.Module;
import com.inwaiders.plames.api.module.ModuleStatus;
import com.inwaiders.plames.bootloader.stages.Stage;
import com.inwaiders.plames.bootloader.stages.StageBase;

@Component("InitStage")
@Scope("prototype")
public class InitStage extends StageBase{

	public InitStage() {
		
	}
	
	public InitStage(Stage next) {
		super(next);
		
	}
	
	@Override
	public void run(Set<Module> modules) {
		
		c2: while(true) {
			
			for(Module module : modules) {
				
				if(module == PlamesBootloader.CORE) continue;
				
				if(module.getStatus() != ModuleStatus.ACTIVE) continue;
				
				try {
					
					PlamesBootloader.LOGGER.info("Module "+module.getName()+"#"+module.getVersion()+" initialization begin!");
					
					module.init();
					
					PlamesBootloader.LOGGER.info("Module "+module.getName()+"#"+module.getVersion()+" successful initialized!");
				}
				catch(Exception e) {
					
					e.printStackTrace();
				
					modules.remove(module);
					continue c2;
				}
			}
			
			break;
		}
		
		PlamesBootloader.LOGGER.info("Initialization stage complete!");
		
		nextStage.run(modules);
	}
}