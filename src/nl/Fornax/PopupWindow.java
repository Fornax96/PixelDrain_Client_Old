package nl.Fornax;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class PopupWindow {
	public static void notify(final String text, final int time){
		Thread notifyThread = new Thread(){
			public void run(){
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				JFrame popup = new JFrame();
				
				Toolkit tk = Toolkit.getDefaultToolkit();  
				int width = ((int) tk.getScreenSize().getWidth());
				
				//JFrame.setDefaultLookAndFeelDecorated(true);
				
				popup.setSize(300, 80);
				popup.setUndecorated(true);
				popup.setResizable(false);
				popup.setFocusableWindowState(false);
				popup.getContentPane().setBackground(new Color(0.3F, 0.3F, 0.3F));
				popup.setTitle("Notification");
				popup.setLocation(width - 350, 50);
				popup.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				popup.setLayout(null);
				popup.setAlwaysOnTop(true);
				popup.setVisible(false);
				popup.setIconImage(GUI.icon.getImage());
				popup.setShape(new RoundRectangle2D.Double(0, 0, 300, 80, 20, 20));
				
				JLabel label = new JLabel("<html><font size='5' color='#FFFFFF'>" + text + "</font></html>", JLabel.CENTER);
				
				label.setBounds(5, 5, 290, 70);
				popup.add(label);
				
				popup.repaint();
				popup.setVisible(true);
				
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				popup.setVisible(false);
			}
		};
		notifyThread.start();
		
	}
}
