package com.inwaiders.plames.bootloader.stages.license;

import com.inwaiders.plames.PlamesBootloader;
import com.inwaiders.plames.api.module.Module;

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