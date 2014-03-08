package codingame.medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Scrabble {

	private static class Word implements Comparable<Word> {
		public int		score	= 0;
		public int		dicoIndex;
		public String	text;
		
		public Word(String text, int dicoIndex) {
			this.text = text;
			int l     = text.length();
			for (int i=0 ; i<l ; i++)
				this.score += getScore(text.charAt(i));
			this.dicoIndex  = dicoIndex;
		}
		
		private static int getScore(Character c) {
			c = Character.toLowerCase(c);
			if (c == 'e' || c == 'a' || c == 'i' || c == 'o' || c == 'n' || 
				c == 'r' ||	c == 't' || c == 'l' || c == 's' || c == 'u')
				return 1;
			else if (c == 'd' || c == 'g')
				return 2;
			else if (c == 'b' || c == 'c' || c == 'm' || c == 'p')
				return 3;
			else if (c == 'f' || c == 'h' || c == 'v' || c == 'w' || c == 'y')
				return 4;
			else if (c == 'k')
				return 5;
			else if (c == 'j' || c == 'x')
				return 8;
			else 
				return 10;
		}

		@Override
		public int compareTo(Word w) {
			return score == w.score ? 
					Integer.compare(dicoIndex,w.dicoIndex) : 
					Integer.compare(w.score,score);
		}
	}
	
	private static boolean canWrite(Word w, char[] letters) {
		boolean[] used = new boolean[letters.length];
		String word    = w.text;
		int l          = word.length();
		int filled     = 0;
		for (int i=0 ; i<l ; i++) {
			boolean found = false;
			for (int j=0 ; j<letters.length && !found ; j++)
				if (found = letters[j] == word.charAt(i) && !used[j]) {
					used[j] = true;	
					filled++;
				}			
		}
		return l == filled;
	}
	
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		in.nextLine();
		List<Word> words = new ArrayList<>();
		for (int i = 0; i < n; i++) 
			words.add(new Word(in.nextLine(),i));
		
		Collections.sort(words);
		
		String line    = in.nextLine();
		char[] letters = new char[line.length()];
		in.close();
		for (int i=0 ; i<letters.length ; i++)
			letters[i] = line.charAt(i);
		
		for (Word w : words)
			if (canWrite(w,letters)) {
				System.out.println(w.text);
				break;
			}
	}
}