package codingame.easy;

import java.util.*;

class ASCIIArt {
	
	public static final int nChars = 26;
	
	public static int charIndex(char c) {
		return c - 'A';
	}    

	public static void main(String args[]) {
		Scanner in  = new Scanner(System.in);
		int width   = in.nextInt();
		int height  = in.nextInt();
		
		in.nextLine();
		String text = in.nextLine().trim().toUpperCase();		
        int n       = text.length();
        
        String[] lines = new String[height];
        for (int i=0 ; i<height ; i++)
        	lines[i] = in.nextLine();
        in.close();	
        
        
        String result = "";
        for (int i=0 ; i<height ; i++) {
        	String line = "";
        	for (int j=0 ; j<n ; j++) {        		
        		int index = charIndex(text.charAt(j));
        		if (index >= nChars || index < 0)
        			line += lines[i].substring(nChars*width);
        		else
        			line += lines[i].substring(index*width,(index + 1)*width);
        	}
        	line   += "\n";
        	result += line;
        }
        System.out.println(result);
	}
}
