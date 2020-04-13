package enterprises.inwaiders.plames.bootloader.stages.duplicated;

import java.util.Set;

import enterprises.inwaiders.plames.PlamesBootloader;
import enterprises.inwaiders.plames.api.module.Module;
import enterprises.inwaiders.plames.bootloader.LoadErrorException;
import enterprises.inwaiders.plames.bootloader.stages.Stage;
import enterprises.inwaiders.plames.bootloader.stages.StageBase;

public class DuplicationCheckStage extends StageBase{

	public DuplicationCheckStage() {
		
	}
	
	public DuplicationCheckStage(Stage next) {
		super(next);
		
	}
	
	@Override
	public void run(Set<Module> modules) {
	
		for(Module module : modules) {
			
			for(Module suspect : modules) {
				
				if(module.getId() == suspect.getId() && module != suspect) {
					
					PlamesBootloader.LOGGER.warn("Duplicate modules detected! Module Id: "+module.getId()+" try auto resolve ...");
					
					Module toRemove = PlamesBootloader.DUPLICATION_STRATEGY.toRemove(module, suspect);
				
					if(toRemove == null) {
						
						throw new LoadErrorException("Can't resolve module duplication with id: "+module.getId()+"! Please by hand remove unnecessary version.");
					}
					
					modules.remove(toRemove);
				
					PlamesBootloader.LOGGER.info("Duplicate modules successful resolved, ignore version: "+toRemove.getVersion()+"/"+toRemove.getSystemVersion());
				}
			}
		}
		
		PlamesBootloader.LOGGER.info("Duplication check stage complete!");
		
		nextStage.run(modules);
	}
}
