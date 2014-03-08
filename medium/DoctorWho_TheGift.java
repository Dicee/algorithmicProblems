package codingame.medium;

import java.util.Arrays;
import java.util.Scanner;

public class DoctorWho_TheGift {
	
	public static int countSameConsecutiveValues(int index, int[] arr) {		
		int result = 1;
		int value  = arr[index];
		while (index + result < arr.length && arr[index + result] == value) result++;
		return Math.min(arr.length - index,result);
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[] budgets = new int[n];
		int sum = 0;
		int c = in.nextInt();
		
		for (int i=0 ; i<n ; i++) {
			int budget = in.nextInt();
			budgets[i] = budget;
			sum += budget;
		}		
		in.close();
		
		//Thanks to this sorting, we'll be able to write only once in
		//contributions[i] (for i in 0:n excluded), twice in the worst case
		Arrays.sort(budgets);
		
		int canPay = n;
		while (canPay < n && budgets[n - canPay] == 0)
			canPay--;
		
		if (sum < c)
			System.out.println("IMPOSSIBLE");
		else {
			int[] contributions  = new int[n];
			int indexMin         =  n - canPay;
			int paid             = 0;
			while (c > 0) { 
				int toDistribute = c/canPay;
				int budgetMin    = budgets[indexMin] - paid;
				int count        = countSameConsecutiveValues(indexMin,budgets);
				
				if (toDistribute != 0) {
					//Fair repartition of what is left to pay. The amount paid cannot
					//be higher than the minimum contributor's budget left
					int pay      = Math.min(budgetMin,toDistribute);
					paid        += pay;
					c           -= pay*canPay;
					
					for (int i=indexMin ; i<indexMin + count ; i++)
						contributions[i] = paid;
					if (budgetMin <= toDistribute) {
					    indexMin += count;
					    canPay   -= count;
					}
				} else 
					//In that case, the amount left to pay is lower than the number
					//of contributors who still have some money
					for (int i=n-1 ; i>=indexMin ; i--) {
					    int left         =  (c > 0 ? 1 : 0);
						contributions[i] = paid + left;
						c               -= left;
					}				
			}	
			for (Integer i : contributions)
				System.out.println(i);
		}
	}
}