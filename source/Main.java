package annotation_tool;

import annotation_tool.Frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileWriter;


public class Main {
	
	JButton clearButton, nextButton, prevButton, saveButton;
	static boolean still_working;
	static boolean prev;
	static boolean exit;
	Frame frame;
	
	ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == clearButton) {
				frame.clear();
			} else if (e.getSource() == nextButton) {
				still_working = false;
			} else if (e.getSource() == prevButton) {
				still_working=false;
				prev = true;
			} else if (e.getSource() == saveButton) {
				exit = true;
			}
		}
	};

	public static void main(String[] args) {
		new Main().show();
		return;
	}
	public void show() {
		//Create Main Frame
		JFrame f = new JFrame();
		Container content = f.getContentPane();
		
		content.setLayout(new BorderLayout());
		
		//Create list of paths 
		ArrayList<String> paths = new ArrayList<>();
		
		//Set the config file path
		String config_filename = "config.txt";
		File config_file = new File(config_filename);
		
		// If the file path doesn't exist, print error and exit
		if (!config_file.exists()) {
			System.out.println("config.txt not found");
			return;
		}
		
		HashMap<String, String> configuration = new HashMap<>(); 
		
		// Read in the configuration attributes if it exists
		Scanner reader;
		try {
			reader = new Scanner(config_file);
			while(reader.hasNextLine()) {
				String config_line = reader.nextLine();
				String[] split = config_line.split(":");
				configuration.put(split[0], split[1]);
			}
			reader.close();
		} catch (FileNotFoundException e1) {System.out.println("config.txt not found");}
		
		// Set the paths_filename (filepaths.txt)
		String images_path = "images/";
		
		// Create a file object from that path
		File images_file = new File(images_path);
		
		for (String s : images_file.list()) {
			String out_path = images_path+s;
			paths.add(out_path);
		}
		JLabel count_display = new JLabel();
		
		// Create control panel
		JPanel controls = new JPanel();
		// Add buttons to control panel
		prevButton = new JButton("Previous Image");
		prevButton.addActionListener(actionListener);
		clearButton = new JButton("Clear");
		clearButton.addActionListener(actionListener);
		nextButton = new JButton("Next Image");
		nextButton.addActionListener(actionListener);
		saveButton = new JButton("Save and Exit");
		saveButton.addActionListener(actionListener);
		// Add the two buttons
		controls.add(count_display);
		controls.add(prevButton);
		controls.add(clearButton);
		controls.add(nextButton);
		controls.add(saveButton);
		// Add panel to layout
		content.add(controls, BorderLayout.NORTH);
		
		frame = new Frame();
		
		content.add(frame, BorderLayout.CENTER);
		
		int counter = Integer.parseInt(configuration.get("lastPathNum"));
		still_working = true;
		exit = false;
		
		ArrayList<HashMap<Integer, Integer[]>> coordinates = new ArrayList<>();
		
		//Set the frame size, close operation, and make it visible
		f.setSize(600, 600);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		// Keep looping until the counter reaches the length of paths
		while(counter < paths.size() &! exit) {	
			count_display.setText("Current Progress: %"+Integer.toString((counter*100)/paths.size()));
			controls.repaint();
			
			frame.changeImg(paths.get(counter));
			
			while(still_working &! exit) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
			
			if (exit) {
				break;
			}
			
			if (prev) {
				counter -= 1;
				coordinates.remove(counter);
				System.out.println(coordinates);
			}
			else {
				coordinates.add(frame.getCoords());
				counter += 1;
				System.out.println(coordinates);
			}
			
			still_working = true;
			prev=false;
			
		};
		
		File outputFile;
				
		// Create an output writer object
		FileWriter outputWriter = null;
		
		// Check to see if there is already an output file
		// If there is, create an output writer with the specified outputFile
		// Else, create the file and then create the output writer
		outputFile = new File("config.txt");
		
		try {
			
			if (outputFile.createNewFile()) {
				System.out.println("File created: " + outputFile.getName());
			}
			
			outputWriter = new FileWriter(outputFile);
		}
		catch (Exception e) {System.out.println(e);}
		
		configuration.put("lastPathNum", Integer.toString(counter));
		
		for (String s : configuration.keySet()) {
			String outputString = "";
			outputString = s + ":" + configuration.get(s)+"\n";
			try {
				outputWriter.write(outputString);
			}
			catch (Exception e) {System.out.println(e);}
		}
		try {
			outputWriter.close();
		}
		catch (Exception e) {System.out.println(e);}
		
		
		outputFile = new File("output.txt");
		
		try {
			
			if (outputFile.createNewFile()) {
				System.out.println("File created: " + outputFile.getName());
			}
			
			outputWriter = new FileWriter(outputFile, true);
		}
		catch (Exception e) {System.out.println(e);}
		
		for (HashMap<Integer, Integer[]> a : coordinates) {
			String outString = "";
			for (Integer[] t : a.values()) {
				String t_s = "[" + t[0].toString() + "," + t[1].toString() + "]";
				outString = outString + t_s + ":";
			}
			outString = outString.substring(0, outString.length()-1) + "\n";
			
			try {
				outputWriter.write(outString);
			}
			catch (Exception e) {System.out.println(e);}
		}
		
		// Finally close the output Writer
		try {
			outputWriter.close();
		}
		catch (Exception e) {System.out.println(e);}
		
		
		f.setVisible(false);
		f.dispose();
		
		return;
	}

}
