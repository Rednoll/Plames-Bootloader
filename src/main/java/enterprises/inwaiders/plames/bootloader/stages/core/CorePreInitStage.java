package enterprises.inwaiders.plames.bootloader.stages.core;

import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import enterprises.inwaiders.plames.PlamesBootloader;
import enterprises.inwaiders.plames.api.module.Module;
import enterprises.inwaiders.plames.bootloader.stages.Stage;
import enterprises.inwaiders.plames.bootloader.stages.StageBase;

@Component("CorePreInitStage")
@Scope("prototype")
public class CorePreInitStage extends StageBase{

	public CorePreInitStage() {
		
		
	}
	
	public CorePreInitStage(Stage next) {
		super(next);
		
	}
	
	@Override
	public void run(Set<Module> modules) {
		
		PlamesBootloader.CORE.preInit();
		
		PlamesBootloader.LOGGER.info("Core "+PlamesBootloader.CORE.getName()+"#"+PlamesBootloader.CORE.getVersion()+" successful preinitialized!");
		
		nextStage.run(modules);
	}
}
