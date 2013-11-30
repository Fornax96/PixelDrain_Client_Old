package pixelDrain;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.iharder.dnd.FileDrop;

import org.apache.commons.io.FilenameUtils;

public class GUI{
	
	//Global user settings
	public static boolean showHintWhileCropping = true;
	
	public static JFrame frame = new JFrame();
	public static ImageIcon icon = new ImageIcon("res/tray32.png");
	
	public static void optionFrame(){
		frame.setSize(300, 400);
		frame.setResizable(false);
		frame.setBackground(Color.BLACK);
		frame.setTitle("PixelDrain");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setLayout(null);
		frame.setIconImage(icon.getImage());
		
		//Instructions
		String instructions = "<html>Instructions:<br><br>"
				+ "'CTRL + ALT + 1' Takes a screenshot of your whole screen.<br><br>"
				+ "'CTRL + ALT + 2' Lets you crop a screenshot before uploading.<br><br>"
				+ "'CTRL + ALT + 3' Opens the options window, just like clicking the tray icon.<br><br>"
				+ "To select an area for cropping, drag your mouse from one corner to another.</html>";
		
		//Initializing the objects
		final JPanel menuPanel = new JPanel();
		menuPanel.setBounds(0, 0, 300, 400);
		
			final JLabel titleLabel = new JLabel("Menu:");
			titleLabel.setBounds(10, 5, 280, 20);
			final JButton keysBtn = new JButton("Edit keybindings");
			keysBtn.setBounds(10, 30, 280, 30);
			final JButton helpBtn = new JButton("Instructions");
			helpBtn.setBounds(10, 70, 280, 30);
			JLabel dropLabelText = new JLabel("Drop file here for direct sharing:", JLabel.CENTER);
			dropLabelText.setBounds(10, 100, 280, 20);
			JLabel dropLabel = null;
			try {
				dropLabel = new JLabel(new ImageIcon(ImageIO.read(new File("res/dropTexture.png"))));
			} catch (IOException e1) {e1.printStackTrace();}
			dropLabel.setBounds(50, 120, 200, 200);
			final JButton exitBtn = new JButton("Close PixelDrain");
			exitBtn.setBounds(10, 330, 280, 30);
			
		if (PixelDrain.java_version.startsWith("1.8")) {
			dropLabelText = new JLabel("<html>Drag and drop upload is not supported by Java 1.8 (which you're using), and has been disabled</html>");
			dropLabelText.setBounds(10, 100, 280, 60);
		}else{
			menuPanel.add(dropLabel);
		}
		menuPanel.setLayout(null);
		menuPanel.add(titleLabel);
		menuPanel.add(keysBtn);
		menuPanel.add(helpBtn);
		menuPanel.add(dropLabelText);
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
		new FileDrop(dropLabel, false,  new FileDrop.Listener(){
			@Override
			public void filesDropped(File[] files) {
				try {
					System.out.println(files[0].toString());
					System.out.println(FilenameUtils.getExtension(files[0].toString()));
					String directLink = "Your screenshot has not been uploaded, try again";
					directLink = uploadFile.upload(files[0], FilenameUtils.getExtension(files[0].toString()));
					runProgram.copyToClipboard(directLink, "Your file has been copied to your clipboard,<br>Press 'CTRL + V' to paste");
					
				} catch (Exception e) {
					notification.notify("Whatever you tried to do does not work, please don't try it again", 10000);
					e.printStackTrace();
				}
			}
			
			
		});
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
		
	}
}