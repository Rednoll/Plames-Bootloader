package enterprises.inwaiders.plames.bootloader.stages;

import java.util.Set;

import enterprises.inwaiders.plames.api.module.Module;

public interface Stage {

	public void run(Set<Module> modules);
	
	public void setNext(Stage stage);
}
