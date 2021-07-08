/*
 * Copyright (c) 2020 Mark Crowe <https://github.com/markcrowe-com>. All rights reserved.
 */
import java.awt.*;
import java.awt.event.* ;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.event.*;

public class Election extends JFrame implements ActionListener
{
	//Singalton
	private static Election election = new Election();
	//Private Data
	private Party[] parties;
	private Constituency[] constituencies;
//	private static boolean flag;
	//GUI Componets
	private JMenuBar menu_bar;
	private JMenu menu;
	private JMenuItem mopen, mclose, mexit, mtest, mhelp;
	private JDesktopPane desktop;
	private JInternalFrame results_file;
	private JComboBox con_list;
	private JTextArea text_area, text_area1;
/*	Methods
	//Constructor
	private Election()
	private void setParties()
	public static Election Instance()
	//GUI Constructor Operations
	private void setGui()
	private void setGuiMenu()
	//Election Window Operations
	private void electionWindow()
	private void loadList(JComboBox mybox)
	//Operations for an Election
	public void runElection()
	public void calculatePartyResults()
	public void showPartyResults()
	public void showResults(int id)
	//Operations called by GUI
	private void open()
	private void openFile()
	private void closeFile()
	private void createTestFile()
	private void about()
	private static void setupClosing(JFrame frame)
	//Listener
	public void actionPerformed(ActionEvent e)	
	//Load a file
	private void takeInput(BufferedReader file_contents)
*/	//Constructor
	private Election()
	{
//		flag = false;
		this.setGuiMenu();
		this.setGui();
	}

	private void setParties()
	{
		parties = new Party[7];
		parties[0] = new Party("FF");
		parties[1] = new Party("FG");
		parties[2] = new Party("Lab");
		parties[3] = new Party("PD");
		parties[4] = new Party("GP");
		parties[5] = new Party("SF");
		parties[6] = new Party("Ind");
	}

	public static Election Instance()
	{
		return election;
	}
	//GUI Constructor Operations
	private void setGui()
	{
		desktop = new JDesktopPane();
		text_area = new JTextArea();
		text_area.setFont(new Font("Courier",Font.PLAIN,12));
		text_area1 = new JTextArea();
		text_area.setEditable(false);
		text_area1.setEditable(false);
		this.setParties();
		this.setTitle("Proportional Representation Electronic Voting");
		this.setSize(850, 500);
		this.setLocation(150, 0);
		this.getContentPane().add(desktop);
		this.setVisible(true);
		setupClosing(this);
	}

	private void setGuiMenu()
	{
		//Menu Bar
		this.menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		//File Menu
		menu = new JMenu("File");
		menu.setMnemonic('F') ;
		menu_bar.add(menu);
		//Open
		mopen = new JMenuItem("Open", 'O');
		menu.add(mopen);
		mopen.addActionListener(this);
		//Close
		mclose = new JMenuItem("Close", 'C');
		menu.add(mclose);
		mclose.addActionListener(this);
		//Exit
		mexit = new JMenuItem("Exit", 'E');
		menu.add(mexit);
		mexit.addActionListener(this);
		//Election Menu
		menu = new JMenu("Election");
		menu.setMnemonic('E') ;
		menu_bar.add(menu);
		//Test
		mtest = new JMenuItem("Create Election Test File", 'T');
		menu.add(mtest);
		mtest.addActionListener(this);
		//Help Menu
		menu = new JMenu("Help");
		menu.setMnemonic('H') ;
		menu_bar.add(menu);
		//about
		mhelp = new JMenuItem("About", 'A');
		menu.add(mhelp);
		mhelp.addActionListener(this);
	}
	//Election Window Operations
	private void electionWindow()
	{
		results_file = new JInternalFrame("Munster Elections 2003", true , true , true , true);
		results_file.getContentPane().setLayout(new BorderLayout());
		results_file.setPreferredSize(new Dimension(840, 400));
		//Components
		con_list = new JComboBox();
		loadList(con_list);
		con_list.addActionListener(new ActionListener()  {public void actionPerformed(ActionEvent e)
				{
					JComboBox temp = (JComboBox) e.getSource();
					int  id = temp.getSelectedIndex();
					showResults(id);
				} 	}	);
		//Left Window
		JPanel results_party = new JPanel();
		JPanel results_navagation = new JPanel();
		results_navagation.setLayout(new BorderLayout());
		results_navagation.add(con_list, BorderLayout.NORTH);
//		results_navagation.add(text_area1, BorderLayout.CENTER);
		results_party.add(text_area1);
		results_file.getContentPane().add(results_navagation, BorderLayout.WEST);
		results_file.getContentPane().add(results_party, BorderLayout.EAST);

		//Right Window
		JScrollPane results_view = new JScrollPane(text_area);
		results_file.getContentPane().add(results_view, BorderLayout.CENTER);

		results_file.pack() ;
		desktop.add(results_file) ;
		results_file.setVisible(true) ;
	}
	
	private void loadList(JComboBox mybox)
	{
		for(int i = 0; i < constituencies.length; i++)
		{
			mybox.addItem(constituencies[i].getName());
		}
	}
	//Operations for an Election
	public void runElection()
	{
		for(int i = 0; i < this.constituencies.length; i++)
		{
			constituencies[i].election();
		}
	}

	public void calculatePartyResults()
	{
		for(int i = 0; i < parties.length; i++)
		{
			for(int n = 0; n < constituencies.length; n++)
			{
				parties[i].addSeats(constituencies[n].getPartyResults(parties[i].name()));
			}
		}
	}

	public void showPartyResults()
	{
		String temp = new String();
		for(int i = 0; i < parties.length; i++)
		{
			temp += parties[i];
			if(i < parties.length - 1)
			{
				temp += "\n";
			}
		}
		text_area1.setText(temp);
	}

	public void showResults(int id)
	{
		text_area.setText(constituencies[id].toString()); 
	}
	//Operations called by GUI
	private void open()
	{
//		int option = 0;
//		if(this.flag == true)
//		{
//			option = (int) JOptionPane.showConfirmDialog(null, "An Election is running.\nDo you to close it.", "Run Election", JOptionPane.YES_NO_OPTION);
//		}
//		if(option == 0)
//		{
			this.closeFile();
			openFile();
//		}
	}

	private void openFile()
	{
		JFileChooser get_file = new JFileChooser();
		get_file.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result =  get_file.showOpenDialog(this);
		if(result == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				BufferedReader file_contents = new BufferedReader(new FileReader(get_file.getSelectedFile().getPath()));
				this.takeInput(file_contents);
				this.runElection();
				this.calculatePartyResults();
				this.showResults(0);
				this.showPartyResults();
				this.electionWindow();
//				this.flag = true;
			}
			catch(IOException ioException)
			{
				ioException.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error Opening File", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void closeFile()
	{
		if(results_file != null)	results_file.dispose();
	}

	private void createTestFile()
	{
		TestFile temp = new TestFile("votes.dat");
	}
	private void about()
	{
		String message = new String("Election Processing System v1.0 \nCopright Mark Crowe 2003\nhttps://github.com/markcrowe-com");
		JOptionPane.showMessageDialog(null,  message, "Election Processing System", JOptionPane.INFORMATION_MESSAGE);
	}

	private static void setupClosing(JFrame frame)
	{
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	//Listener
	public void actionPerformed(ActionEvent e)
	{
		JMenuItem menu_item = (JMenuItem) e.getSource();
		if(menu_item == mopen)		this.open();
		else if(menu_item == mclose)	this.closeFile();
		else if(menu_item == mexit)	System.exit(0);
		else if(menu_item == mtest)	this.createTestFile();
		else if(menu_item == mhelp)	this.about();
	}

	private void takeInput(BufferedReader file_contents)
	{
		String line = new String();
		int cons_count = 0;
		int index = 0;
		try
		{
			while((line = file_contents.readLine()) != null)
			{
				if(line.compareToIgnoreCase("<CONS>") == 0)
				{
					line = file_contents.readLine();
					while(!(line.compareToIgnoreCase("</CONS>") == 0))
					{
						cons_count = cons_count +1;
						line = file_contents.readLine();
					}
					this.constituencies = new Constituency[cons_count];
				}
				if(line.compareToIgnoreCase("<ELEC>") == 0)
				{
					line = file_contents.readLine();
					String name = new String();
					int seats = 0;
					ArrayList cand = new ArrayList();
					ArrayList ballots = new ArrayList();
					while(!(line.compareToIgnoreCase("</ELEC>") == 0))
					{
						if(line.compareToIgnoreCase("<ENAME>") == 0)
						{
							line = file_contents.readLine();
							name = line;
						}
						if(line.compareToIgnoreCase("<NUMOFSEATS>") == 0)
						{
							line = file_contents.readLine();
							seats = Integer.parseInt(line);
						}
						if(line.compareToIgnoreCase("<CAND>") == 0)
						{
							line = file_contents.readLine();
							while(!(line.compareToIgnoreCase("</CAND>") == 0))
							{
								Candidate temp = new Candidate(line);
								cand.add(temp);
								line = file_contents.readLine();
							}
						}
						if(line.compareToIgnoreCase("<BALLOTS>") == 0)
						{
							line = file_contents.readLine();
							while(!(line.compareToIgnoreCase("</BALLOTS>") == 0))
							{
								Ballot temp = new Ballot(line);
								ballots.add(temp);
								line = file_contents.readLine();
							}
						}
						line = file_contents.readLine();
					}
					this.constituencies[index] = new Constituency(name, seats, cand, ballots);
					index++;
				}
			}
		}
		catch(IOException ioException)
		{
    			ioException.printStackTrace() ;
    			JOptionPane.showMessageDialog(this , "Error Opening File " , "Error" , JOptionPane.ERROR_MESSAGE) ;
		}
	}
}
