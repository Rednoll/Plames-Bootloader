package enterprises.inwaiders.plames.bootloader.stages.impl;

import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import enterprises.inwaiders.plames.PlamesBootloader;
import enterprises.inwaiders.plames.api.module.Module;
import enterprises.inwaiders.plames.api.module.ModuleStatus;
import enterprises.inwaiders.plames.bootloader.stages.Stage;
import enterprises.inwaiders.plames.bootloader.stages.StageBase;

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