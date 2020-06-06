package codingame.medium;

import java.util.Scanner;

public class MayaNumbers {
	
	private static int getFigure(String figure) {
		int i=0 ; 
		for (String mayaFigure : mayaFigures) {
			if (figure.equals(mayaFigure))
				return i;
			i++;
		}
		return -1;
	}
	
	private static String[]	mayaFigures;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int L = in.nextInt();
		int H = in.nextInt();
		in.nextLine();
		
		mayaFigures = new String[20];
		for (int i=0 ; i<20 ; i++)
			mayaFigures[i] = "";
		
		for (int i=0 ; i<H ; i++) {
			String line = in.nextLine();
			int length = line.length();
			for (int j=0 ; j<length ; j++)
				mayaFigures[j/L] += line.charAt(j);
			if (i < H - 1)
				for (int k=0 ; k<20 ; k++)
					mayaFigures[k] += "\n";
		}
			
		String mayaNumber;
		long[] numbers = { 0,0 };
		for (int i=0 ; i<2 ; i++) {
			mayaNumber = "";
		    int n = in.nextInt()/H;
		    in.nextLine();
		    for (int j=0 ; j<n ; j++) {
		    	for (int k=0 ; k<H ; k++)
		    		mayaNumber += in.nextLine() + "\n";
		    	numbers[i] = numbers[i]*20 + getFigure(mayaNumber.trim());
		    	mayaNumber = "";
		    }
		}
		String op = in.next();
		long result = op.equals("+") ? numbers[0] + numbers[1] :
					  op.equals("-") ? numbers[0] - numbers[1] :
					  op.equals("*") ? numbers[0] * numbers[1] :
						 			   numbers[0] / numbers[1];
					  
		/*We know that the result cannot exceed 2^126, which is lower than 20^30 
		 *(easy to demonstrate using 20 = 2 x 2 x 5) so 30 rows will be enough
		 */
		int[] resultFigures = new int[30];
		int index           = 0;
		do {
			int figure             = (int) (result - result/20*20);
			resultFigures[index++] = figure;
			result                -= figure;
			result                /= 20;
		} while (result != 0);
		for (int i=index - 1 ; i>=0 ; i--)
			System.out.println(mayaFigures[resultFigures[i]]);
		in.close();
	} 	
}