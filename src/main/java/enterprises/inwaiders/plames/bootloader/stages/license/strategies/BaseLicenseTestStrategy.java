package enterprises.inwaiders.plames.bootloader.stages.license.strategies;

import enterprises.inwaiders.plames.api.module.Module;
import enterprises.inwaiders.plames.bootloader.stages.license.LicenseTestResult;
import enterprises.inwaiders.plames.bootloader.stages.license.LicenseTestStrategy;

public class BaseLicenseTestStrategy implements LicenseTestStrategy{

	@Override
	public LicenseTestResult verificate(Module module) {
		
		return LicenseTestResult.STANDARD;
	}
}
