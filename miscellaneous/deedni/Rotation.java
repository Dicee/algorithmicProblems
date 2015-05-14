package miscellaneous.deedni;

import java.util.Scanner;

public class Rotation {
	public static void main(String[] args) {
		test("input/rotation_input1.txt");
		test("input/rotation_input2.txt");
	}

	private static void test(String path) {
		long start = System.currentTimeMillis();
		solve(path);
		System.out.println((System.currentTimeMillis() - start) + " ms");
	}

	private static void solve(String path) {
		Scanner sc = new Scanner(Rotation.class.getResourceAsStream(path));
		
		int    n = sc.nextInt();
		sc.nextLine();
		String s = sc.nextLine().substring(0,n);
		int    m = sc.nextInt();
		sc.nextLine();
		
		for (int i=0 ; i<m ; i++) {
			int l = sc.nextInt() - 1;
			int r = sc.nextInt() - 1;
			int k = sc.nextInt();
			s     = rotate(s,l,r,k);
		}
		sc.close();
		System.out.println(s);
	}
	
	static String rotate(String s, int l, int r, int k) {
		k = k % (r - l + 1);
		if (k == 0) return s;
		
		char[] chars = s.toCharArray();
		for (int i=l ; i<=r ; i++) 
			chars[l + ((i - l) + k) % (r - l + 1)] = s.charAt(i);
		return new String(chars);
	}
}
