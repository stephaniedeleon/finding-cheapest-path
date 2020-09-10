
//Date: 		May 15, 2020
//Description:	
//				Main method
//

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

public class AirportApp {
	
	static LinkedHashMap<String, String> airportNameMap = new LinkedHashMap <String, String>();
    static GraphInterface<String> airportGraph = new DirectedGraph<>();
	

	public static void main (String[] args) {
	
		//Reading info...
		boolean fileFound = true;
		String airportFile = "airports.csv"; 
		String distancesFile = "distances.csv"; 
				
		try {
			
			AirportApp.prepareAirportData(airportFile, 1); //inputs the file, and 1 & 2 specifies the type of file...
			AirportApp.prepareAirportData(distancesFile, 2); //goes to the helper method to read the file
			
		} catch(FileNotFoundException e) {
			
			fileFound = false;
			System.out.println("File not found."); // If the file is not found, then this warning message is printed out.
		}
		
		
		//Main...
		//if the file is found and read, then this will run...
		if(fileFound) {

			System.out.println("Airports by S. De Leon\n");
			
			StackInterface<String> path = new ArrayStack<>(); //for the D command
			String code, origin, end = "";
			double distance = 0;
			boolean exit = false;
	
			Scanner scan = new Scanner(System.in);
	
			while(!exit) { //Keeps asking 'Command?' until E commmand (exit = true)
	
				System.out.print("Command? ");
				String command = scan.next();
	
				switch(command) {
	
					case "Q":
						System.out.print("Airport Code: ");
						code = scan.next();
						
						System.out.println(airportNameMap.get(code));
						break;
	
					case "D":
						System.out.print("Airport Codes: ");
						origin = scan.next();
						end = scan.next();
						
						int cost = (int) airportGraph.getCheapestPath(origin, end, path); 
						
						System.out.println("The minimum distance between " + airportNameMap.get(origin) +  " and " + airportNameMap.get(end) + " is " + cost + " through the route:"); 
						while(!path.isEmpty()) 
							System.out.println(airportNameMap.get(path.pop())); // pops out the airport codes in the stack, to get the route
						break;
	
					case "I":
						System.out.print("Airport Codes and distance: ");
						origin = scan.next();
						end = scan.next();
						distance = scan.nextDouble();
	
						boolean result = airportGraph.addEdge(origin, end, distance); //returns true if successful, false if not
						
						if(result)
							System.out.println("You have inserted a connection from " + airportNameMap.get(origin) + " to " + airportNameMap.get(end) + " with a distance of " + (int)distance + ".");
						else 
							System.out.println("Unsuccessful. The pair of Airport Codes already exist or Airport Code does not exist."); //prints out a warning message if not succesfully added.
						break;
	
					case "R":
						System.out.print("Airport Codes: ");
						origin = scan.next();
						end = scan.next();
	
						result = airportGraph.removeEdge(origin, end); //returns true if successful, false if not. 
						
						if(result)
							System.out.println("The connection from " + airportNameMap.get(origin) + " and " + airportNameMap.get(end) + " removed."); 
						else
							System.out.println("Unsuccessful. Connection does not exist."); //prints out a warning message, if not succesfully removed.
						break;
	
					case "H":
						System.out.println("Q Query the airport information by entering the airport code." + 
											"\nD Find the minimum distance between two airports." +
											"\nI Insert a connection by entering two airport codes and distance." +
											"\nR Remove an existing connection by entering two airport codes." +
											"\nH Display this message." +
											"\nE Exit.");
						break;
	
					case "E":
						exit = true;
						scan.close();
						System.exit(0);
						break;
	
				}	
			}
		}
	}
	
	 
	//helper method to read the data...
	private static void prepareAirportData(String file, int type) throws FileNotFoundException{
				  
		File inputFile = new File(file);
		Scanner scan = new Scanner(inputFile);
			  
		while(scan.hasNextLine()) {
			
			if(type == 1)
				getVertices(scan.nextLine());	
			
			else if (type == 2)
				getConnections(scan.nextLine());
		}
		scan.close();
	}
	

	//this is for the airports.csv
	private static void getVertices(String line) {
		
		Scanner scan = new Scanner(line);
		scan.useDelimiter(",");
		  
		String code = scan.next();
		String name = scan.next();
		  
		airportGraph.addVertex(code);
		airportNameMap.put(code, name);
	  		 		 
		scan.close();
	}
	
	//this is for the distances.csv
	private static void getConnections(String line) {
		
		Scanner scan = new Scanner(line);
		scan.useDelimiter(",");
		  
		String begin = scan.next();
		String end = scan.next();
		double distance = scan.nextDouble();
		 
		airportGraph.addEdge(begin, end, distance);  
		
		scan.close();
	}
	
}