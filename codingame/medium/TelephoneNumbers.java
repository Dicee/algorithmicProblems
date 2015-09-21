package codingame.medium;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TelephoneNumbers {
    private Map<String, TelephoneNumbers> children = new HashMap<>();  
      
    public int addSequence(String num) { return addSequence(num.split(""), 0); }
    
   	private int addSequence(String[] arr, int index) {
   		if (index > arr.length - 1) return 0;
    	int added = children.putIfAbsent(arr[index], new TelephoneNumbers()) == null ? 1 : 0;
		return added + children.get(arr[index]).addSequence(arr,index + 1);
    }    
    
	public static void main(String args[]) {
		try (Scanner in = new Scanner(System.in)) {
			int n = in.nextInt();
			in.nextLine();
			
			TelephoneNumbers phoneNumbers = new TelephoneNumbers();
			int created = 0;
			for (int i = 0; i < n; i++) created += phoneNumbers.addSequence(in.nextLine());
			System.out.println(created);
		}
	}
}