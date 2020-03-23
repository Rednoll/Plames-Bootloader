package com.inwaiders.plames.bootloader.stages.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

import com.inwaiders.plames.PlamesBootloader;
import com.inwaiders.plames.api.application.ApplicationAgent;
import com.inwaiders.plames.api.application.ApplicationAgentRegistry;
import com.inwaiders.plames.api.module.Module;
import com.inwaiders.plames.bootloader.stages.Stage;
import com.inwaiders.plames.bootloader.stages.StageBase;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

public class ApplicationRegistrationStage extends StageBase{

	public ApplicationRegistrationStage() {
		
	}
	
	public ApplicationRegistrationStage(Stage next) {
		super(next);
		
	}
	
	@Override
	public void run(Set<Module> modules) {	
		
		ScanResult scanResult = new ClassGraph().enableAllInfo().scan();
		ClassInfoList idataClasses = scanResult.getClassesImplementing(ApplicationAgent.class.getCanonicalName());
		List<Class<?>> applicationAgentsClasses = idataClasses.loadClasses();
		
		for(Class<?> clazz : applicationAgentsClasses) {
			
			if(Modifier.isAbstract(clazz.getModifiers())) continue;
			
			try {
				
				Method method = clazz.getDeclaredMethod("getInstance");
			
				ApplicationAgent agent = (ApplicationAgent) method.invoke(null);
				
				PlamesBootloader.LOGGER.info("Found application agent "+agent.getDisplayName()+"["+agent.getTag()+"]");
				
				ApplicationAgentRegistry.register(agent);
			}
			catch(Exception e) {
				
				e.printStackTrace();
			}
		}
		
		PlamesBootloader.LOGGER.info("Load modules files stage complete!");
		
		nextStage.run(modules);
	}
}
