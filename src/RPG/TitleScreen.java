package RPG;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class TitleScreen extends JFrame{
	
	public final String GAME_NAME = "GAME NAME: Subtitle";
	
	JPanel allEncompasingPanel;
	JLabel titleLabel;
	JPanel buttonPanel;
	JButton newGameButton;
	JButton continueGameButton;
	JButton settingsButton;
	JButton exitGameButton;
	
	public TitleScreen(){
		setSize(500,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		buildMenu();
		
		add(allEncompasingPanel);
		setVisible(true);

	}
	
	private void buildMenu(){
		allEncompasingPanel = new JPanel(){
			//have paintComponent use whatever image we have for background 
		};
		allEncompasingPanel.setLayout(new BorderLayout());
		titleLabel = new JLabel(GAME_NAME);
		allEncompasingPanel.add(titleLabel, BorderLayout.NORTH);
		
		buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new MenuButtonListener());
		buttonPanel.add(newGameButton);
		
		continueGameButton = new JButton("Continue");
		continueGameButton.addActionListener(new MenuButtonListener());
		buttonPanel.add(continueGameButton);
		
		settingsButton = new JButton("Settings");
		settingsButton.addActionListener(new MenuButtonListener());
		buttonPanel.add(settingsButton);
		
		exitGameButton = new JButton("Exit");
		exitGameButton.addActionListener(new MenuButtonListener());
		buttonPanel.add(exitGameButton);
		
		allEncompasingPanel.add(buttonPanel, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		TitleScreen beginApp = new TitleScreen();
	}
	/*
	 * File f = new File("HEYO\\whatsGud");
		f.mkdirs();
		PrintWriter pw = new PrintWriter("HEYO\\whatsGud\\live.txt");
		pw.print("Still here!");
		pw.close();
		f.mkdirs();
	 */
	JPanel createFilePanel;
	JButton returnButton;
	JButton createFileButton;
	
	private class MenuButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String text = ((JButton) e.getSource()).getText();
			System.out.println(text);
			returnButton = new JButton("Return");
			returnButton.addActionListener(new MenuButtonListener());
			JTextField fileNameInput = new JTextField(15);
			
			if(text.equals("New Game")){
				createFilePanel = new JPanel(){
					//paintComponent set to whatever background we want
				};
				createFilePanel.setLayout(new BoxLayout(createFilePanel, BoxLayout.Y_AXIS));
				JLabel fileNamePrompt = new JLabel("File Name:");
				createFileButton = new JButton("Create File");
				createFileButton.addActionListener(new MenuButtonListener());
				
				createFilePanel.add(fileNamePrompt);
				createFilePanel.add(fileNameInput);
				createFilePanel.add(createFileButton);
				createFilePanel.add(returnButton);
				
				setVisible(false);
				remove(allEncompasingPanel);
				add(createFilePanel);
				setVisible(true);
			}
			else if(text.equals("Continue")){
				
			}
			else if(text.equals("Settings")){
				
			}
			else if(text.equals("Exit")){
				
			}
			else if(text.equals("Return")){
				setVisible(false);
				removeAll();
				buildMenu();
				add(allEncompasingPanel);
				invalidate();
				validate();
				setVisible(true);
			}
			
			else if(text.equals("Create File")){
				
				File f = new File("Saves\\" + fileNameInput.getText() + "\\Roster");
				f.mkdirs();
				PrintWriter pw;
				try {
					pw = new PrintWriter("Saves\\" + fileNameInput.getText() + "\\AvailableLevels.txt");
					pw.println("Test");
					pw.println("Tutorial");
					pw.close();
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
				toMap();
				
			}
		}
	}
	
	private void toMap(){
		// makes map panel, replaces it, etc.
	}
	
}
