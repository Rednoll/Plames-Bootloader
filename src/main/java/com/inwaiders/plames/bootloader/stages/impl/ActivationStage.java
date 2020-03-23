package com.inwaiders.plames.bootloader.stages.impl;

import java.util.Set;

import com.inwaiders.plames.api.module.Module;
import com.inwaiders.plames.api.module.ModuleStatus;
import com.inwaiders.plames.bootloader.stages.Stage;
import com.inwaiders.plames.bootloader.stages.StageBase;

public class ActivationStage extends StageBase{

	public ActivationStage() {
		
	}
	
	public ActivationStage(Stage next) {
		super(next);
		
	}
	
	@Override
	public void run(Set<Module> modules) {
	
		for(Module module : modules) {
			
			if(module.getStatus() == ModuleStatus.AWAITING_ON) {
				
				module.setStatus(ModuleStatus.ACTIVE);
			}
			
			if(module.getStatus() == ModuleStatus.AWAITING_OFF) {
				
				module.setStatus(ModuleStatus.INACTIVE);
			}
		}
		
		nextStage.run(modules);
	}
}
