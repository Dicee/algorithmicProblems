package codingame.easy;
import java.util.*;

class ChuckNorris {

    public static String toBin(char c) {
       String result = "";
       for (int j=0 ; j<7 ; j++) {
    	   result  = c % 2 + result;
    	   c       = (char) ((c - ((char) c % 2))/2);
       }        
        return result;
    }
    
    private static String chuckNorris(String bin) {
		String result = "";
		while (!bin.isEmpty()) {
			int same;
			if (bin.charAt(0) == '0') {
				result += "00 ";
				same    = bin.indexOf('1');
			} else {
				result += "0 ";
				same    = bin.indexOf('0');
			}
			int l = bin.length();   
			same  = same == -1 ? l  : same;
			bin   = same == l  ? "" : bin.substring(same);
			for (int i=0 ; i<same ; i++) 
				result += "0";	
			result += same == l ? "" : " ";
		}
		return result;
	}
    
	public static void main(String args[]) {
		Scanner in  = new Scanner(System.in);
		String text = in.nextLine().trim();
		int n       = text.length();
		
		String bin  = ""; 
		for (int i=0; i<n; i++) 
			bin += toBin(text.charAt(i));
		System.err.println("Debug : " + bin);
		System.out.print(chuckNorris(bin));		
		in.close();
	}
}