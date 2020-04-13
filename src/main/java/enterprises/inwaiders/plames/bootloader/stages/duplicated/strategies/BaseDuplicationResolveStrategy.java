package enterprises.inwaiders.plames.bootloader.stages.duplicated.strategies;

import enterprises.inwaiders.plames.api.module.Module;
import enterprises.inwaiders.plames.bootloader.stages.duplicated.DuplicationResolveStrategy;

public class BaseDuplicationResolveStrategy implements DuplicationResolveStrategy{

	public Module pickModule(Module a, Module b) {
		
		if(a.getSystemVersion() < 0 || b.getSystemVersion() < 0) return null;
		
		if(a.getSystemVersion() == b.getSystemVersion()) return a;
		
		return a.getSystemVersion() > b.getSystemVersion() ? a : b;
	}
	
	public Module toRemove(Module a, Module b) {
		
		Module pick = pickModule(a, b);
		
		if(pick == null) {
			
			return null;
		}
		else {
		
			return pick == a ? b : a;
		}
	}
}
