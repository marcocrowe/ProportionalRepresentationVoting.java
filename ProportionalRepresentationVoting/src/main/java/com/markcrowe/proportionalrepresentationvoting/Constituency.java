/*
 * Copyright (c) 2020 Mark Crowe <https://github.com/markcrowe-com>. All rights reserved.
 */
package com.markcrowe.proportionalrepresentationvoting;

import java.util.List;

public class Constituency
{
	public Constituency(String name, int seats, List<Candidate> candidates, List<Ballot> ballots)
	{
		this.name = name;
		this.seats = seats;
		this.votes = 1000;
		this.candidates = new Candidate[candidates.size()];
		for(int index = 0; index < this.candidates.length; index++)
		{
			this.candidates[index] = candidates.get(index);
		}
		this.ballots = new Ballot[ballots.size()];
		for(int index = 0; index < this.ballots.length; index++)
		{
			this.ballots[index] = ballots.get(index);
		}

		quota = calculateQuota(this.ballots.length, seats);
	}
	//Obsevers
	public String getName()
	{
		return name;
	}
	public int getQuota()
	{
		return quota;
	}
	public float getTurnOut()
	{
		return (ballots.length / (float) votes) * 100;
	}
	//
	//	Public Methods
	//
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
				if((candidates[i].getTotal() >= quota || seats_left == candidates_left) && !candidates[i].isFinished())
				{
					candidates[i].setElected(round);
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
		for(Candidate candidate : candidates)
		{
			if(!(candidate.isFinished()))
			{
				candidate.isExcluded(round);
			}
		}
	}
	public int getPartySeatCount(String party)
	{
		int count = 0;
		for(Candidate candidate : candidates)
		{
			if(candidate.getParty().compareToIgnoreCase(party) == 0)
				count++;
		}
		return count;
	}
	@Override
	public String toString()
	{
		var text = name + "\n"
				+ "TurnOut => " + String.format("%.2f", getTurnOut()) + "%\t"
				+ "Total Votes => " + ballots.length + "\n"
				+ "Quota => " + quota + "\t"
				+ "Seats => " + seats + "\t"
				+ "Candidates => " + candidates.length + "\n\n"
				+ "Name" + "\t\t\t" + "Party" + "\t" + "Total" + "\t" + "1st" + "\t" + "Status" + "\t\t" + "Count" + "\n";
		for(Candidate candidate : candidates)
		{
			text += candidate + "\n";
		}
		return text;
	}
	//
	//	Public Static Methods
	//
	public static int calculateQuota(int numberOfBallots, int numberOfSeats)
	{
		return Math.round(((float) numberOfBallots / (numberOfSeats + 1)) + 1);
	}
	//
	//	Private Methods
	//
	private int min()
	{
		int min = 0;
		boolean done = false;
		for(int index = 0; index < candidates.length && !done; index++)
		{
			if(!candidates[index].isFinished())
			{
				min = index;
				done = true;
			}
		}
		for(int index = 0; index < candidates.length; index++)
		{
			if((candidates[min].getTotal() > candidates[index].getTotal()) && !candidates[index].isFinished())
				min = index;
		}
		return min;
	}
	private void redistributeAll(int id)
	{
		for(int index = 0; index < candidates[id].getTotal(); index++)
		{
			redistributeVote(id, index);
		}
	}
	private void redistributeSurplus(int id)
	{
		int transferr = candidates[id].getTotal() - getQuota();
		for(int index = 0; index < transferr; index++)
		{
			redistributeVote(id, index);
		}
	}
	private void redistributeVote(int id, int index)
	{
		boolean gettingPreferences = true;
		while(gettingPreferences)
		{
			gettingPreferences = false;
			int recievingCandidate = candidates[id].getNextPreference(index);
			boolean done = false;
			for(int i = 0; i < candidates.length && !done; i++)
			{
				if(recievingCandidate == candidates[i].getId())
				{
					if(candidates[i].isFinished())
					{
						gettingPreferences = true;
					}
					else
					{
						candidates[i].addVote(candidates[id].giveVote(index));
						done = true;
					}
				}
			}
		}
	}
	private void startElection()
	{
		int round = 1;
		for(Ballot ballot : ballots)
		{
			int preference = ballot.getPreference(round);
			for(Candidate candidate : candidates)
			{
				if(preference == candidate.getId())
					candidate.addVote(ballot);
			}
		}
	}
	//
	//	fields
	//
	private final String name;
	private final int seats;
	private final int quota;
	private final int votes;
	private final Candidate[] candidates;
	private final Ballot[] ballots;
}
