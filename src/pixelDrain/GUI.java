package pixelDrain;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import javax.swing.UIManager;

public class GUI{
	
	public static JFrame frame = new JFrame();
	
	@SuppressWarnings("serial")
	public static void optionFrame(){
		frame.setSize(300, 400);
		frame.setResizable(false);
	    frame.setBackground(Color.BLACK);
	    frame.setTitle("PixelDrain");
	    frame.setLocationRelativeTo(null);
	    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	    frame.setLayout(null);
	    
	    //Instructions
	    String instructions = "<html>Instructions:<br><br>"
	    		+ "To make a screenshot of your whole screen, press: 'CTRL + ALT + 1'<br><br>"
	    		+ "To make a screenshot of a cropped area of your screen, press: 'CTRL + ALT + 2'<br><br>"
	    		+ "To select an area for cropping, click and hold your left mouse button on the top-left corner of the area, and drag your mouse to the bottom-right corner of the area.<br>"
	    		+ "everything between those two points will be uploaded and put on your clipboard.</html>";
	    
	    //Initializing the objects
	    final JPanel menuPanel = new JPanel();
	    menuPanel.setBounds(0, 0, 300, 400);
	    
		    final JLabel titleLabel = new JLabel("Menu:");
		    titleLabel.setBounds(10, 5, 280, 20);
			final JButton keysBtn = new JButton("Edit keybindings");
			keysBtn.setBounds(10, 30, 280, 30);
			final JButton helpBtn = new JButton("Instructions");
			helpBtn.setBounds(10, 70, 280, 30);
		    final JLabel dropLabel = new JLabel("");
		    dropLabel.setBounds(10, 110, 280, 30);
			final JButton exitBtn = new JButton("Close PixelDrain");
			exitBtn.setBounds(10, 330, 280, 30);
		
		menuPanel.setLayout(null);
		menuPanel.add(titleLabel);
		menuPanel.add(keysBtn);
		menuPanel.add(helpBtn);
		menuPanel.add(dropLabel);
		menuPanel.add(exitBtn);
		
		final JPanel helpPanel = new JPanel();
	    helpPanel.setBounds(0, 0, 300, 400);
	    
			final JLabel helpLabel = new JLabel(instructions);
		    helpLabel.setBounds(10, 5, 280, 300);
			final JButton helpBackBtn = new JButton("Back");
			helpBackBtn.setBounds(10, 330, 280, 30);
			
		helpPanel.setLayout(null);
		helpPanel.add(helpLabel);
		helpPanel.add(helpBackBtn);
		
		final JPanel keysPanel = new JPanel();
		keysPanel.setBounds(0, 0, 300, 400);
		
			final JButton key1Btn = new JButton("Key 1: " + keyListener.KEY1);
			key1Btn.setBounds(10, 10, 135, 30);
			final JButton key2Btn = new JButton("Key 2: " + keyListener.KEY2);
			key2Btn.setBounds(155, 10, 135, 30);
			final JButton key3Btn = new JButton("Fullscreen: " + keyListener.KEY3_FULLSCREEN);
			key3Btn.setBounds(10, 50, 280, 30);
			final JButton key4Btn = new JButton("Cropped: " + keyListener.KEY4_CROPPED);
			key4Btn.setBounds(10, 80, 280, 30);
			final JButton keysBackBtn = new JButton("Back");
			keysBackBtn.setBounds(10, 330, 280, 30);
		
		keysPanel.add(key1Btn);
		keysPanel.add(key2Btn);
		keysPanel.add(key3Btn);
		keysPanel.add(key4Btn);
		keysPanel.add(keysBackBtn);
		
		//Setting properties
		frame.setDropTarget(new DropTarget());
	    
	    //Adding the objects
		frame.add(menuPanel);
	    
	    frame.repaint();
	    
	    exitBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
	    });
	    helpBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.remove(menuPanel);
				frame.add(helpPanel);
				frame.repaint();
			}
	    });
	    helpBackBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.add(menuPanel);
				frame.remove(helpPanel);
				frame.repaint();
			}
		});
	    keysBackBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.add(menuPanel);
				frame.remove(keysPanel);
				frame.repaint();
			}
		});
	    keysBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.remove(menuPanel);
				frame.add(keysPanel);
				frame.repaint();
			}
		});
	    
	    key1Btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				/*keyListener.LAST_KEY = 0;
				GUI.notify("Press a key", 3000);
				
				while(keyListener.LAST_KEY == 0){
					try{Thread.sleep(100);}catch(InterruptedException ex){}
				}
				keyListener.KEY1 = keyListener.LAST_KEY;
				//key1Btn = new JButton("Key 1: " + keyListener.KEY1);
				frame.repaint();*/
			}
		});
		frame.setTransferHandler(new TransferHandler(){
			public boolean importData(TransferSupport ts) {
				System.out.println("File dropped!: " + ts);
				return false;

			}
		});
	}
	static JFrame popup = new JFrame();
	public static void notify(final String text, final int time){
		if(time == 0){
			try {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Toolkit tk = Toolkit.getDefaultToolkit();  
			int width = ((int) tk.getScreenSize().getWidth());
			
			JFrame.setDefaultLookAndFeelDecorated(true);
			
			popup.setSize(300, 80);
			popup.setUndecorated(true);
			popup.setResizable(false);
			popup.setBackground(new Color(0.9F, 0.9F, 0.9F, 1.0F));
			popup.setTitle("Notification");
			popup.setLocation(width - 350, 50);
			popup.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			popup.setLayout(null);
			popup.setAlwaysOnTop(true);
			popup.setVisible(false);
			return;
		}
		
		Thread notifyThread = new Thread(){
			public void run(){
				JLabel label = new JLabel("<html><font size='5' color='#AAAAAA'>" + text + "</font></html>");
				label.setBounds(5, 5, 290, 70);
				JLabel shadow = new JLabel("<html><font size='5' color='#111111'>" + text + "</font></html>");
				shadow.setBounds(6, 6, 290, 70);
				
				popup.add(label);
				popup.add(shadow);
				
				popup.repaint();
				
				popup.setVisible(true);
				
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				popup.setVisible(false);
				
				popup = null;
			}
		};
		
		notifyThread.run();
	}
}