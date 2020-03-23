package com.inwaiders.plames.bootloader.stages.license.strategies;

import com.inwaiders.plames.api.module.Module;
import com.inwaiders.plames.bootloader.stages.license.LicenseTestResult;
import com.inwaiders.plames.bootloader.stages.license.LicenseTestStrategy;

public class BaseLicenseTestStrategy implements LicenseTestStrategy{

	@Override
	public LicenseTestResult verificate(Module module) {
		
		return LicenseTestResult.STANDARD;
	}
}
