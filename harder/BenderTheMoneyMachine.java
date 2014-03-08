package codingame.harder;

import java.util.Scanner;

public class BenderTheMoneyMachine {

	private static final int EXIT  = -1; 
	private static final int ENTRY =  0; 
	
	private static class Room {
		public int door0, door1, money;

		public Room(int door0, int door1, int money) {
			this.door0 = door0;
			this.door1 = door1;
			this.money = money;
		}
	}
	
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		
		Room[] rooms = new Room[n];
		for (int i = 0; i < n; i++) {
			//We suppose that the rooms numbers are consecutive
			int num = in.nextInt();
			int money = in.nextInt();
			String[] lineEnd = in.nextLine().trim().split(" ");
			int[] doors = { EXIT,EXIT };
			for (int j=0 ; j<2 ; j++)
				try {
					doors[j] = Integer.parseInt(lineEnd[j]);
				} catch (NumberFormatException nfe) { }
			rooms[num] = new Room(doors[0],doors[1],money);
		}
		in.close();
		
		boolean updated;
		int[] bestProfitFrom = new int[n];	
		for (int i=0 ; i<n ; i++)
			bestProfitFrom[i] = rooms[i].money; 
		
		do {
			updated = false;
			for (int i=0 ; i<n ; i++) {
				Room room        = rooms[i];
				int bestFrom0    = room.door0 != EXIT ? bestProfitFrom[room.door0] : 0;
				int bestFrom1    = room.door1 != EXIT ? bestProfitFrom[room.door1] : 0;			
				int bestFromRoom = room.money + Math.max(bestFrom0,bestFrom1);				
				
				if (bestFromRoom > bestProfitFrom[i]) {
					bestProfitFrom[i] = bestFromRoom;
					updated = true;
				}				
			}			
		} while (updated);
		
		/*If the room 0 wasn't, by convention, the only entry of the building, we should have
		 *printed the maximum of bestProfitFrom instead. Moreover, it was quite convenient that
		 *there was (in the specifications) no cycle in the graph. This algorithm wouldn't be
		 *enough otherwise.
		 */
		System.out.println(bestProfitFrom[ENTRY]);
	}
}