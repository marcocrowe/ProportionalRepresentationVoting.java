/*
 * Copyright (c) 2020 Mark Crowe <https://github.com/markcrowe-com>. All rights reserved.
 */
package com.markcrowe.proportionalrepresentationvoting;

import java.util.ArrayList;
import java.util.List;

public class Candidate
{
	public Candidate(int id, String name, String surname, String party)
	{
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.party = party;

		elected = false;
		excluded = false;
	}
	//
	//	Properties
	//
	public int getId()
	{
		return id;
	}
	public String getName()
	{
		return name + " " + surname;
	}
	public String getParty()
	{
		return party;
	}
	public boolean isElected()
	{
		return elected;
	}
	public boolean isExcluded()
	{
		return excluded;
	}
	public boolean isFinished()
	{
		return elected || excluded;
	}
	public int count()
	{
		return count;
	}
	public int getTotal()
	{
		return votes.size();
	}
	//
	//	methods
	//
	public void addVote(Ballot vote)
	{
		votes.add(vote);
	}
	public void setElected(int round)
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
		return votes.get(id);
	}
	public int getNextPreference(int id)
	{
		return votes.get(id).getPreference();
	}
	public int totalPreferenceVotes(int preference)
	{
		int total = 0;
		for(int index = 0; index < votes.size(); index++)
		{
			if(preference == votes.get(index).getCandidatePreference(this.id))
				total++;
		}
		return total;
	}
	@Override
	public String toString()
	{
		String text = String.format("%-24s", getName()) + party + "\t" + votes.size() + "\t" + totalPreferenceVotes(1) + "\t";
		if(isElected())
			text += "Elected ";
		else if(isExcluded())
			text += "Excluded";
		text += "\t" + count;
		return text;
	}
	//
	//	fields
	//
	private final int id;
	private final String name;
	private final String surname;
	private final String party;
	private boolean elected;
	private boolean excluded;
	private int count;
	private final List<Ballot> votes = new ArrayList<>();
}
