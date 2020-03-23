package com.inwaiders.plames.bootloader.stages;

import java.util.Set;

import com.inwaiders.plames.api.module.Module;

public abstract class StageBase implements Stage{
	
	public static final Stage EMPTY = new EmptyStage();
	
	protected Stage nextStage = EMPTY;
	
	public StageBase() {}
	
	public StageBase(Stage next) {
		
		this.setNext(next);
	}
	
	public void setNext(Stage stage) {
		
		this.nextStage = stage;
	}
	
	private static class EmptyStage extends StageBase {

		@Override
		public void run(Set<Module> modules) {
			
			if(nextStage != null && nextStage != StageBase.EMPTY) {
				
				nextStage.run(modules);
			}
		}
	}
}
