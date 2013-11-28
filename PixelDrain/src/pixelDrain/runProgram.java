package pixelDrain;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class runProgram{
	public static void copyToClipboard(String link){
		StringSelection stringSelection = new StringSelection(link);
		Clipboard clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipBoard.setContents(stringSelection, null);
		stringSelection = null;
		
		GUI.notify("Your screenshot has been copied to your clipboard,<br>Press 'CTRL + V' to paste", 6000);
	}
	
	public static void fullScreen() throws IOException{
		try {
			capture.captureScreen();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String directLink = "Your screenshot has not been uploaded, try again";
		directLink = uploadFile.upload(new File("uploads/capture.png"), "image/png");
		copyToClipboard(directLink);
	}
	
	static boolean pressed = false;
	static boolean released = false;
	static boolean canceled = false;
	
	static int mouseXstart = 0;
	static int mouseYstart = 0;
	static int mouseXstop = 0;
	static int mouseYstop = 0;
	
	public static void cropped() throws IOException, InterruptedException{
		try {
			capture.captureScreen();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JFrame cropper = new JFrame("Cropper");
		
		cropper.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		cropper.setUndecorated(true);
		//cropper.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//The setSize way works better on Linux
		Toolkit tk = Toolkit.getDefaultToolkit();  
		final int xSize = ((int) tk.getScreenSize().getWidth());  
		final int ySize = ((int) tk.getScreenSize().getHeight());  
		cropper.setSize(xSize,ySize);
		cropper.setFocusable(true);
		cropper.setLocationRelativeTo(null);
		cropper.setLayout(null);
		
		cropper.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("uploads/capture.png")))));
		
		if(GUI.showHintWhileCropping){
			JLabel titleLabel = new JLabel("<html><font color='yellow'>SELECT AN AREA FOR CROPPING<br>Right click to cancel</font></html>");
			titleLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		    titleLabel.setBounds(20, 20, xSize, 50);
		    JLabel titleLabelShadow = new JLabel("<html><font color='black'>SELECT AN AREA FOR CROPPING<br>Right click to cancel</font></html>");
		    titleLabelShadow.setFont(new Font("Dialog", Font.BOLD, 20));
		    titleLabelShadow.setBounds(22, 22, xSize, 50);
		    
		    //Adding the components
		    cropper.add(titleLabel);
		    cropper.add(titleLabelShadow);
		}
	    
	    //Trying to add an transparent layer, but not succeeding
	   /* cropper.add(new JPanel(){
	    	@Override
	    	public void paint(Graphics g){
	    		super.paint(g);
	    		g.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
	    		g.fillRect(0, 0, xSize, ySize);
	    	}
	    });*/
		
		cropper.repaint();
		cropper.setVisible(true);
		cropper.setAlwaysOnTop(true);
		cropper.requestFocus();
		cropper.toFront();
		
		
		cropper.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				mouseXstart = e.getX();
				mouseYstart = e.getY();
				pressed = true;
				
				if(SwingUtilities.isRightMouseButton(e)){
					canceled = true;
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				mouseXstop = e.getX();
				mouseYstop = e.getY();
				released = true;
				
				if(SwingUtilities.isRightMouseButton(e)){
					canceled = true;
				}
			}
		});
		
		//Wait until the user made his selection
		while(!pressed){
			Thread.sleep(400);
		}
		while(!released){
			Thread.sleep(400);
		}
		
		
		pressed = false;
		released = false;
		
		cropper.setVisible(false);
		cropper = null;
		
		//TODO Attempt at adding a selection area
		/*boolean finished = false;
		JPanel selection = new JPanel();
		selection.setOpaque(true);
		selection.setBounds(0, 0, 0, 0);
		cropper.add(selection);
		
		while(!canceled && !finished){
			
		}*/
		
		//Check if the user actually dragged
		if(mouseXstart == mouseXstop && mouseYstart == mouseYstop){
			GUI.notify("Crop canceled", 5000);
			canceled = false;
			return;
		}
		if(canceled == true){
			GUI.notify("Crop canceled", 5000);
			canceled = false;
			return;
		}
		
		//Start cropping the screenshot!
		int x = Math.min(mouseXstart, mouseXstop), y = Math.min(mouseYstart, mouseYstop), w = Math.abs(mouseXstop - mouseXstart), h = Math.abs(mouseYstop - mouseYstart);
		
		Image unCropped = ImageIO.read(new File("uploads/capture.png"));
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		bi.getGraphics().drawImage(unCropped, 0, 0, w, h, x, y, x + w, y + h, null);
		ImageIO.write(bi, "png", new File("uploads/capture_cropped.png"));
		
		System.out.println("x: " + x + " y: " + y + " w: " + w + " h: " + h);
		
		String directLink = "Your screenshot has not been uploaded, try again";
		directLink = uploadFile.upload(new File("uploads/capture_cropped.png"), "image/png");
		copyToClipboard(directLink);
	}
}
