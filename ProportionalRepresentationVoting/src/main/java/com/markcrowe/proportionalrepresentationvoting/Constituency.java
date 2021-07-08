/*
 * Copyright (c) 2020 Mark Crowe <https://github.com/markcrowe-com>. All rights reserved.
 */

import java.util.*;
import java.lang.*;

public class Constituency
{
	private String name;
	private int seats;
	private int quota;
	private int votes;
	private Candidate[] candidates;
	private Ballot[] ballots;
/*	Methods
	//Constructor
	public Constituency(String name, int seats, ArrayList candidates, ArrayList ballots)
	//Common Constructor Operations
	private void calculateQuota()
	//Obsevers
	public String getName()
	public int quota()
	//Output
	public String toString()
	//Operations
	public int turnOut()
	public int getPartyResults(String party)
	public void election()
	//Private Operations for public void election()
	private void startElection()
	private void redistributeAll(int id)
	private void redistributeSurplus(int id)
	private void redistributeVote(int id, int index)
	private int min()
*/	// Constructor
	public Constituency(String name, int seats, ArrayList candidates, ArrayList ballots)
	{
		this.name = name;
		this.seats = seats;
		this.votes = 1000;
		this.candidates = new Candidate[candidates.size()];
		for(int i = 0; i < this.candidates.length; i++)
		{
			this.candidates[i] = (Candidate) candidates.get(i);
		}
		this.ballots = new Ballot[ballots.size()];
		for(int i = 0; i < this.ballots.length; i++)
		{
			this.ballots[i] = (Ballot) ballots.get(i);
		}
		calculateQuota();
	}
	//Common Constructor Operations
	private void calculateQuota()
	{
		float temp = (float) ((ballots.length/(seats + 1)) + 1);
		quota = (int) Math.round(temp);
	}
	//Obsevers
	public String getName()	{return this.name;}
	public int quota()	{return quota;}
	//Output
	public String toString()
	{
		String temp = new String();
		temp =  name + "\n";
		temp += "TurnOut => " + turnOut() + "\t";
		temp += "Total Votes => " + ballots.length + "\n";
		temp += "Quota => " + quota + "\t";
		temp += "Seats => " + seats + "\t";
		temp += "Candidates => " + candidates.length + "\n\n";
		temp += "Name" + "\t\t\t" + "Party" + "\t" + "Total" + "\t" + "1st" + "\t" + "Status" + "\t\t" + "Count" +"\n";
		for(int i = 0; i < candidates.length; i++)
		{
			temp += candidates[i] + "\n";
		}
		return temp;
	}
	//Operations
	public float turnOut()
	{
		return (((float)(ballots.length)/ (float)votes) * 100);
		
	}

	public int getPartyResults(String party)
	{
		int sum = 0;
		for(int i = 0; i < candidates.length; i++)
		{
			if(candidates[i].party().compareToIgnoreCase(party) == 0)
			{
				sum++;
			}
		}
		return sum;
	}

	public void election()
	{
		int seats_left = seats;
		int candidates_left = candidates.length;
		int round = 0;
		boolean some;
		this.startElection();
		while(seats_left > 0)
		{
			round++;
			some = false;
			for(int i = 0; i < candidates.length; i++)
			{
				if((candidates[i].getTotal() >= quota  || seats_left == candidates_left) && !candidates[i].finished())
				{
					candidates[i].isElected(round);
					this.redistributeSurplus(i);
					seats_left -= 1;
					candidates_left -= 1;
					some = true;
				}
			}		
			if(some == false)
			{
				int small = min();
				candidates[small].isExcluded(round);
				candidates_left -= 1;
				this.redistributeAll(small);
			}
		}
		// deal with Candidates not elected
		for(int c = 0; c < candidates.length; c++)
		{
			if(!(candidates[c].finished()))
			{
				candidates[c].isExcluded(round);
			}
		}
	}
	//Private Operations for public void election()
	private void startElection()
	{
		int round = 1;
		for(int i = 0; i < ballots.length; i++)
		{
			int preference = ballots[i].getPreference(round);
			for(int j = 0; j < candidates.length; j++)
			{
				if(preference == candidates[j].getId())
				{
					candidates[j].addVote(ballots[i]);
				}
			}
		}
	}

	private void redistributeAll(int id)
	{
		for(int i = 0; i < candidates[id].getTotal(); i++)
		{
			redistributeVote(id, i);
		}
	}

	private void redistributeSurplus(int id)
	{
		int transferr = candidates[id].getTotal() - quota();
		for(int i = 0; i < transferr; i++)
		{
			redistributeVote(id, i);
		}
	}

	private void redistributeVote(int id, int index)
	{
		boolean getting_preferences = true;
		while(getting_preferences)
		{
			getting_preferences = false;
			int recieving_candidate = candidates[id].getNextPreference(index);
			boolean done = false;
			for(int i = 0; i < candidates.length && !done; i++)
			{
				if(recieving_candidate == candidates[i].getId())
				{
					if(candidates[i].finished())
					{
						getting_preferences = true;
					}
					else
					{
						candidates[i].addVote(candidates[id].giveVote(index));
						done = true;
					}
				}
			}
		}// end while
	}

	private int min()
	{
		int min = 0;
		boolean done = false;
		for(int i = 0; i < candidates.length && !done; i++)
		{
			if(!candidates[i].finished())
			{
				min = i;
				done = true;
			}
		}
		for(int i = 0; i < candidates.length; i++)
		{
			if((candidates[min].getTotal() > candidates[i].getTotal()) && !candidates[i].finished())
			{
				min = i;
			}
		}
		return min;
	}
}