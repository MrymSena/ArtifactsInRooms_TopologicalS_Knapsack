/**
Author:  Meryem Sena Bark

* Date:    December 2015

*/

import java.io.IOException;

public class Main {
	
	 public static void main(String args[]) throws IOException{

			EditRooms rooms = new EditRooms();
			rooms.ReadFile("input.txt");
			rooms.Findway();
			rooms.callKnapsack();
						
		 }
}
