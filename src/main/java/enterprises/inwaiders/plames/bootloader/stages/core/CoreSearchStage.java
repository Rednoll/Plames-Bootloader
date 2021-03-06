package enterprises.inwaiders.plames.bootloader.stages.core;

import java.util.Set;

import enterprises.inwaiders.plames.PlamesBootloader;
import enterprises.inwaiders.plames.api.module.Module;
import enterprises.inwaiders.plames.bootloader.LoadErrorException;
import enterprises.inwaiders.plames.bootloader.stages.Stage;
import enterprises.inwaiders.plames.bootloader.stages.StageBase;

public class CoreSearchStage extends StageBase{

	public CoreSearchStage(Stage next) {
		super(next);
		
	}
	
	@Override
	public void run(Set<Module> modules) {
		
		c2: for(Module module : modules) {
			
			if(module.getType().equalsIgnoreCase("core")) {
			
				PlamesBootloader.CORE = module;
				break c2;
			}
		}
		
		if(PlamesBootloader.CORE == null) {
			
			throw new LoadErrorException("Core not found!");
		}
		else {
			
			PlamesBootloader.LOGGER.info("Found core: "+PlamesBootloader.CORE.getName()+"#"+PlamesBootloader.CORE.getVersion());
			
			nextStage.run(modules);
		}
	}
}
