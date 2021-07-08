/*
 * Copyright (c) 2020 Mark Crowe <https://github.com/markcrowe-com>. All rights reserved.
 */
public class Party
{
	private String name;
	private int seats;
/*	Methods
	//Constructor
	public Party(String name)
	//Input
	public void addSeat()
	//Obsevers
	public String name()
	public int seats()
	//Output
	public String toString()
*/	//Constructor
	public Party(String name)
	{
		this.name = name;
		this.seats = 0;
	}
	//Input
	public void addSeats(int num)
	{
		seats += num;
	}
	//Obsevers
	public String name()	{return name;}
	public int seats()	{return this.seats;}
	//Output
	public String toString()
	{
		return (this.name() + "\t" + this.seats());
	}
}