package codingame.easy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

class MimeType {
	
	public static HashMap<String,HashSet<String>> mimeTypes = new HashMap<>(); 

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int q = in.nextInt();
		
		in.nextLine();
				
		for (int i = 0 ; i < n ; i++) {
			String[] split = in.nextLine().trim().split(" ");
			HashSet<String> extensions = mimeTypes.get(split[1]);
			if (extensions == null) {
				extensions = new HashSet<>();
				mimeTypes.put(split[1],extensions);
			}
			extensions.add(split[0].toLowerCase());
		}

		for (int i=0 ; i<q ; i++) {
		    String s = in.nextLine();
		    int index = s.lastIndexOf('.');
			String mimeType = null;
			if (index == -1 && index < s.length() - 1)
				mimeType = "UNKNOWN";
			else {
			    s = s.substring(index + 1);
				for (Map.Entry<String,HashSet<String>> entry : mimeTypes.entrySet()){
					if (entry.getValue().contains(s.toLowerCase())) {
						mimeType = entry.getKey();
						break;
					}
				}
			}
			System.out.println(mimeType == null ? "UNKNOWN" : mimeType);
		}
		in.close();
	}
}