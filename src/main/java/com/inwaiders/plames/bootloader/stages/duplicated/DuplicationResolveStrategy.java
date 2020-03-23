package com.inwaiders.plames.bootloader.stages.duplicated;

import com.inwaiders.plames.api.module.Module;

public interface DuplicationResolveStrategy {

	public Module pickModule(Module a, Module b);
	public Module toRemove(Module a, Module b);
}
