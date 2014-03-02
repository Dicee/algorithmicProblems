package codingame.medium;

import java.util.ArrayList;
import java.util.Scanner;

public class TelephoneNumbers {

    private ArrayList<Couple> children;   
    private int n;
    
    public TelephoneNumbers(int n) {
        children = new ArrayList<>();
        this.n = n;
    }
      
   	public int addSequence(String[] arr, int index) {
   		if (index > arr.length - 1)
   			return 0;
   		int created    = 0;
    	String num     = arr[index];
    	TelephoneNumbers child = null;
    	for (Couple j : children)
    		if (j.num.equals(num)) {
    			child = j.sol;
    			break;
    		}
    	if (child == null) {
    		created++;
    	    children.add(new Couple(num,child = new TelephoneNumbers(n-1)));
    	}
    	return created + child.addSequence(arr,index + 1);
    }    
   	
   	public int countChild() {
   		int result = 0;
   		for (Couple j : children)
   			result += 1 + j.sol.countChild();
    	return result;
   	}
   	
   	class Couple {
   		public TelephoneNumbers	sol;
		public String num;

		public Couple(String num, TelephoneNumbers sol) {
   			this.sol = sol;
   			this.num = num;
   		}
   	}
    
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		TelephoneNumbers sol = new TelephoneNumbers(n);
		in.nextLine();
		int created = 0;
		for (int i = 0; i < n; i++) 
			created += sol.addSequence(in.nextLine().split(""),1);		
		System.out.println(created);
		in.close();
	}
}