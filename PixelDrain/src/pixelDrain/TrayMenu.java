package pixelDrain;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TrayMenu{
	public void create() throws IOException{
		
		//Check the SystemTray support
		if (!SystemTray.isSupported()) {
			System.out.println("SystemTray is not supported");
			return;
		}
		Image trayImage = null;
		TrayIcon trayIcon = null;
		if(PixelDrain.system.contains("Windows")){
			trayImage = ImageIO.read(new File("res/tray16.png"));
			trayIcon = new TrayIcon(trayImage, "PixelDrain", null);
		}else{
			trayImage = ImageIO.read(new File("res/tray32.png"));
			trayIcon = new TrayIcon(trayImage, "PixelDrain", null);
			trayIcon.setImageAutoSize(true);
		}
		
		final SystemTray tray = SystemTray.getSystemTray();
		
		//
		
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println("TrayIcon could not be added.");
			return;
		}
		
		trayIcon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUI.frame.setVisible(true);
			}
		});
	}
}
