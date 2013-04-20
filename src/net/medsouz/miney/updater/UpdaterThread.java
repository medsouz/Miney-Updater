package net.medsouz.miney.updater;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdaterThread implements Runnable{

	public boolean isUpdating = true;
	public boolean failed = true;
	
	private File fileoutput;
	private String fileurl;
	
	public UpdaterThread(File out, String url){
		fileoutput = out;
		fileurl = url;
	}
	
	@Override
	public void run() {
		try {
			BufferedInputStream in = new BufferedInputStream(new URL(fileurl).openStream());
			FileOutputStream out = new FileOutputStream(fileoutput);
			byte buffer[] = new byte[1024];
			int line;
			while ((line = in.read(buffer, 0, 1024)) != -1){
				out.write(buffer, 0, line);
			}
			in.close();
			out.close();
			failed = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		isUpdating = false;
	}

}
