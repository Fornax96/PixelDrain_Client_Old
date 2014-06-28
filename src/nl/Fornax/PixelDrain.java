package nl.Fornax;

import java.io.File;
import javax.swing.UIManager;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class PixelDrain {
	public static String system = System.getProperty("os.name");
	public static String java_version = System.getProperty("java.version");
	
	public static void main(String[] args) throws Exception{
		System.out.println("Running on system: " + system);
		System.out.println("Running JVM version: " + java_version);
		
		File uploadDir = new File("uploads");
		// if the directory does not exist, create it
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		//UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		// Sadly, UIManager.getSystemLookAndFeelClassName() will never return GTK
		
		TrayMenu tray = new TrayMenu();
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
	    GlobalScreen.getInstance().addNativeKeyListener(new KeyEventListener());
	    
	    /*GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] gs = ge.getScreenDevices();
	    for(GraphicsDevice curGs : gs)
	    {
	          GraphicsConfiguration[] gc = curGs.getConfigurations();
	          for(GraphicsConfiguration curGc : gc)
	          {
	                Rectangle bounds = curGc.getBounds();

	                System.out.println(bounds.getX() + "," + bounds.getY() + " " + bounds.getWidth() + "x" + bounds.getHeight());
	          }
	     }*/
	    
	    while(true){
	    	Thread.sleep(60000);
	    }
	}
}