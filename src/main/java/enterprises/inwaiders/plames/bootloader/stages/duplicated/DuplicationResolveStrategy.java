package enterprises.inwaiders.plames.bootloader.stages.duplicated;

import enterprises.inwaiders.plames.api.module.Module;

public interface DuplicationResolveStrategy {

	public Module pickModule(Module a, Module b);
	public Module toRemove(Module a, Module b);
}
