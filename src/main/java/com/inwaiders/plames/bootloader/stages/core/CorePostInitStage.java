package com.inwaiders.plames.bootloader.stages.core;

import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.inwaiders.plames.PlamesBootloader;
import com.inwaiders.plames.api.module.Module;
import com.inwaiders.plames.bootloader.stages.Stage;
import com.inwaiders.plames.bootloader.stages.StageBase;

@Component("CorePostInitStage")
@Scope("prototype")
public class CorePostInitStage extends StageBase{

	public CorePostInitStage() {
		
	}
	
	public CorePostInitStage(Stage next) {
		super(next);
		
	}
	
	@Override
	public void run(Set<Module> modules) {
		
		PlamesBootloader.CORE.postInit();
		
		PlamesBootloader.LOGGER.info("Core "+PlamesBootloader.CORE.getName()+"#"+PlamesBootloader.CORE.getVersion()+" successful postinitialized!");
		
		nextStage.run(modules);
	}
}
