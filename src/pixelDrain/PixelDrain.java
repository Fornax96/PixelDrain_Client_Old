package pixelDrain;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class PixelDrain {
	public static void main(String[] args) throws Exception{
		GUI.notify("", 0);
		
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("GTK".equals(info.getName())) {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
				}else{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
			e.printStackTrace();
		}
		
		
		trayMenu tray = new trayMenu();
		tray.create();
		
		GUI.optionFrame();
		
		try {
            GlobalScreen.registerNativeHook();
	    }
	    catch (NativeHookException ex) {
	            System.err.println("It is not my fault");
	            System.err.println(ex.getMessage());
	    }
	
	    //Create the key listener
	    GlobalScreen.getInstance().addNativeKeyListener(new keyListener());
	    
	    while(true){
	    	Thread.sleep(60000);
	    }
	}
}