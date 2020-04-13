package enterprises.inwaiders.plames.bootloader.stages.license;

import enterprises.inwaiders.plames.PlamesBootloader;
import enterprises.inwaiders.plames.api.module.Module;

public enum LicenseTestResult {
	
	FALSE, STANDARD, TRUE;
	
	public void log(Module module) {
		
		PlamesBootloader.LOGGER.warn("Module "+module.getName()+"#"+module.getId()+" verification error! Unknown verification key! - "+this.toString());
	}
	
	public boolean isBad() {
		
		return !isGood();
	}
	
	public boolean isGood() {
		
		if(this == TRUE) {
			
			return true;
		}
		
		if(this == STANDARD) {
			
			return true;
		}
		
		if(this == FALSE) {
			
			return false;
		}
		
		return false;
	}
}