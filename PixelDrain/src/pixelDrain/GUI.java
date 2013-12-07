package pixelDrain;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.iharder.dnd.FileDrop;

import org.apache.commons.io.FilenameUtils;
import org.jnativehook.keyboard.NativeKeyEvent;

public class GUI{
	
	static JButton key1Btn = new JButton();
	static JButton key2Btn = new JButton();
	static JButton key3Btn = new JButton();
	static JButton key4Btn = new JButton();
	static JButton key5Btn = new JButton();
	static JButton key6Btn = new JButton();
	static JCheckBox chkBrowser = new JCheckBox();
	
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
				+ "'CTRL + ALT + 3' Takes a photo with your webcam (If you have one).<br><br>"
				+ "'CTRL + ALT + 4' Opens the options window, just like clicking the tray icon.<br><br>"
				+ "To select an area for cropping, drag your mouse from one corner to another.</html>";
		
		//Initializing the objects
		final JPanel menuPanel = new JPanel();
		menuPanel.setBounds(0, 0, 300, 400);
		
			final JLabel titleLabel = new JLabel("Menu:");
			titleLabel.setBounds(10, 5, 280, 20);
			final JButton settingsBtn = new JButton("Edit settings");
			settingsBtn.setBounds(10, 30, 280, 30);
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
		menuPanel.add(settingsBtn);
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
		
		final JPanel settingsPanel = new JPanel();
		settingsPanel.setBounds(0, 0, 300, 400);

			settingsPanel.setLayout(null);
			key1Btn = new JButton("Key 1: " + NativeKeyEvent.getKeyText(KeyEventListener.KEY1));
			key1Btn.setBounds(10, 10, 135, 30);
			key2Btn = new JButton("Key 2: " + NativeKeyEvent.getKeyText(KeyEventListener.KEY2));
			key2Btn.setBounds(155, 10, 135, 30);
			key3Btn = new JButton("Fullscreen: " + NativeKeyEvent.getKeyText(KeyEventListener.KEY3_FULLSCREEN));
			key3Btn.setBounds(10, 50, 280, 30);
			key4Btn = new JButton("Cropped: " + NativeKeyEvent.getKeyText(KeyEventListener.KEY4_CROPPED));
			key4Btn.setBounds(10, 80, 280, 30);
			key5Btn = new JButton("Webcam: " + NativeKeyEvent.getKeyText(KeyEventListener.KEY5_WEBCAM));
			key5Btn.setBounds(10, 110, 280, 30);
			key6Btn = new JButton("Interface: " + NativeKeyEvent.getKeyText(KeyEventListener.KEY6_INTERFACE));
			key6Btn.setBounds(10, 140, 280, 30);
			chkBrowser = new JCheckBox("Open browser after upload", false);
			chkBrowser.setBounds(10, 180, 280, 30);
			
			final JButton settingsBackBtn = new JButton("Back");
			settingsBackBtn.setBounds(10, 330, 280, 30);
		
		settingsPanel.add(key1Btn);
		settingsPanel.add(key2Btn);
		settingsPanel.add(key3Btn);
		settingsPanel.add(key4Btn);
		settingsPanel.add(key5Btn);
		settingsPanel.add(key6Btn);
		settingsPanel.add(chkBrowser);
		settingsPanel.add(settingsBackBtn);
		
		//Setting properties
		new FileDrop(dropLabel, BorderFactory.createEmptyBorder(),  new FileDrop.Listener(){
			@Override
			public void filesDropped(File[] files) {
				try {
					System.out.println(files[0].toString());
					System.out.println(FilenameUtils.getExtension(files[0].toString()));
					String directLink = UploadFile.upload(files[0], FilenameUtils.getExtension(files[0].toString()));
					RunProgram.copyToClipboard(directLink);
					
				} catch (Exception e) {
					PopupWindow.notify("Whatever you tried to do does not work, please don't try it again", 10000);
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
		settingsBackBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.add(menuPanel);
				frame.remove(settingsPanel);
				frame.repaint();
			}
		});
		settingsBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.remove(menuPanel);
				frame.add(settingsPanel);
				frame.repaint();
			}
		});
		
		key1Btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				editKeys(key1Btn, 1, "Key 1: ");
			}
		});
		key2Btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				editKeys(key2Btn, 2, "Key 2: ");
			}
		});
		key3Btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				editKeys(key3Btn, 3, "Fullscreen: ");
			}
		});
		key4Btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				editKeys(key4Btn, 4, "Cropped: ");
			}
		});
		key5Btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				editKeys(key5Btn, 5, "Webcam: ");
			}
		});
		key6Btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				editKeys(key5Btn, 6, "Interface: ");
			}
		});
		
		chkBrowser.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("chkBrowser: " + chkBrowser.isSelected());
				Config.openBrowser = chkBrowser.isSelected();
			}
		});
		
	}
	
	public static void editKeys(JButton keyBtn, int keyid, String btnText){
		int keySetting = 0;
		
		KeyEventListener.LAST_KEY = 0;
		keyBtn.setText("PRESS A KEY");
		frame.repaint();
		
		while(KeyEventListener.LAST_KEY == 0){
			try{Thread.sleep(100);}catch(InterruptedException ex){}
		}
		
		keySetting = KeyEventListener.LAST_KEY;
		keyBtn.setText(btnText + NativeKeyEvent.getKeyText(KeyEventListener.LAST_KEY));
		System.out.println(keySetting);

		if(keyid == 1){KeyEventListener.KEY1 = keySetting;}
		else if(keyid == 2){KeyEventListener.KEY2 = keySetting;}
		else if(keyid == 3){KeyEventListener.KEY3_FULLSCREEN = keySetting;}
		else if(keyid == 4){KeyEventListener.KEY4_CROPPED = keySetting;}
		else if(keyid == 5){KeyEventListener.KEY5_WEBCAM = keySetting;}
		else if(keyid == 6){KeyEventListener.KEY6_INTERFACE = keySetting;}
		
		frame.repaint();
	}
	
	public static String keys[];
	
	public static String localizeKeys(int key){
		
		
		return keys[key];
	}
}