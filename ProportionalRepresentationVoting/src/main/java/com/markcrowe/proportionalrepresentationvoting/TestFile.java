/*
 * Copyright (c) 2020 Mark Crowe <https://github.com/markcrowe-com>. All rights reserved.
 */
package com.markcrowe.proportionalrepresentationvoting;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class TestFile
{
	private int next;

	private final int ballots_min = 200;
	private final int ballots_max = 1000 - ballots_min;
	private final int candidates_min = 2;
	private final int candidates_max = 15 - candidates_min;
	private final int seats_min = 2;
	private final int seats_max = 8 - seats_min;

	public TestFile(String myfile)
	{
		try
		{
			buildTestFile(myfile);
			String message = "A Test File has successfully been created\nFile Name => " + myfile;
			info("File Created", message);
		}
		catch(IOException e)
		{
			String message = "An error occurred while opening the data.\nCan't open " + myfile;
			info("File I/O Error", message);
		}
	}
	private void buildTestFile(String myfile) throws IOException
	{
		try(PrintWriter printWriter = new PrintWriter(new FileWriter(myfile)))
		{
			next = 0;
			buildFile(printWriter);
			check(printWriter);
		}
	}

	//Output
	private void info(String title, String message)
	{
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	private void buildFile(PrintWriter printWriter)
	{
		printWriter.println("<ELECTION>");
		printWriter.println("<CONS>");
		for(int index = 0; index < constituencies.length; index++)
		{
			printWriter.println((index + 1) + " " + constituencies[index]);
		}
		printWriter.println("</CONS>");
		for(int index = 0; index < constituencies.length; index++)
		{
			int seats = seats_min + (int) (Math.random() * (seats_max + 1));
			int candidatesCount = 0;
			while(candidatesCount < seats)
			{
				candidatesCount = candidates_min + (int) (Math.random() * (candidates_max + 1));
			}
			int ballots = ballots_min + (int) (Math.random() * (ballots_max + 1));
			generateConstituency(printWriter, constituencies[index], seats, candidatesCount, ballots);
		}
		printWriter.println("</ELECTION>");
	}
	private void generateConstituency(PrintWriter printWriter, String name, int seats, int candidates, int ballots)
	{
		printWriter.println("<ELEC>");
		//Name
		printWriter.println("<ENAME>");
		printWriter.println(name);
		printWriter.println("</ENAME>");
		//Seats
		printWriter.println("<NUMOFSEATS>");
		printWriter.println(seats);
		printWriter.println("</NUMOFSEATS>");
		//Candidates
		printWriter.println("<CAND>");
		for(int i = 0; i < candidates; i++)
		{
			printWriter.println((i + 1) + " " + nextCandidate());
		}
		printWriter.println("</CAND>");

		printWriter.println("<BALLOTS>");
		for(int index = 0; index < ballots; index++)
		{
			printWriter.println(buildBallot(candidates));
		}
		printWriter.println("</BALLOTS>");
		printWriter.println("</ELEC>");
		printWriter.println("");
	}
	private String nextCandidate()
	{
		next++;
		return candidates[next - 1] + ", " + this.randomParty();
	}

	private String buildBallot(int candidateCount)
	{
		ArrayList<Integer> arrayList = new ArrayList<>();
		for(int index = 1; index <= candidateCount; index++)
		{
			arrayList.add(index);
		}
		Collections.shuffle(arrayList);

		int[] votes = new int[candidateCount];
		for(int index = 0; index < candidateCount; index++)
		{
			votes[index] = arrayList.get(index);
		}
		Ballot ballot = new Ballot(candidateCount, votes);
		return ballot.toString();
	}
	private String randomParty()
	{
		int randomIndex = (int) (Math.random() * parties.length);
		return parties[randomIndex];
	}
	//Error Check Operation
	private void check(PrintWriter printWriter)
	{
		if(printWriter.checkError())
		{
			String message = "An error occurred while writing the data.\nOutput file might be missing or incomplete.";
			info("File I/O Error", message);
		}
	}

	public static Constituency[] parseFile(BufferedReader reader) throws IOException
	{
		Constituency[] constituencies = null;
		String line;
		int constituencyNumber = 0;
		while((line = reader.readLine()) != null)
		{
			if(line.compareToIgnoreCase("<CONS>") == 0)
			{
				line = reader.readLine();
				int constituencyCount = 0;
				while(line.compareToIgnoreCase("</CONS>") != 0)
				{
					constituencyCount = constituencyCount + 1;
					line = reader.readLine();
				}
				constituencies = new Constituency[constituencyCount];
			}
			if(line.compareToIgnoreCase("<ELEC>") == 0)
			{
				line = reader.readLine();
				String name = "";
				int seats = 0;
				ArrayList<Candidate> cand = new ArrayList<>();
				ArrayList<Ballot> ballots = new ArrayList<>();
				while(line.compareToIgnoreCase("</ELEC>") != 0)
				{
					if(line.compareToIgnoreCase("<ENAME>") == 0)
						name = reader.readLine();
					if(line.compareToIgnoreCase("<NUMOFSEATS>") == 0)
						seats = Integer.parseInt(reader.readLine());
					if(line.compareToIgnoreCase("<CAND>") == 0)
						parseCandidates(reader, cand);
					if(line.compareToIgnoreCase("<BALLOTS>") == 0)
						parseBallots(reader, ballots);
					line = reader.readLine();
				}
				constituencies[constituencyNumber] = new Constituency(name, seats, cand, ballots);
				constituencyNumber++;
			}
		}
		return constituencies;
	}
	private static Ballot parseBallot(String preferences)
	{
		return new Ballot(preferences.split(", "));
	}
	private static void parseBallots(BufferedReader reader, List<Ballot> ballotList) throws IOException
	{
		String line = reader.readLine();
		while(!(line.compareToIgnoreCase("</BALLOTS>") == 0))
		{
			ballotList.add(parseBallot(line));
			line = reader.readLine();
		}
	}
	private static Candidate parseCandidate(String details)
	{
		String[] detail = details.split(", ");
		String[] temp = detail[0].split(" ");
		int id = Integer.parseInt(temp[0]);
		if(temp.length > 2)
		{
			for(int i = 2; i < temp.length; i++)
			{
				temp[1] += (" " + temp[i]);
			}
		}
		return new Candidate(id, detail[1], temp[1], detail[2]);
	}
	private static void parseCandidates(BufferedReader reader, List<Candidate> candidateList) throws IOException
	{
		String line = reader.readLine();
		while(!(line.compareToIgnoreCase("</CAND>") == 0))
		{
			candidateList.add(parseCandidate(line));
			line = reader.readLine();
		}
	}
	//
	//	fields
	//
	private final String[] candidates =
	{
		"Murchison, Ahmed",
		"Samsel, Althea",
		"Ferrante, Ami",
		"Mckiernan, Ana",
		"Piro, Annis",
		"Smit, Antonia",
		"Scarborough, Arica",
		"Condrey, Arie",
		"Keplinger, Arnette",
		"Raines, Arturo",
		"Nelson, Ashli",
		"Pryor, Asuncion",
		"Gauntt, Aurelia",
		"Placek, Ava",
		"Gershman, Beatrice",
		"Beres, Bertie",
		"Gathers, Brigitte",
		"Bunting, Bryce",
		"Martucci, Buena",
		"Schwenk, Carlos",
		"Weigel, Catheryn",
		"Drovin, Ceola",
		"Flohr, Chasidy",
		"Mijangos, Chelsie",
		"Pippenger, Christene",
		"Pleas, Christi",
		"Seals, Cinda",
		"Andres, Cindy",
		"Saenz, Colin",
		"Meads, Corrie",
		"Atkins, Corrin",
		"Tookes, Cyndy",
		"Reagan, Danilo",
		"Wood, Dario",
		"Shore, Delois",
		"Emond, Detra",
		"Knisely, Digna",
		"Schram, Domenic",
		"Bustle, Donovan",
		"Marra, Edythe",
		"Riggins, Elicia",
		"Trundy, Elisabeth",
		"Markell, Eloisa",
		"Durrant, Elsa",
		"Billing, Ervin",
		"Weingartner, Eryn",
		"Stansfield, Eva",
		"Purdie, Evalyn",
		"Harned, Forrest",
		"Fobbs, France",
		"Heist, Francesco",
		"Skerrett, Frank",
		"Gates, Fredric",
		"Lurie, Garrett",
		"Wold, Garry",
		"Dart, Gary",
		"Eastburn, Gema",
		"Reinert, Georgene",
		"Southworth, Georgianne",
		"Wetter, Giovanni",
		"Macken, Glinda",
		"Skow, Glinda",
		"Dosch, Grayce",
		"Alejandro, Gudrun",
		"Graham, Guillermo",
		"Schneiderman, Herb",
		"Jimenez, Ileen",
		"Bivins, Inez",
		"Clapp, Ingeborg",
		"Battersby, Janay",
		"Barlett, Janett",
		"Leader, Jasmine",
		"Rieser, Jen",
		"Lefebure, Jerrod",
		"Atlas, Joel",
		"Petersen, Joellen",
		"Albro, Johanna",
		"Mortimer, Jon",
		"Tejeda, Jone",
		"Casperson, Joshua",
		"Holz, Julia",
		"Janelle, Junita",
		"Bastarache, Kandra",
		"Barsh, Karine",
		"Feltmann, Karoline",
		"Luse, Keneth",
		"Atencio, Kennith",
		"Devaughn, Krysta",
		"Ortega, Lanette",
		"Shroyer, Lanora",
		"Faria, Larraine",
		"Ohern, Larraine",
		"Colorado, Lashanda",
		"Chausse, Latanya",
		"Candelaria, Latonya",
		"Velasques, Lauran",
		"Vandyke, Leanna",
		"Arent, Leila",
		"Cerutti, Lemuel",
		"Groce, Lenita",
		"Wickwire, Lettie",
		"Merrow, Lezlie",
		"Wingerter, Libbie",
		"Gladden, Lindsy",
		"Schultz, Linnie",
		"Brock, Lissette",
		"Degen, Lizzette",
		"Baskins, Lorena",
		"Wohlwend, Lovetta",
		"Holland, Lucy",
		"Stoneham, Lurlene",
		"Eddleman, Lynetta",
		"Mastin, Madlyn",
		"Pastore, Malik",
		"Louque, Mandi",
		"Sparrow, Manuela",
		"Weekley, Many",
		"Duncan, Margareta",
		"Battle, Mari",
		"Prue, Marianne",
		"Schiavo, Maude",
		"Wind, Maureen",
		"Mazzei, Maxwell",
		"Lubinski, Meda",
		"Spector, Merissa",
		"Broadnax, Meryl",
		"Minnis, Meryl",
		"Snodgrass, Mi",
		"Kuhlmann, Mica",
		"Manrique, Michelina",
		"Mealey, Micki",
		"Reiman, Milan",
		"Alspaugh, Miles",
		"Groen, Millie",
		"Purves, Ming",
		"Harrigan, Monserrate",
		"Ginther, Nettie",
		"Aldrich, Neville",
		"Ziemer, Nidia",
		"Feeley, Ozie",
		"Moss, Page",
		"Younce, Patti",
		"Dosch, Patty",
		"Gebo, Pete",
		"Boehm, Petronila",
		"Briley, Porsha",
		"Demers, Preston",
		"Secrist, Raymond",
		"Knobel, Regan",
		"Dollard, Renita",
		"Wiemer, Robbie",
		"Boer, Rocky",
		"Salgado, Ronna",
		"Doughty, Rosann",
		"Frampton, Rosann",
		"Borg, Roseanne",
		"Honey, Roselia",
		"Stuart, Rozanne",
		"Porco, Russell",
		"Oviedo, Sadye",
		"Chew, Sallie",
		"Lodge, Sandi",
		"Paulette, Sanora",
		"Marinez, Santiago",
		"Luby, Sarina",
		"Mcghee, Shameka",
		"Foshee, Shaquita",
		"Meade, Shara",
		"Shorey, Shawna",
		"Hammett, Soo",
		"Salley, Stephan",
		"Rusk, Syreeta",
		"Waltrip, Tamatha",
		"Hinchman, Tasia",
		"Lackey, Taunya",
		"Mullings, Teena",
		"Boland, Terence",
		"Hillock, Terence",
		"Ellis, Terry",
		"Haase, Thalia",
		"Cane, Tiffani",
		"Sorrell, Tiffany",
		"Newlin, Tressie",
		"Waterman, Trista",
		"Polster, Verena",
		"Ruocco, Vickie",
		"Brick, Vita",
		"Tutino, Vito",
		"Jorgensen, Wallace",
		"Hein, Werner",
		"Rockhill, Wilber",
		"Reves, Willian",
		"Rea, Wilton",
		"Bring, Yahaira",
		"Petillo, Yetta",
	};
	private final String[] constituencies =
	{
		"Clare",
		"Cork East",
		"Cork North Central",
		"Cork North West",
		"Cork South Central",
		"Cork South West",
		"Kerry North",
		"Kerry South",
		"Limerick East",
		"Limerick West",
		"Tipperary North",
		"Tipperary South",
		"Waterford"
	};
	private final String[] parties =
	{
		"FF", "FG", "Lab", "PD", "GP", "SF", "Ind"
	};
}
