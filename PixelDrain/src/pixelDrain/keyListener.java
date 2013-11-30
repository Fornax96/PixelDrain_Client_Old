package pixelDrain;

import java.io.IOException;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class keyListener implements NativeKeyListener{
	public static int KEY1 = 17;
	public static int KEY2 = 18;
	public static int KEY3_FULLSCREEN = 49;
	public static int KEY4_CROPPED = 50;
	public static int LAST_KEY = 0;
	
	public static boolean KEY1_TYPED = false;
	public static boolean KEY2_TYPED = false;
	public static boolean MODE1_TYPED = false;
	public static boolean MODE2_TYPED = false;
	public static boolean MODE3_TYPED = false;
	
	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		//System.out.println(e.getKeyCode());
		LAST_KEY = e.getKeyCode();
		
		if (e.getKeyCode() == 17) {
			KEY1_TYPED = true;
		}
		if(e.getKeyCode() == 18){
			KEY2_TYPED = true;
        }
		if(e.getKeyCode() == 49){
			MODE1_TYPED = true;
        }
		if(e.getKeyCode() == 50){
			MODE2_TYPED = true;
        }
		if(e.getKeyCode() == 51){
			MODE3_TYPED = true;
        }
		combinationChecker();
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		//ystem.out.println(e.getKeyCode());
		
		if (e.getKeyCode() == 17) {
			KEY1_TYPED = false;
        }
		if(e.getKeyCode() == 18){
			KEY2_TYPED = false;
        }
		if(e.getKeyCode() == 49){
			MODE1_TYPED = false;
        }
		if(e.getKeyCode() == 50){
			MODE2_TYPED = false;
        }
		if(e.getKeyCode() == 51){
			MODE3_TYPED = false;
        }
	}
	
	public void combinationChecker(){
		if(keyListener.KEY1_TYPED && keyListener.KEY2_TYPED && keyListener.MODE1_TYPED){
			try {
				runProgram.fullScreen();
			} catch (IOException e) {
				System.out.println("Could not take screenshot: something went wrong :(");
				e.printStackTrace();
			}
		}
		if(keyListener.KEY1_TYPED && keyListener.KEY2_TYPED && keyListener.MODE2_TYPED){
			try {
				runProgram.cropped();
			} catch (Exception e) {
				System.out.println("Could not take screenshot: something went wrong :(");
				e.printStackTrace();
			}
		}
		if(keyListener.KEY1_TYPED && keyListener.KEY2_TYPED && keyListener.MODE3_TYPED){
			try {
				GUI.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}