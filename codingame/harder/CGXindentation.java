package codingame.harder;

import java.util.Scanner;

public class CGXindentation {

	private static String	tab	= "    ";

	public static int nextNotEmptyChar(String s, int index) {
		int l = s.length();
		while (index < l && (s.charAt(index) == ' ' || s.charAt(index) == '\t'))
			index++;
		return index == l ? -1 : index;
	}

	public static void main(String args[]) {
		Scanner in  = new Scanner(System.in);
		in.nextLine();
		
		StringBuilder CGXindentation = new StringBuilder();
		String align                 = "";
		
		boolean opennedString = false;
		boolean followingKeyValue = false;
		boolean newLine           = true;
		while (in.hasNext()) {
			String line = in.nextLine();
			int l       = line.length();
			int i       = 0;
			while (i < l) {
				switch (line.charAt(i)) {
					case '(' :
						if (!opennedString) {
						    if (!newLine)
							    CGXindentation.append("\n");
							CGXindentation.append(align + "(\n");
							align += tab;
							newLine = true;
							
							if (followingKeyValue)
						        followingKeyValue = false;
						} else 
							CGXindentation.append("(");
						break;	
						
					case ')' : 
						if (!opennedString) {
							align = align.substring(4);
							if (!newLine)
							    CGXindentation.append("\n");
							CGXindentation.append(align + ")");
							newLine = false;
						} else 
							CGXindentation.append(")");
						break;
						
					case '\'' :
						if (opennedString) {
							int next = nextNotEmptyChar(line,i+1);
							if (next != -1 && (followingKeyValue = line.charAt(next) == '=')) {
								CGXindentation.append("'=");
								i = next;
							} else
								CGXindentation.append("'");					
						} else if (!followingKeyValue) {
						    String hopLine = newLine ? "" : "\n";
						    newLine = false;
							CGXindentation.append(hopLine + align + "'");
						}
						else {
						    if (newLine) {
						        followingKeyValue = false;
						        CGXindentation.append(align);
						        newLine = false;
						    }
							CGXindentation.append("'");
						}
						opennedString = !opennedString;
						break;
						
					case ' ' : 
						if (opennedString) CGXindentation.append(" ");
						break;
						
					case '\t' :
					    if (opennedString) CGXindentation.append("\t");
						break;
						
					case ';' :
					    if (!opennedString) {
					        CGXindentation.append(";\n");
					        newLine = true;
					    }
					    break;
					    
					default : 
					    String indent = newLine ? align : "";
					    if (newLine)
					        newLine = false;
					    CGXindentation.append(indent + line.charAt(i));
					    break;
				}
				i++;
			}
		}
		in.close();
		System.out.println(CGXindentation.toString());
	}
}