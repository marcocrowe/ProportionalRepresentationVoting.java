/*
 * Copyright (c) 2020 Mark Crowe <https://github.com/markcrowe-com>. All rights reserved.
 */
package com.markcrowe.proportionalrepresentationvoting;

public class Party
{
	public Party(String name)
	{
		this.name = name;
		seats = 0;
	}
	//
	//	Methods
	//
	public void addSeats(int number)
	{
		seats += number;
	}
	public String getName()
	{
		return name;
	}
	public int getSeats()
	{
		return seats;
	}
	@Override
	public String toString()
	{
		return name + "\t" + seats;
	}
	//
	//	fields
	//
	private final String name;
	private int seats;
}
