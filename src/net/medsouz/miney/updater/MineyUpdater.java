package net.medsouz.miney.updater;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "Miney", name = "Miney", version = "0.1")
public class MineyUpdater {
	@Instance
	public static MineyUpdater instance;

	public static UpdaterThread updater;
	public static boolean updateFailed = false;
	
	@Init
	public void init(FMLInitializationEvent evt) {
		TickRegistry.registerTickHandler(new UpdateCheckTick(), Side.CLIENT);
		TickRegistry.registerTickHandler(new RenderTick(), Side.CLIENT);
		String localVer = VersionManager.checkLocalMineyVersion();
		String remoteVer = VersionManager.checkRemoteMineyVersion();
		File client = new File(Minecraft.getMinecraftDir(),"Miney/client.zip");
		if(!remoteVer.equals("null")){
			if(localVer.equals(remoteVer)){
				MineyUpdater.loadClient();
			}else{
				updater = new UpdaterThread(client, VersionManager.clientURL);
				Thread updateThread = new Thread(updater);
				updateThread.setName("Miney Updater");
				updateThread.start();
			}
		}else{
			updateFailed = true;
		}
	}
	
	public static void loadClient(){
		//TODO: fancy classloader stuff to make client.zip execute
	}
}
