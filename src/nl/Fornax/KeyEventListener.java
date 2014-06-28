package nl.Fornax;

import java.io.IOException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyEventListener implements NativeKeyListener{
	public static int KEY1 = 17;
	public static int KEY2 = 18;
	public static int KEY3_FULLSCREEN = 49;
	public static int KEY4_CROPPED = 50;
	public static int KEY5_WEBCAM = 51;
	public static int KEY6_INTERFACE = 52;
	public static int LAST_KEY = 0;
	
	public static boolean KEY1_TYPED = false;
	public static boolean KEY2_TYPED = false;
	public static boolean MODE1_TYPED = false;
	public static boolean MODE2_TYPED = false;
	public static boolean MODE3_TYPED = false;
	public static boolean MODE4_TYPED = false;
	
	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		//System.out.println(e.getKeyCode());
		LAST_KEY = e.getKeyCode();
		
		if (e.getKeyCode() == KEY1) {
			KEY1_TYPED = true;
		}
		if(e.getKeyCode() == KEY2){
			KEY2_TYPED = true;
        }
		if(e.getKeyCode() == KEY3_FULLSCREEN){
			MODE1_TYPED = true;
        }
		if(e.getKeyCode() == KEY4_CROPPED){
			MODE2_TYPED = true;
        }
		if(e.getKeyCode() == KEY5_WEBCAM){
			MODE3_TYPED = true;
        }
		if(e.getKeyCode() == KEY6_INTERFACE){
			MODE4_TYPED = true;
        }
		combinationChecker();
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		//ystem.out.println(e.getKeyCode());
		
		if (e.getKeyCode() == KEY1) {
			KEY1_TYPED = false;
        }
		if(e.getKeyCode() == KEY2){
			KEY2_TYPED = false;
        }
		if(e.getKeyCode() == KEY3_FULLSCREEN){
			MODE1_TYPED = false;
        }
		if(e.getKeyCode() == KEY4_CROPPED){
			MODE2_TYPED = false;
        }
		if(e.getKeyCode() == KEY5_WEBCAM){
			MODE3_TYPED = false;
        }
		if(e.getKeyCode() == KEY6_INTERFACE){
			MODE4_TYPED = false;
        }
	}
	
	public void combinationChecker(){
		if(KeyEventListener.KEY1_TYPED && KeyEventListener.KEY2_TYPED && KeyEventListener.MODE1_TYPED){
			try {
				RunProgram.fullScreen();
			} catch (IOException e) {
				System.out.println("Could not take screenshot: something went wrong :(");
				e.printStackTrace();
			}
		}
		if(KeyEventListener.KEY1_TYPED && KeyEventListener.KEY2_TYPED && KeyEventListener.MODE2_TYPED){
			try {
				RunProgram.cropped();
			} catch (Exception e) {
				System.out.println("Could not take screenshot: something went wrong :(");
				e.printStackTrace();
			}
		}
		if(KeyEventListener.KEY1_TYPED && KeyEventListener.KEY2_TYPED && KeyEventListener.MODE3_TYPED){
			try {
				RunProgram.webcam();
			} catch (Exception e) {
				System.out.println("Could not take screenshot: something went wrong :(");
				e.printStackTrace();
			}
		}
		if(KeyEventListener.KEY1_TYPED && KeyEventListener.KEY2_TYPED && KeyEventListener.MODE4_TYPED){
			try {
				GUI.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}