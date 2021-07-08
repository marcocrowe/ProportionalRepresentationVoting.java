/*
 * Copyright (c) 2020 Mark Crowe <https://github.com/markcrowe-com>. All rights reserved.
 */
import java.util.*;

public class Candidate
{
	private int id;
	private String name;
	private String surname;
	private String party;
	private boolean elected;
	private boolean excluded;
	private int count;
	private ArrayList votes;
/*	Methods
	//Constructors
	public Candidate(int id, String name, String surname, String party)
	public Candidate(String details)
	//Common Constructor Operations
	private void initialise()
	private void set(int id, String name, String surname, String party)
	//Observers
	public int getId()
	public String party()
	public boolean elected()
	public boolean excluded()
	public boolean finished()
	public int count()
	public int getTotal()
	//Input
	public void addVote(Ballot vote)
	//Output
	public void isElected(int round)
	public void isExcluded(int round)
	public Ballot giveVote(int id)
	public int getNextPreference(int id)
	public int totalPreferenceVotes(int preference)
	public String toString()
*/	//Constructors
	public Candidate(int id, String name, String surname, String party)
	{
		set(id, name, surname, party);
		initialise();
	}

	public Candidate(String details)
	{
		String[] detail = (String[]) details.split(", ");
		String[] temp = (String[]) detail[0].split(" ");
		int id = Integer.parseInt(temp[0]);
		if(temp.length > 2)
		{
			for(int i = 2;  i < temp.length; i++)
			{
				temp[1] += (" " + temp[i]);
			}
		}
		set(id, detail[1], temp[1], detail[2]);
		initialise();
	}
	//Common Constructor Operations
	private void initialise()
	{
		votes = new ArrayList();
		elected = false;
		excluded = false;
	}

	private void set(int id, String name, String surname, String party)
	{
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.party = party;
	}
	//Observers
	public int getId()		{return id;}
	public String party()		{return party;}
	public boolean elected()	{return elected;}
	public boolean excluded()	{return excluded;}
	public boolean finished()	{return (elected || excluded);}
	public int count()		{return count;}
	public int getTotal()		{return votes.size();}
	//Input
	public void addVote(Ballot vote)
	{
		votes.add(vote);
	}
	//Output
	public void isElected(int round)
	{
		elected = true;
		count = round;
	}

	public void isExcluded(int round)
	{
		excluded = true;
		count = round;
	}

	public Ballot giveVote(int id)
	{
		return (Ballot) votes.get(id);
	}

	public int getNextPreference(int id)
	{
		Ballot temp = (Ballot) votes.get(id);
		return temp.getPreference();
	}

	public int totalPreferenceVotes(int preference)
	{
		int sum = 0;
		for(int i = 0; i < votes.size(); i++)
		{
			Ballot temp = (Ballot) votes.get(i);
			if(preference == temp.getCandidatePreference(this.id))
			{
				sum++;
			}
		}
		return sum;
	}

	public String toString()
	{
		String temp = new String();
		temp = name + " " + surname + "\t";
		if((name.length() + surname.length()) < 15)
		{
			temp += "\t";
			if(name.length() + surname.length() < 7)
			{
				temp += "\t";
			}
		}


		temp += party + "\t" + votes.size() + "\t" + totalPreferenceVotes(1) + "\t";
		if(elected())		temp += "Elected ";
		else if (excluded())	temp += "Excluded";
		temp += "\t" + count;
		return temp;
	}
}