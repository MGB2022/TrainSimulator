package edu.cpt187.churchwell.program6;

import java.util.Random;

public class TrainStation {

	private int passengersWaiting = 0;
	private int maximumCapacity = 0;
	
	// Random Number Generator
	private Random ranNumGenerator = new Random();
	
	// Set Random Number
	public int genRandNumber(int newMaxCapacity)				
	{
		return ranNumGenerator.nextInt((newMaxCapacity * newMaxCapacity) + 1); 
	}	
	
	// Get Passengers Waiting
	public int getPassengersWaiting()
	{
		return passengersWaiting;
	}
	
	// Get Max Capacity
	public int getStnMaxCapacity()
	{
		return maximumCapacity;
	}
	
	// Set Passengers Waiting
	public void setPassengersWaiting(int newPassengersWaiting)
	{
		passengersWaiting = newPassengersWaiting;
	}
	
	public void setStnMaxCapacity(int newMaximumCapacity)
	{
		maximumCapacity = newMaximumCapacity * newMaximumCapacity;
	}
	
	// Set Passengers Waiting (Reduce Number Waiting)
	public void reduceNumberWaiting(int newNumReduction)
	{
		passengersWaiting = passengersWaiting - newNumReduction;
	}
	
}
