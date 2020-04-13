package enterprises.inwaiders.plames.bootloader.stages.license;

import enterprises.inwaiders.plames.api.module.Module;

public interface LicenseTestStrategy {

	public LicenseTestResult verificate(Module module);
}
