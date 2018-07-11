package edu.cpt187.churchwell.program6;

public class SubwayTrain {

	// Variables
	private int numStations = 0;							//Number of stations serviced by this train
	private int maxCapacity = 0;							//Max capacity of entire train
	private int currentStation = 0;						//Number of the station the train is stopped at
	private int destinationStation = 0;					//Number of the next station to stop at
	private int numOnBoard = 0;							//Number of passengers on train
	private int peopleWaitingToLoad = 0;
	private int peopleWaitingToUnload = 0;
	private int vacantSeats = 0;
	private int peopleToLoad = 0;
	private int occupiedSeats = 0;
	private int peopleToUnload = 0;
	private int metroStart = 0;
	private int expressStart = 0;
	private int expressStop = 0;
	private boolean returnFlag = false;
	
	
	// Constructor Method
	public SubwayTrain(int newNumStations, int newMaxCapacity, int newStartStation)
	{
		numStations = newNumStations;
		maxCapacity = newMaxCapacity;
		currentStation = newStartStation;
	}
	
	/*------------------------------------------*/
	/*-------------  GET METHODS  --------------*/
	/*------------------------------------------*/
	// GET Train's Number of Service Stations
	public int getNumStations()
	{
		return numStations;
	}
	
	// GET Train's Max Capacity
	public int getMaxCapacity()
	{
		return maxCapacity;
	}
	
	// GET Train's Current Station
	public int getCurrentStation()
	{
		return currentStation;
	}
	
	// GET Train's Destination Station
	public int getDestinationStation()
	{
		return destinationStation;
	}
	
	public int getLastStation()
	{
		if (returnFlag == true)
		{
			return currentStation + 1;
		}
		else
		{
			return currentStation - 1;
		}
	}
	
	// GET Train's Current Number of Passengers
	public int getNumOnBoard()
	{
		return numOnBoard;
	}
	
	// Get People to Load
	public int getPeopleToLoad()
	{
		return peopleToLoad;
	}
	
	// Get People to Unload
	public int getPeopleToUnload()
	{
		return peopleToUnload;
	}
	
	/*------------------------------------------*/
	/*-------------  SET METHODS  --------------*/
	/*------------------------------------------*/
	
	// Set Number of Stations
	public void setNumStations(int newNumStations)
	{
		numStations = newNumStations;
	}
	
	// Set Max Capacity
	public void setMaxCapacity(int newMaxCapacity)
	{
		maxCapacity = newMaxCapacity;
	}
	
	// Set Starting Station
	public void setStartingStation(int newStartingStation)
	{
		metroStart = newStartingStation;
		currentStation = newStartingStation;
		expressStart = newStartingStation;
	}
	
	public void setDestinationStation(int newDestinationStation)
	{
		destinationStation = newDestinationStation;
		expressStop = newDestinationStation;
	}
	
	// Set Train Station From Current Station to Destination Station
	public void moveToStation(String newCurrentStation) /* OVERLOADED VERSION (Accepting String)*/
	{		
		currentStation = Integer.parseInt(newCurrentStation);
		
		returnFlag = !returnFlag;
		
		if (currentStation == expressStart)
		{
			destinationStation = expressStop;
		}
		else
		{
			destinationStation = expressStart;			
		}
		
		currentStation = destinationStation;
	}
	
	// Set Train Station From Current Station to Destination Station
	public void moveToStation(int newCurrentStation) 
	{		
		destinationStation = newCurrentStation;
		
		if (destinationStation == metroStart + numStations)
		{
			returnFlag = true;
		}
		
		if (returnFlag == false)
		{
			destinationStation++;
		}
		else
		{
			destinationStation--;
		}
		
		currentStation = destinationStation;
	}
	
	// Set Method to Unload People
	public void unloadPeople(TrainStation newStation) // SAME LOGIC
	{
		
		peopleWaitingToUnload = newStation.genRandNumber(maxCapacity);

		occupiedSeats = numOnBoard;
		
		if (peopleWaitingToUnload > occupiedSeats)
		{
			peopleToUnload = occupiedSeats;
		}
		else
		{
			peopleToUnload = peopleWaitingToUnload;
		}
		
		numOnBoard = numOnBoard - peopleToUnload;
	}
	
	// Set Method to Load People
	public void loadPeople(TrainStation newStation)
	{
		
		peopleWaitingToLoad = newStation.genRandNumber(maxCapacity);
		
		vacantSeats = maxCapacity - numOnBoard;
		
		if (peopleWaitingToLoad > vacantSeats)
		{
			peopleToLoad = vacantSeats;
		}
		else
		{
			peopleToLoad = peopleWaitingToLoad;
		}
		
		numOnBoard = numOnBoard + peopleToLoad;
	}
	
	public void loadFromStation(TrainStation newStation)
	{
		int newPassengersWaiting = 0;
		newPassengersWaiting = newStation.getPassengersWaiting();
	
		if (newPassengersWaiting > maxCapacity)
		{
			newPassengersWaiting -= maxCapacity;
			peopleToLoad = maxCapacity;
			numOnBoard = peopleToLoad;
		}
		else
		{
			peopleToLoad = newPassengersWaiting;
			newPassengersWaiting -= newPassengersWaiting;
			numOnBoard = peopleToLoad;
		}
		newStation.reduceNumberWaiting(peopleToLoad);
	}
	
	public void unloadAll()
	{
		peopleToUnload = numOnBoard;
	}
	
}