package net.medsouz.miney.updater;

import java.util.EnumSet;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class UpdateCheckTick implements ITickHandler{

	public boolean completed = false;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if(MineyUpdater.updater != null){
			if(!MineyUpdater.updater.isUpdating){
				if(!MineyUpdater.updater.failed){
					MineyUpdater.updater = null;
					VersionManager.setLocalMineyVersion();
					MineyUpdater.loadClient();
				}else{
					MineyUpdater.updateFailed = true;
				}
				completed = true;
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		if(!completed){
			return EnumSet.of(TickType.CLIENT);
		}else{
			return null;
		}
	}

	@Override
	public String getLabel() {
		return "Miney Update checker";
	}
	
}
