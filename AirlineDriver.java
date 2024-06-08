import java.util.Scanner;

class Seat { //create Seat object to handle seat position and status for reservation.
	private int row;
	private int column;
	private boolean taken = false;  // this boolean will keep track of whether the seat is available or not. Could be switched to available = true.
	
	public Seat(int y, int x) { //constructor initializes private variables of the seats position in the array AKA plane.
		row = y;
		column = x;
	}
	
	public void changeStatus() {
		taken = taken == false ? true : false;  //this is a Setter method using ternary condition to change the seat status of taken to True or False.
	}
	
	public boolean getStatus() { //Getter method for checking seat status. Very important!
		return taken;
	}
	
	public int getRow() { //Getter Method
		return row;
	}
	
	public int getColumn() { //Getter Method
		return column;
	}
	
	public char convertColumnToChar() { //Getter Method that converts column integer value to an appropriate char
		char columnChar = 'Z';
		switch (column) {
			case 0:
				columnChar = 'A';
				break;
			case 1:
				columnChar ='B';
				break;
			case 2:
				columnChar ='C';
				break;
			case 3:
				columnChar = 'D';
				break;
		}	
		return columnChar;
	}
	
	public String toString() {  //output string values needed for the application depending on the status of taken attribute.
		if (taken == false) {
			return String.valueOf(convertColumnToChar());
		}
		else {
			return "X";
		}	
	}

}


public class AirlineDriver {
	
	static Scanner scan = new Scanner(System.in);
	
	public static Seat[][] createLayout(int numRows) { //function returns a two dimensional array of Seat objects
		Seat[][] newLayout = new Seat[numRows][4]; //uses the number of rows desired to declare a correct size of the two dimensional array of Seat
		
		for (int row = 0; row < numRows; row++) {
			for (int column = 0; column < newLayout[row].length; column++) {
				newLayout[row][column] = new Seat(row, column); //nested for-loop gives row and column values to be initialized into each Seat object.
			}
		}
		return newLayout; //return the two-dimensional array
	}
	
	public static void displaySeats(Seat [][] plane) {
		for (int row = 0; row < plane.length; row++) { //loop through multidimensional Seat array with nested for loops.
			System.out.print("   " + (row + 1) + ") ");
			for (int column = 0; column < plane[row].length; column++) {
				System.out.print((plane[row][column]) + " "); //the Seat objects will use the toString Method to correctly display a letter or X!
			}
	       System.out.println();
	    }
	}
	
	
	public static boolean planeLoop(String planeApp) { //The outer loop boolean function to restart or quit application
		if (!planeApp.equals("-1")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean seatLoop(String seatApp) { //The inner loop boolean function to choose more seats or finish with current plane.
		if (!seatApp.equals("-1")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static int convertToInt(String column) { //A useful tool to convert the user column choice to a number. 
		int columnNum = -1;
		switch (column.toUpperCase()) {
			case "A":
				columnNum = 0;
				break;
			case "B":
				columnNum = 1;
				break;
			case "C":
				columnNum = 2;
				break;
			case "D":
				columnNum = 3;
				break;
		}	
		return columnNum;
		
	}


	public static void main (String[] args) {
		
		scan.useDelimiter("\\R"); //delimiter to prevent empty inputs by user.
		
		System.out.println("Welcome to the Airplane Seat Reservation Application!");
		System.out.println("ENTER to continue / -1 to Quit");
		String loopBool = scan.nextLine(); //initialize the outer loop boolean and start the application
		
		
		while (planeLoop(loopBool)) { //Outer Loop
			final int planeLength = 5; //initialize plane size. Can be easily changed!
			int totalAvailableSeats = planeLength * 4; //calculate total possible seats to check later whether all are taken and end loop.
			
			Seat [][] layout = createLayout(planeLength); //create plane!
			System.out.println("\n***********************************************************");
			System.out.println("This is the current layout of AVAILABLE and RESERVED seats: ");
			System.out.println("***********************************************************\n");
			displaySeats(layout); 
			
			String lookForSeats = "Y"; //initialize inner loop boolean to start the seat selection loop
			while (seatLoop(lookForSeats)) { //Inner Loop

				int seatRowChoice = -1; //initialize bad RowChoice to start While loop, could have used a Do - Until instead.
				while (seatRowChoice <= 0 || seatRowChoice > planeLength) {
					try {
						System.out.print("\nPlease enter the ROW NUMBER you would like (1 - " + planeLength + "): ");
						seatRowChoice = scan.nextInt(); //get integer data from user input
						scan.nextLine();
						
						if  (seatRowChoice <= 0 || seatRowChoice > planeLength) {
							System.out.println("\nError! Please enter a number from 1 - " + planeLength + "!");
						}
					}
					catch (Exception Err) { //catch error that user didn't enter a number
						System.out.println("\nError! Please enter a valid number!");
						scan.nextLine();
					}
				}
				
				System.out.print("\nThank you! "); //let user know they added correct information
				
				
				String seatColumnChoice = "Z"; //initialize bad ColumnChoice to start While loop, could have used a Do - Until instead.
				while (!seatColumnChoice.toUpperCase().equals("A")  && !seatColumnChoice.trim().toUpperCase().equals("B")
						&& !seatColumnChoice.toUpperCase().equals("C") && !seatColumnChoice.trim().toUpperCase().equals("D")) { //possibly could have turned this check into a function
					
					System.out.print("\nPlease enter the SEAT LETTER you would like (A - D): ");
					seatColumnChoice = scan.next(); //get string data from user input
					scan.nextLine();
					
					if (!seatColumnChoice.toUpperCase().equals("A")  && !seatColumnChoice.trim().toUpperCase().equals("B")
							&& !seatColumnChoice.toUpperCase().equals("C") && !seatColumnChoice.trim().toUpperCase().equals("D")) {
						System.out.println("Error! Please enter a valid letter!");
					}
				}
				
				System.out.println("\nYou selected Row " + seatRowChoice + " / Column " + seatColumnChoice.toUpperCase()); //let user know the seat they chose.
				
				if (!layout[seatRowChoice - 1][convertToInt(seatColumnChoice)].getStatus()) { //check the taken status of Seat object at array position selected
					layout[seatRowChoice - 1][convertToInt(seatColumnChoice)].changeStatus(); //change the seat status taken to true!
					System.out.print("That seat is available! It will now be reserved for you. ");
					totalAvailableSeats -= 1; //decrement the total seats left on this current plane.
				}
				
				else {
					System.out.print("That seat is already taken! "); //Seat status taken was already true. Is is not available.
				}
				
				System.out.println("Press ENTER to continue:");
				scan.nextLine();

				System.out.println("\n***********************************************************");
				System.out.println("This is the current layout of AVAILABLE and RESERVED seats: ");
				System.out.println("***********************************************************\n");
				displaySeats(layout);
				
				if (totalAvailableSeats > 0) { //check that there are still seats available
					System.out.println("\nWould you like to look for more seats on this plane? ENTER to continue / -1 to Quit: ");
					lookForSeats = scan.nextLine(); //user decides if they are done making reservations on this current plane.
				}
				
				else {
					System.out.println("\nAll Seats Taken!");
					lookForSeats = "-1"; //force the loop to end by converting the look for seats to -1.
				}
			}
			
			System.out.println("\nCurrent plane revervations finished...");
			
			System.out.println("\nWould you like to start reservations on a brand new plane? ");
			System.out.print("ENTER to continue / -1 to Quit: ");
			loopBool = scan.nextLine(); //user decides if they want to start application over again
		}
			
		scan.close();
		System.out.println("\nProgram Terminated!");
	}
}

