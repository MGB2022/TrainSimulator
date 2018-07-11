package edu.cpt187.churchwell.program6;

import java.util.Scanner;

public class MainClass {
	
	public static void main(String[] args)
	{
		// CONSTANTS
		final char SELECTOR_A = 'A';
		final char SELECTOR_B = 'B';
		
		// VARIABLES
		int numStations = 0;												// NUMBER OF STATIONS
		int maxCapacity = 0;												// NUMBER OF MAX PASSENGERS
		int startingStation = 0;											// NUMBER OF STARTING STATION
		int expressStop = 0;
		int metroTrainCount = 0;
		int expressTrainCount = 0;
		int metroStatServed = 0;
		int expressStatServed = 0;
		int metroPassXported = 0;
		int expressPassXported = 0;
		boolean quitFlag = false;
		String firstName = "";
		char trainTypeSelection = 0;
	
		Scanner input = new Scanner(System.in);														// SCANNER OBJECT
		
		SubwayTrain nycTrain = new SubwayTrain(numStations, maxCapacity, startingStation);			// TRAIN OBJECT
		TrainStation StartStation = new TrainStation();
		TrainStation StopStation = new TrainStation();
		
		welcomeBanner();
		
		System.out.println("Please enter your name:\n");
		System.out.print("-> ");
		firstName = input.nextLine();																// ACCEPT FIRST NAME		
		

		while (quitFlag != true)																		// OUTER LOOP
		{
			trainTypeSelection = trainTypeMenu(input, firstName);										// MAIN MENU
			trainTypeSelection = trainTypeValidator(input, trainTypeSelection, firstName);			// VALIDATE SELECTION
			
			if (trainTypeSelection == SELECTOR_A) 													// START METRO TRAIN
			{
			
				startingStation = inputStartingStation(input, numStations);							// ENGINEER INPUT
				startingStation = validateStartingStation(input, startingStation, numStations);
				nycTrain.setStartingStation(startingStation);
				
				numStations = inputNumStations(input);							// ENGINEER INPUT
				numStations = validateNumStations(input, numStations);			// VALIDATE INPUT
				nycTrain.setNumStations(numStations);												// SET numStations
				
				maxCapacity = inputMaxCapacity(input);							// ENGINEER INPUT
				maxCapacity = validateMaxCapacity(input, maxCapacity);			// VALIDATE INPUT
				nycTrain.setMaxCapacity(maxCapacity);												// SET maxCapacity
				
				displayMetroParameters(numStations, maxCapacity, startingStation);						// DISPLAY TEST PARAMETERS
						
				do {
					welcomeToStation(nycTrain.getCurrentStation());
					nycTrain.unloadPeople(StopStation);
					System.out.println("< " + nycTrain.getPeopleToUnload() + " Passengers Unloaded >");
					nycTrain.loadPeople(StopStation);
					System.out.println("< " + nycTrain.getPeopleToLoad() + " Passengers Loaded >");
					nycTrain.moveToStation(nycTrain.getCurrentStation());
					System.out.printf("Leaving Station %d for Station %d with %d passengers\n", nycTrain.getLastStation(), nycTrain.getCurrentStation(), nycTrain.getNumOnBoard());
					metroStatServed++;
					metroPassXported += nycTrain.getPeopleToUnload();
				}	
				while (nycTrain.getCurrentStation() != startingStation);

				welcomeToStation(nycTrain.getCurrentStation());
				nycTrain.unloadPeople(StopStation);
				System.out.println("< " + nycTrain.getPeopleToUnload() + " Passengers Unloaded >");
				metroStatServed++;
				metroPassXported += nycTrain.getPeopleToUnload();
				
				metroTrainCount++;
				System.out.println("END OF SIMULATION REPORT\n");
				System.out.println("Train's Current Station: " + nycTrain.getCurrentStation());
				System.out.println("Number of Passengers Onboard: " + nycTrain.getNumOnBoard());
				System.out.println("Train's Maximum Capacity: " + nycTrain.getMaxCapacity());
				System.out.println("Number of Met Stations Served: " + metroStatServed);
				System.out.println("Number of Met Passengers Transported: " + metroPassXported);
								
			} // END OF METRO TRAIN
			else if (trainTypeSelection == SELECTOR_B) // START EXPRESS TRAIN
			{
				
				startingStation = inputStartingStation(input, numStations);							// ENGINEER INPUT
				startingStation = validateStartingStation(input, startingStation, numStations);
				nycTrain.setStartingStation(startingStation);
				
				expressStop = inputExpressStop(input, numStations);										// INPUT STRING
				expressStop = validateExpressStop(input, expressStop, numStations);
				nycTrain.setDestinationStation(expressStop);
				
				maxCapacity = inputMaxCapacity(input);							// ENGINEER INPUT
				maxCapacity = validateMaxCapacity(input, maxCapacity);		
				nycTrain.setMaxCapacity(maxCapacity);
				
				StartStation.setStnMaxCapacity(maxCapacity);
				StartStation.setPassengersWaiting(StartStation.genRandNumber(maxCapacity));
				StopStation.setPassengersWaiting(StopStation.genRandNumber(maxCapacity));
				StopStation.setStnMaxCapacity(maxCapacity);
				
				displayExpressParameters(maxCapacity, startingStation, expressStop);						// DISPLAY TEST PARAMETERS

				System.out.println("\nStation " + startingStation + " Passengers Waiting: " + StartStation.getPassengersWaiting());
				System.out.println("Station " + expressStop + " Passengers Waiting: " + StopStation.getPassengersWaiting());
				
				while (StartStation.getPassengersWaiting() > 0 || StopStation.getPassengersWaiting() > 0)
				{
					welcomeToStation(nycTrain.getCurrentStation());
					System.out.println("- Passengers Waiting: " + StartStation.getPassengersWaiting() + " -");
					nycTrain.unloadAll();
					System.out.println("< " + nycTrain.getPeopleToUnload() + " Passengers Unloaded >");
					nycTrain.loadFromStation(StartStation);
					System.out.println("< " + nycTrain.getPeopleToLoad() + " Passengers Loaded >");
					nycTrain.moveToStation(Integer.toString(nycTrain.getCurrentStation()));
					expressStatServed++;
					expressPassXported += nycTrain.getPeopleToUnload();
					
					while (StopStation.getPassengersWaiting() > 0)
					{
						welcomeToStation(nycTrain.getCurrentStation());
						System.out.println("- Passengers Waiting: " + StopStation.getPassengersWaiting() + " -");
						nycTrain.unloadAll();
						System.out.println("< " + nycTrain.getPeopleToUnload() + " Passengers Unloaded >");
						nycTrain.loadFromStation(StopStation);
						System.out.println("< " + nycTrain.getPeopleToLoad() + " Passengers Loaded >");
						nycTrain.moveToStation(Integer.toString(nycTrain.getCurrentStation()));
						expressStatServed++;
						expressPassXported += nycTrain.getPeopleToUnload();
						nycTrain.unloadAll();
					}
				}
				welcomeToStation(nycTrain.getCurrentStation());
				nycTrain.unloadPeople(StopStation);
				System.out.println("< " + nycTrain.getPeopleToUnload() + " Passengers Unloaded >");
				expressStatServed++;
				expressPassXported += nycTrain.getPeopleToUnload();		
				System.out.println("\nStation " + startingStation + " Passengers Waiting: " + StartStation.getPassengersWaiting());
				System.out.println("Station " + expressStop + " Passengers Waiting: " + StopStation.getPassengersWaiting());
				System.out.println("");
				
				expressTrainCount++;
				System.out.println("END OF SIMULATION REPORT");
				System.out.println("Train's Current Station: " + nycTrain.getCurrentStation());
				System.out.println("Number of Passengers Onboard: " + nycTrain.getNumOnBoard());
				System.out.println("Train's Maximum Capacity: " + nycTrain.getMaxCapacity());
				System.out.println("Number of Exp Stations Served: " + expressStatServed);
				System.out.println("Number of Exp Passengers Transported: " + expressPassXported);
			} // END OF E TRAIN
			else
			{
				quitFlag = true;
			}

		} // END OF MAIN MENU

		System.out.println("\nFINAL USAGE REPORT");
		System.out.println("Metro Train Count: " + metroTrainCount);
		System.out.println("Express Train Count: " + expressTrainCount);
		
	} // END OF main
	
	// PRINT WELCOME BANNER
	public static void welcomeBanner()
	{
		System.out.println("======   WELCOME TO THE NYC SUBWAY DIAGNOSTIC SYSTEM   ======");
		System.out.println("      Model your subway system and test its performance");
		System.out.println("-------------------------------------------------------------\n");

	}
	
	// INPUT MAX NUMBER OF STATIONS
	public static int inputNumStations(Scanner input)
	{
		int newStationCount = 0; 					// HOLDS ENGINEER'S DESIRED NUMBER OF STATIONS FOR TESTING

		System.out.printf("\nPlease set the number of stations for your test route: \n(eg. 10)\n\n");
		System.out.print("-> ");
		newStationCount = input.nextInt();	// PUT THIS IN A METHOD AND VALIDATE
		
		return newStationCount;
	}
		
	// VALIDATE MAX NUMBER OF STATIONS
	public static int validateNumStations(Scanner input, int numStations)
	{
		int MIN_ENTRY = 1;
		
		while (numStations < MIN_ENTRY)
		{			
			System.out.printf("\n\nInvalid Entry - Your entry must be greater than %d \n\n", MIN_ENTRY); 
			System.out.print("-> ");
			numStations = input.nextInt();
			
		}
		return numStations;
	}
	
	// INPUT MAX NUMBER OF PASSENGERS
	public static int inputMaxCapacity(Scanner input)
	{
		int newPassengerCount = 0; 					// HOLDS ENGINEER'S DESIRED NUMBER OF STATIONS FOR TESTING
		
		System.out.printf("\nPlease set your maximum number of passengers for your \ntest run: (eg. 20)\n\n");
		System.out.print("-> ");
		newPassengerCount = input.nextInt();	// PUT THIS IN A METHOD AND VALIDATE
		
		return newPassengerCount;
	}
	
	// VALIDATE MAX NUMBER OF PASSENGERS
	public static int validateMaxCapacity(Scanner input, int numPassengers)
	{
		int MIN_ENTRY = 1;
		
		while (numPassengers < MIN_ENTRY)
		{			
			System.out.printf("\n\nInvalid Entry - Your entry must be greater than or equal to %d \n\n", MIN_ENTRY); 
			System.out.print("-> ");
			numPassengers = input.nextInt();
			
		}
		return numPassengers;
	}
	
	// INPUT STARTING STATION
	public static int inputStartingStation(Scanner input, int newStationCount)
	{
		int startingStation = 0;
		
		System.out.printf("\nPlease set the train's home station for your test \nrun: (eg. 1)\n\n");
		System.out.print("-> ");
		startingStation = input.nextInt();	// PUT THIS IN A METHOD AND VALIDATE
		
		return startingStation;
	}
	
	// VALIDATE STARTING STATION
	public static int validateStartingStation(Scanner input, int newStartingStation, int newStationCount)
	{
		int MIN_ENTRY = 1;
		
		while (newStartingStation < MIN_ENTRY)
		{			
			System.out.printf("\n\nInvalid Entry - Your entry must be greater than or equal to %d \n\n", MIN_ENTRY); 
			System.out.print("-> ");
			newStartingStation = input.nextInt();
			
		}
		return newStartingStation;
	}
	
	// INPUT EXPRESS STOP
	public static int inputExpressStop(Scanner input, int newStationCount)
	{
		int expressStop = 0;
		
		System.out.printf("\nPlease set the express train's receiving station for your test \nrun: (eg. 7)\n\n");
		System.out.print("-> ");
		expressStop = input.nextInt();	// PUT THIS IN A METHOD AND VALIDATE
		
		return expressStop;
	}
	
	// VALIDATE EXPRESS STOP
	public static int validateExpressStop(Scanner input, int newStartingStation, int newStationCount)
	{
		int MIN_ENTRY = 1;
		
		while (newStartingStation < MIN_ENTRY)
		{			
			System.out.printf("\n\nInvalid Entry - Your entry must be greater than or equal to %d! \n\n", MIN_ENTRY); 
			System.out.print("-> ");
			newStartingStation = input.nextInt();
			
		}
		return newStartingStation;
	}
	
	// DISPLAY ENGINEER'S TEST PARAMETERS 
	public static void displayMetroParameters(int newStationCount, int newMaxPassengers, int currentStation)
	{
		System.out.println("\n========================================================");
		System.out.println("\nYOUR NYC SUBWAY TEST MODEL");
		System.out.println("Train Type: Metropolitan");
		System.out.println("Home Station: Station #" + currentStation);
		System.out.println("Number of Stations: " + newStationCount);
		System.out.println("Maximum Passenger Capacity: " + newMaxPassengers);
	}
	
	// DISPLAY ENGINEER'S TEST PARAMETERS
	public static void displayExpressParameters(int newMaxPassengers, int currentStation, int expressStop)
	{
		System.out.println("\n========================================================");
		System.out.println("\nYOUR NYC SUBWAY TEST MODEL");
		System.out.println("Train Type: Express");
		System.out.println("Home Station: Station #" + currentStation);
		System.out.println("Express Stop: Station #" + expressStop);
		System.out.println("Maximum Passenger Capacity: " + newMaxPassengers);
	}
	
	public static void welcomeToStation(int currentStation)
	{
		System.out.println("\nWelcome to Station " + currentStation + "!");
	}
	
	public static void finalStop()
	{
		System.out.println("Final stop. All passengers please depart the train.");
		System.out.println("\n========================================================");
	}
	
	/*==========================================================*/
	/*------------  T R A I N   T Y P E   M E N U  -------------*/
	/*==========================================================*/
	public static char trainTypeMenu(Scanner input, String firstName)
	{

		//  CONSTANTS
		final String MAKE_SELECTION = ", please select the type of train for your simulation: \n\n";
		final String SELECTION_A = "[A] Metropolitan Train (Home Station to Multiple Stops) \n\n";
		final String SELECTION_B = "[B] Express Train (Home Station to 1 Destination) \n\n";
		final String SELECTION_Q = "[Q] Quit \n\n";
		
		String trainTypeMenu = "";
		String dashBorder = "";
		char dashChar = 0;
		char selection = 0;
		
		// Welcome Single Dash Boarder Length
		for (int e = 0; e < 60; e++) 
		{
			dashChar = '-';
			dashBorder += dashChar;
		}

		//  Concatenate Customer Type Menu
		trainTypeMenu = "\n" + dashBorder + "\n\n" + firstName + MAKE_SELECTION + SELECTION_A + SELECTION_B + SELECTION_Q;

		//  Return Customer Type Menu to Main
		System.out.print(trainTypeMenu);

		System.out.print("-> ");
		selection = input.next().charAt(0);
		selection = Character.toUpperCase(selection);

		return selection;
	}
	
	/*====================================================================*/
	/*------------  T R A I N   T Y P E   V A L I D A T O R  -------------*/
	/*====================================================================*/
	
	public static char trainTypeValidator(Scanner input, char trainTypeSelection, String firstName)
	{
		char SELECTOR_A = 'A';
		char SELECTOR_B = 'B';
		char SELECTOR_Q = 'Q';
		
		while (trainTypeSelection != SELECTOR_A && trainTypeSelection != SELECTOR_B && trainTypeSelection != SELECTOR_Q) 
		{
			System.out.println("\n\nInvalid Entry - Your must enter either 'A', 'B', or 'Q' \n");
			System.out.print("-> ");
			trainTypeSelection = input.next().charAt(0);
			trainTypeSelection = Character.toUpperCase(trainTypeSelection);
		}
		return trainTypeSelection;
	}
	
}
