package annotation_tool;

import java.util.HashMap;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;
import javax.imageio.*;

import javax.swing.JComponent;

public class Frame extends JComponent{
	
	// Current Image File
	private Image image;
	// Target Path
	private File path;
	// Graphics2D object
	private Graphics2D g2;
	// Hash map to store coordinates
	private HashMap<Integer, Integer[]> coords = new HashMap<>();
	// Current points counter
	private int points_counter = 0;
	
	// Constructor 
	public Frame() {
		
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				Integer[] temp_arr = {e.getX(), e.getY()};
				coords.put(points_counter, temp_arr);
				points_counter += 1;
				
				if (g2 != null) {
					int x = temp_arr[0]-10;
					int y = temp_arr[1]-10;
					int size = 20;
					g2.fillOval(x,y,size,size);
					
					repaint();
				}
			}
		});
	}
	
	protected void paintComponent(Graphics g) {
		g.drawImage(image,  0,  0,  null);
	};
	
	public void clear()	{
		changeImg(path.toString());
	}
	
	public HashMap<Integer, Integer[]> getCoords() {
		return coords;
	}
	

	public void changeImg(String filepath) {
		try {
			path = new File(filepath);
			image = ImageIO.read(path);
			//image = image.getScaledInstance(getWidth(), getHeight(), 0);
			g2 = (Graphics2D) image.getGraphics();
		}
		catch (Exception e) {System.out.println(e);}
		coords = new HashMap<>();
		repaint();
	}
	
}
