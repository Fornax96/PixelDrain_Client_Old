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

public class trayMenu{
	public void create() throws IOException{
		
		//Check the SystemTray support
		if (!SystemTray.isSupported()) {
			System.out.println("SystemTray is not supported");
			return;
		}
		
		Image trayImage = ImageIO.read(new File("res/tray.png"));
		TrayIcon trayIcon = new TrayIcon(trayImage, "PixelDrain", null);
		
		final SystemTray tray = SystemTray.getSystemTray();
		
		trayIcon.setImageAutoSize(true);
		
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
