/*
 * Copyright (c) 2020 Mark Crowe <https://github.com/markcrowe-com>. All rights reserved.
 */
package com.markcrowe.proportionalrepresentationvoting;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElectionFileParser
{
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
				List<Candidate> cand = new ArrayList<>();
				List<Ballot> ballots = new ArrayList<>();
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
	private static Candidate parseCandidate(String candidateDetail)
	{
		String[] details = candidateDetail.split(", ");
		String[] names = details[0].split(" ", 2);
		return new Candidate(Integer.parseInt(/* id */names[0]), /* firstname */ details[1], /* surname */ names[1], /* party */ details[2]);
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
}
