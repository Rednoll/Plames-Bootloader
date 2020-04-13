package enterprises.inwaiders.plames.bootloader.stages.impl;

import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import enterprises.inwaiders.plames.PlamesBootloader;
import enterprises.inwaiders.plames.api.module.Module;
import enterprises.inwaiders.plames.api.module.ModuleStatus;
import enterprises.inwaiders.plames.bootloader.stages.Stage;
import enterprises.inwaiders.plames.bootloader.stages.StageBase;

@Component("PostInitStage")
@Scope("prototype")
public class PostInitStage extends StageBase{

	public PostInitStage() {
		
	}
	
	public PostInitStage(Stage next) {
		super(next);
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void run(Set<Module> modules) {
	
		c2: while(true) {
			
			for(Module module : modules) {
				
				if(module == PlamesBootloader.CORE) continue;
				
				if(module.getStatus() != ModuleStatus.ACTIVE) continue;
				
				try {
					
					PlamesBootloader.LOGGER.info("Module "+module.getName()+"#"+module.getVersion()+" postinitialization begin!");
					
					module.postInit();
					
					PlamesBootloader.LOGGER.info("Module "+module.getName()+"#"+module.getVersion()+" successful postinitialized!");
				}
				catch(Exception e) {
					
					e.printStackTrace();
				
					modules.remove(module);
					continue c2;
				}
			}
			
			break;
		}
		
		PlamesBootloader.LOGGER.info("Post initialization stage complete!");
		
		nextStage.run(modules);
	}
}