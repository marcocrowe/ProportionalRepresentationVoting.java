/*
 * Copyright (c) 2020 Mark Crowe <https://github.com/markcrowe-com>. All rights reserved.
 */
package com.markcrowe.proportionalrepresentationvoting;

public class Ballot
{
	public Ballot(int numberOfCandidates)
	{
		candidatePreferences = new int[numberOfCandidates];
		next_preference = 1;
	}
	public Ballot(int numberOfCandidates, int[] votePreferences)
	{
		this(numberOfCandidates);
		for(int index = 0; index < candidatePreferences.length; index++)
		{
			candidatePreferences[index] = votePreferences[index];
		}
	}
	public Ballot(String[] votePreferences)
	{
		this(votePreferences.length);
		for(int index = 0; index < candidatePreferences.length; index++)
		{
			candidatePreferences[index] = Integer.parseInt(votePreferences[index]);
		}
	}
	//
	//	Methods
	//
	/**
	 * Increases the NextPreference by 1
	 * @return The Next Preference of the Ballot. If there is no next preference returns 0
	 */
	public int getPreference()
	{
		for(int index = 0; index < candidatePreferences.length; index++)
		{
			if(next_preference == candidatePreferences[index])
			{
				next_preference += 1;
				return index + 1;
			}
		}
		return 0;
	}
	public int getPreference(int preference)
	{
		for(int index = 0; index < candidatePreferences.length; index++)
		{
			if(preference == candidatePreferences[index])
				return index + 1;
		}
		return 0;
	}
	public int getCandidatePreference(int candidateId)
	{
		if(candidateId <= candidatePreferences.length)
			return candidatePreferences[(candidateId - 1)];
		return 0;
	}
	@Override
	public String toString()
	{
		var stringBuilder = new StringBuilder();
		if(candidatePreferences.length > 0)
		{
			stringBuilder.append(candidatePreferences[0]);
		}
		for(int index = 1; index < candidatePreferences.length; index++)
		{
			stringBuilder.append(", ").append(candidatePreferences[index]);
		}
		return stringBuilder.toString();
	}
	//
	//	fields
	//
	private final int[] candidatePreferences;
	private int next_preference;
}
