/*
 * Copyright (c) 2020 Mark Crowe <https://github.com/markcrowe-com>. All rights reserved.
 */
package com.markcrowe.proportionalrepresentationvoting;

public class Election
{
	//
	//	Properties
	//
	public Constituency[] getConstituencies()
	{
		return constituencies;
	}
	public void setConstituencies(Constituency[] constituencies)
	{
		this.constituencies = constituencies;
	}
	public Party[] getParties()
	{
		return parties;
	}
	//
	//	Methods
	//
	public void calculatePartyResults()
	{
		for(Party party : parties)
		{
			for(Constituency constituency : constituencies)
			{
				party.addSeats(constituency.getPartySeatCount(party.getName()));
			}
		}
	}
	public String getPartyResults()
	{
		String text = "";
		for(int index = 0; index < parties.length; index++)
		{
			text += parties[index];
			if(index < parties.length - 1)
				text += "\n";
		}
		return text;
	}
	public void runElection()
	{
		for(Constituency constituency : constituencies)
		{
			constituency.election();
		}
	}
	//
	//	fields
	//
	private Constituency[] constituencies;
	private final Party[] parties =
	{
		new Party("FF"),
		new Party("FG"),
		new Party("Lab"),
		new Party("PD"),
		new Party("GP"),
		new Party("SF"),
		new Party("Ind")
	};
}
