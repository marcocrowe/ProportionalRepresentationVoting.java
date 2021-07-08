/*
 * Copyright (c) 2020 Mark Crowe <https://github.com/markcrowe-com>. All rights reserved.
 */

import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.Number;
import javax.swing.*;

public class TestFile
{
	public static void main(String[] args)
	{
		TestFile t = new TestFile("votes.dat");
	}
	private PrintWriter out;
	private String[] constituencies;
	private String[] candidates;
	private int next;
	private int count;
	//Seats
	private int seats_min;
	private int seats_max;
	//Candidates
	private int candidates_min;
	private int candidates_max;
	//Ballots
	private int ballots_min;
	private int ballots_max;
/*	Methods
	//Constructor
	public TestFile(String myfile)
	//Common Constructor Operations
	private void setMaxMinValues()
	private void setConstituenecyNames()
	setCandidatieNames()
	//Output
	private void info(String title, String message)
	private void buildFile()
	//Private Operations for private void buildFile()
	private void header()
	private void generateConstituency(String name, int seats, int candidates, int ballots)
	private void footer()
	//Operations
	private String nextCandidate(int i)
	private String buildBallot(int number_of_candidates)
	private String randomParty();
	//Error Check Operation
	private void check()
*/	//Constructor
	public TestFile(String myfile)
	{
		try
		{
			out = new PrintWriter(new FileWriter (myfile));
			setMaxMinValues();
			count = 13;
			constituencies = new String[count];
			next = 0;
			setCandidatieNames();
			setConstituenecyNames();
			buildFile();
			check();
			String message = new String();
			message =  "A Test File has successfully been created";
			message +=  ("\nFile Name => " + myfile);
			info("File Created", message);

		}
		catch(IOException e)
		{
			String message = new String();
			message =  "An error occurred while opening the data.\n";
			message +=  ("Can't open " + myfile);
			info("File I/O Error", message);
		}
		
	}
	//Common Constructor Operations
	private void setMaxMinValues()
	{
		//Seats
		seats_min = 2;
		seats_max = 8 - seats_min;
		//Candidates
		candidates_min = 2;
		candidates_max = 15 - candidates_min;
		//Ballots
		ballots_min = 200;
		ballots_max = 1000 - ballots_min;
	}

	private void setConstituenecyNames()
	{
		constituencies[0] = "Clare";
		constituencies[1] = "Cork East";
		constituencies[2] = "Cork North Central";
		constituencies[3] = "Cork North West";
		constituencies[4] = "Cork South Central";
		constituencies[5] = "Cork South West";
		constituencies[6] = "Kerry North";
		constituencies[7] = "Kerry South";
		constituencies[8] = "Limerick East";
		constituencies[9] = "Limerick West";
		constituencies[10] = "Tipperary North";
		constituencies[11] = "Tipperary South";
		constituencies[12] = "Waterford";
	}
	//Output
	private void info(String title, String message)
	{
		JOptionPane.showMessageDialog(null,  message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	private void buildFile()
	{
		this.header();
		for(int i = 0; i < count; i++)
		{
			int seats = seats_min + (int) (Math.random()*(seats_max+1));
			int candidates = 0;
			while(candidates < seats)
			{
				candidates = candidates_min + (int) (Math.random()*(candidates_max+1));
			}
			int ballots = ballots_min + (int) (Math.random()*(ballots_max+1));
			generateConstituency(constituencies[i], seats, candidates, ballots);
		}
		this.footer();
	}
	//Private Operations for private void buildFile()
	private void header()
	{
		out.println("<ELECTION>");
		out.println("<CONS>");
		for(int i = 0; i < this.count; i++)
		{
			out.println((i+1) + " " + constituencies[i]);
		}
		out.println("</CONS>");
	}

	private void generateConstituency(String name, int seats, int candidates, int ballots)
	{
		out.println("<ELEC>");
		//Name
		out.println("<ENAME>");
		out.println(name);
		out.println("</ENAME>");
		//Seats
		out.println("<NUMOFSEATS>");
		out.println(seats) ;
		out.println("</NUMOFSEATS>");
		//Candidates
		out.println("<CAND>");
		for(int i = 0; i < candidates; i ++)
		{
			out.println((i+1) + " " + nextCandidate());
		}
		out.println("</CAND>");
		//Ballots
		out.println("<BALLOTS>");
		for(int i = 0; i < ballots; i ++)
		{
			out.println(buildBallot(candidates));
		}
		out.println("</BALLOTS>");
		out.println("</ELEC>");
		out.println("");
	}

	private void footer()
	{
		out.println("</ELECTION>");
	}
	//Operations
	private String nextCandidate()
	{
		next++;
		return candidates[next-1] + ", " + this.randomParty();

	}

	private String buildBallot(int number_of_candidates)
	{
		ArrayList temp = new ArrayList();
		for(int i = 1; i <= number_of_candidates; i++)
		{
			temp.add(new Integer(i));
		}
		Collections.shuffle(temp);
		int[] votes = new int[number_of_candidates];
		for(int i = 0; i < number_of_candidates; i++)
		{
			votes[i] = ((Integer) temp.get(i)).intValue();
		}
		Ballot ballot = new Ballot(number_of_candidates, votes);
		return ballot.toString();
	}
	private String randomParty()
	{
		int party = 1 + (int) (Math.random()*(7));
		switch(party)
		{
			case 1 : return "FF";
			case 2 : return "FG";
			case 3 : return "Lab";
			case 4 : return "PD";
			case 5 : return "GP";
			case 6 : return "SF";
			case 7 : return "Ind";
		}
		return "FF";
	}
	//Error Check Operation
	private void check()
	{
		if (out.checkError())
		{
			String message = new String();
			message =  "An error occurred while writing the data.\n";
			message += "Output file might be missing or incomplete.";
			info("File I/O Error", message);
		}
	}

	private void setCandidatieNames()
	{
		candidates = new String[195];
		candidates[0] = "Ahmed, Murchison";
		candidates[1] = "Althea, Samsel";
		candidates[2] = "Ami, Ferrante";
		candidates[3] = "Ana, Mckiernan";
		candidates[4] = "Annis, Piro";
		candidates[5] = "Antonia, Smit";
		candidates[6] = "Arica, Scarborough";
		candidates[7] = "Arie, Condrey";
		candidates[8] = "Arnette, Keplinger";
		candidates[9] = "Arturo, Raines";
		candidates[10] = "Ashli, Nelson";
		candidates[11] = "Asuncion, Pryor";
		candidates[12] = "Aurelia, Gauntt";
		candidates[13] = "Ava, Placek";
		candidates[14] = "Beatrice, Gershman";
		candidates[15] = "Bertie, Beres";
		candidates[16] = "Brigitte, Gathers";
		candidates[17] = "Bryce, Bunting";
		candidates[18] = "Buena, Martucci";
		candidates[19] = "Carlos, Schwenk";
		candidates[20] = "Catheryn, Weigel";
		candidates[21] = "Ceola, Drovin";
		candidates[22] = "Chasidy, Flohr";
		candidates[23] = "Chelsie, Mijangos";
		candidates[24] = "Christene, Pippenger";
		candidates[25] = "Christi, Pleas";
		candidates[26] = "Cinda, Seals";
		candidates[27] = "Cindy, Andres";
		candidates[28] = "Colin, Saenz";
		candidates[29] = "Corrie, Meads";
		candidates[30] = "Corrin, Atkins";
		candidates[31] = "Cyndy, Tookes";
		candidates[32] = "Danilo, Reagan";
		candidates[33] = "Dario, Wood";
		candidates[34] = "Delois, Shore";
		candidates[35] = "Detra, Emond";
		candidates[36] = "Digna, Knisely";
		candidates[37] = "Domenic, Schram";
		candidates[38] = "Donovan, Bustle";
		candidates[39] = "Edythe, Marra";
		candidates[40] = "Elicia, Riggins";
		candidates[41] = "Elisabeth, Trundy";
		candidates[42] = "Eloisa, Markell";
		candidates[43] = "Elsa, Durrant";
		candidates[44] = "Ervin, Billing";
		candidates[45] = "Eryn, Weingartner";
		candidates[46] = "Eva, Stansfield";
		candidates[47] = "Evalyn, Purdie";
		candidates[48] = "Forrest, Harned";
		candidates[49] = "France, Fobbs";
		candidates[50] = "Francesco, Heist";
		candidates[51] = "Frank, Skerrett";
		candidates[52] = "Fredric, Gates";
		candidates[53] = "Garrett, Lurie";
		candidates[54] = "Garry, Wold";
		candidates[55] = "Gary, Dart";
		candidates[56] = "Gema, Eastburn";
		candidates[57] = "Georgene, Reinert";
		candidates[58] = "Georgianne, Southworth";
		candidates[59] = "Giovanni, Wetter";
		candidates[60] = "Glinda, Macken";
		candidates[61] = "Glinda, Skow";
		candidates[62] = "Grayce, Dosch";
		candidates[63] = "Gudrun, Alejandro";
		candidates[64] = "Guillermo, Graham";
		candidates[65] = "Herb, Schneiderman";
		candidates[66] = "Ileen, Jimenez";
		candidates[67] = "Inez, Bivins";
		candidates[68] = "Ingeborg, Clapp";
		candidates[69] = "Janay, Battersby";
		candidates[70] = "Janett, Barlett";
		candidates[71] = "Jasmine, Leader";
		candidates[72] = "Jen, Rieser";
		candidates[73] = "Jerrod, Lefebure";
		candidates[74] = "Joel, Atlas";
		candidates[75] = "Joellen, Petersen";
		candidates[76] = "Johanna, Albro";
		candidates[77] = "Jon, Mortimer";
		candidates[78] = "Jone, Tejeda";
		candidates[79] = "Joshua, Casperson";
		candidates[80] = "Julia, Holz";
		candidates[81] = "Junita, Janelle";
		candidates[82] = "Kandra, Bastarache";
		candidates[83] = "Karine, Barsh";
		candidates[84] = "Karoline, Feltmann";
		candidates[85] = "Keneth, Luse";
		candidates[86] = "Kennith, Atencio";
		candidates[87] = "Krysta, Devaughn";
		candidates[88] = "Lanette, Ortega";
		candidates[89] = "Lanora, Shroyer";
		candidates[90] = "Larraine, Faria";
		candidates[91] = "Larraine, Ohern";
		candidates[92] = "Lashanda, Colorado";
		candidates[93] = "Latanya, Chausse";
		candidates[94] = "Latonya, Candelaria";
		candidates[95] = "Lauran, Velasques";
		candidates[96] = "Leanna, Vandyke";
		candidates[97] = "Leila, Arent";
		candidates[98] = "Lemuel, Cerutti";
		candidates[99] = "Lenita, Groce";
		candidates[100] = "Lettie, Wickwire";
		candidates[101] = "Lezlie, Merrow";
		candidates[102] = "Libbie, Wingerter";
		candidates[103] = "Lindsy, Gladden";
		candidates[104] = "Linnie, Schultz";
		candidates[105] = "Lissette, Brock";
		candidates[106] = "Lizzette, Degen";
		candidates[107] = "Lorena, Baskins";
		candidates[108] = "Lovetta, Wohlwend";
		candidates[109] = "Lucy, Holland";
		candidates[110] = "Lurlene, Stoneham";
		candidates[111] = "Lynetta, Eddleman";
		candidates[112] = "Madlyn, Mastin";
		candidates[113] = "Malik, Pastore";
		candidates[114] = "Mandi, Louque";
		candidates[115] = "Manuela, Sparrow";
		candidates[116] = "Many, Weekley";
		candidates[117] = "Margareta, Duncan";
		candidates[118] = "Mari, Battle";
		candidates[119] = "Marianne, Prue";
		candidates[120] = "Maude, Schiavo";
		candidates[121] = "Maureen, Wind";
		candidates[122] = "Maxwell, Mazzei";
		candidates[123] = "Meda, Lubinski";
		candidates[124] = "Merissa, Spector";
		candidates[125] = "Meryl, Broadnax";
		candidates[126] = "Meryl, Minnis";
		candidates[127] = "Mi, Snodgrass";
		candidates[128] = "Mica, Kuhlmann";
		candidates[129] = "Michelina, Manrique";
		candidates[130] = "Micki, Mealey";
		candidates[131] = "Milan, Reiman";
		candidates[132] = "Miles, Alspaugh";
		candidates[133] = "Millie, Groen";
		candidates[134] = "Ming, Purves";
		candidates[135] = "Monserrate, Harrigan";
		candidates[136] = "Nettie, Ginther";
		candidates[137] = "Neville, Aldrich";
		candidates[138] = "Nidia, Ziemer";
		candidates[139] = "Ozie, Feeley";
		candidates[140] = "Page, Moss";
		candidates[141] = "Patti, Younce";
		candidates[142] = "Patty, Dosch";
		candidates[143] = "Pete, Gebo";
		candidates[144] = "Petronila, Boehm";
		candidates[145] = "Porsha, Briley";
		candidates[146] = "Preston, Demers";
		candidates[147] = "Raymond, Secrist";
		candidates[148] = "Regan, Knobel";
		candidates[149] = "Renita, Dollard";
		candidates[150] = "Robbie, Wiemer";
		candidates[151] = "Rocky, Boer";
		candidates[152] = "Ronna, Salgado";
		candidates[153] = "Rosann, Doughty";
		candidates[154] = "Rosann, Frampton";
		candidates[155] = "Roseanne, Borg";
		candidates[156] = "Roselia, Honey";
		candidates[157] = "Rozanne, Stuart";
		candidates[158] = "Russell, Porco";
		candidates[159] = "Sadye, Oviedo";
		candidates[160] = "Sallie, Chew";
		candidates[161] = "Sandi, Lodge";
		candidates[162] = "Sanora, Paulette";
		candidates[163] = "Santiago, Marinez";
		candidates[164] = "Sarina, Luby";
		candidates[165] = "Shameka, Mcghee";
		candidates[166] = "Shaquita, Foshee";
		candidates[167] = "Shara, Meade";
		candidates[168] = "Shawna, Shorey";
		candidates[169] = "Soo, Hammett";
		candidates[170] = "Stephan, Salley";
		candidates[171] = "Syreeta, Rusk";
		candidates[172] = "Tamatha, Waltrip";
		candidates[173] = "Tasia, Hinchman";
		candidates[174] = "Taunya, Lackey";
		candidates[175] = "Teena, Mullings";
		candidates[176] = "Terence, Boland";
		candidates[177] = "Terence, Hillock";
		candidates[178] = "Terry, Ellis";
		candidates[179] = "Thalia, Haase";
		candidates[180] = "Tiffani, Cane";
		candidates[181] = "Tiffany, Sorrell";
		candidates[182] = "Tressie, Newlin";
		candidates[183] = "Trista, Waterman";
		candidates[184] = "Verena, Polster";
		candidates[185] = "Vickie, Ruocco";
		candidates[186] = "Vita, Brick";
		candidates[187] = "Vito, Tutino";
		candidates[188] = "Wallace, Jorgensen";
		candidates[189] = "Werner, Hein";
		candidates[190] = "Wilber, Rockhill";
		candidates[191] = "Willian, Reves";
		candidates[192] = "Wilton, Rea";
		candidates[193] = "Yahaira, Bring";
		candidates[194] = "Yetta, Petillo";
	}
}
