package net.medsouz.miney.updater;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import net.minecraft.client.Minecraft;

public class VersionManager {

	public static String clientURL = "http://miney.medsouz.net/latest.zip";
	public static String versionURL = "http://miney.medsouz.net/version.txt";

	public static String checkLocalMineyVersion() {
		String version = "null";
		File versionFile = new File(Minecraft.getMinecraftDir(),"Miney/version.txt");
		System.out.println(versionFile.getAbsolutePath());
		if (versionFile.exists() && versionFile.isFile()) {
			try {
				version = "";
				FileReader v = new FileReader(versionFile);
				char[] buffer = new char[1024];
				int line = 0;
				while ((line = v.read(buffer)) != -1) {
					version = version + String.valueOf(buffer, 0, line);
					buffer = new char[1024];
				}
				v.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return version;
	}

	public static String checkRemoteMineyVersion() {
		String version = "null";
		try {
			URL ver = new URL(versionURL);
			URLConnection con = ver.openConnection();
			con.setConnectTimeout(1000);
			con.setReadTimeout(1000);
			BufferedReader buff = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			version = "";
			while ((line = buff.readLine()) != null) {
				version = version + line;
			}
			buff.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return version;
	}

	public static void setLocalMineyVersion() {
		try {
			BufferedInputStream in = new BufferedInputStream(new URL(versionURL).openStream());
			FileOutputStream out = new FileOutputStream(new File(Minecraft.getMinecraftDir(), "Miney/version.txt"));
			byte buffer[] = new byte[1024];
			int line;
			while ((line = in.read(buffer, 0, 1024)) != -1) {
				out.write(buffer, 0, line);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
