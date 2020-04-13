package enterprises.inwaiders.plames.bootloader.stages.core;

import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import enterprises.inwaiders.plames.PlamesBootloader;
import enterprises.inwaiders.plames.api.module.Module;
import enterprises.inwaiders.plames.bootloader.stages.Stage;
import enterprises.inwaiders.plames.bootloader.stages.StageBase;

@Component("CoreInitStage")
@Scope("prototype")
public class CoreInitStage extends StageBase{

	public CoreInitStage() {
		
	}
	
	public CoreInitStage(Stage next) {
		super(next);
		
	}
	
	@Override
	public void run(Set<Module> modules) {
		
		PlamesBootloader.CORE.init();
		
		PlamesBootloader.LOGGER.info("Core "+PlamesBootloader.CORE.getName()+"#"+PlamesBootloader.CORE.getVersion()+" successful initialized!");
		
		nextStage.run(modules);
	}
}