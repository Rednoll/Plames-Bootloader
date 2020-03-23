package com.inwaiders.plames.bootloader.stages.license;

import com.inwaiders.plames.api.module.Module;

public interface LicenseTestStrategy {

	public LicenseTestResult verificate(Module module);
}
