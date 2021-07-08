/*
 * Copyright (c) 2020 Mark Crowe <https://github.com/markcrowe-com>. All rights reserved.
 */
public class Ballot
{
	private int[] candidate_preferences;
	private int next_preference;
/*	Methods
	//Constructors
	public Ballot(int number_of_candidates, int[] vote_preferences)
	public Ballot(String preferences)
	//Common Constructor Operations
	private void initialise(int number_of_candidate)
	private void set(int[] vote_preferences)
	//Output
	public int getPreference()
	public int getPreference(int preference)
	public int getCandidatePreference(int id)
	public String toString()
*/	//Constructors
	/**
		* Defines an Ballot object with an Array size and an Array that is copied in.
		* @param  number_of_candidates  A number of candidates that will apear on the ballot
		*         vote_preferences the preferences cast on the ballot, relative to the number_of_candidates argument
	*/
	public Ballot(int number_of_candidates, int[] vote_preferences)
	{
		initialise(number_of_candidates);
		set(vote_preferences);
	}
	/**
		* Defines an Ballot object by parsing a string
		* @param  preferences  Contains all information for the ballot
	*/
	public Ballot(String preferences)
	{
		String[] temp = (String[]) (preferences.split(", "));
		initialise(temp.length);
		for(int i = 0; i < candidate_preferences.length; i++)
		{
			candidate_preferences[i] = Integer.parseInt(temp[i]);
		}
	}
	//Common Constructor Operations
	/**
		* Initialise an Ballot objects array with a number_of_candidate
		* and sets next_preference default as 1
		* @param  preferences  Contains all information for the ballot
	*/
	private void initialise(int number_of_candidate)
	{
		candidate_preferences = new int[number_of_candidate];
		next_preference = 1;
	}
	private void set(int[] vote_preferences)
	{
		for(int i = 0; i < candidate_preferences.length; i++)
		{
			candidate_preferences[i] = vote_preferences[i];
		}
	}
	//Output
	/**
		*Increases the NextPreference by 1
		@return The Next Preference of the Ballot. If there is no next preference returns 0

	*/
	public int getPreference()
	{
		for(int i = 0; i < candidate_preferences.length; i++)
		{
			if(this.next_preference == candidate_preferences[i])
			{
				next_preference+=1;
				return i+1;
			}
		}
		return 0;
	}
	public int getPreference(int preference)
	{
		for(int i = 0; i < candidate_preferences.length; i++)
		{
			if(preference == candidate_preferences[i])
			{
				return i+1;
			}
		}
		return 0;
	}
	public int getCandidatePreference(int id)
	{
		if(id <= candidate_preferences.length)
		{
			return candidate_preferences[(id - 1)];
		}
		return 0;
	}
	public String toString()
	{
		String astring = new String();
		for(int i = 0; i < candidate_preferences.length; i++)
		{
			astring += candidate_preferences[i];
			if((i + 1) < candidate_preferences.length)
			{
				astring += ", ";
			}
		}
		return (astring);
		//return ("[" + astring + "]");
	}
}