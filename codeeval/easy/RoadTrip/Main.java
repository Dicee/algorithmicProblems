import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) throws IOException {
		Pattern        p    = Pattern.compile("\\d+");
		File           file = new File(args[0]);
		BufferedReader br   = new BufferedReader(new FileReader(file));
		
		StringBuilder     sb   = new StringBuilder();
		Set<Integer> dists = new TreeSet<Integer>();
		
		String line;
		while ((line = br.readLine()) != null) {
			Matcher      m     = p.matcher(line); 
			while (m.find()) dists.add(Integer.parseInt(m.group()));
			
			Iterator<Integer> it   = dists.iterator();
			int               prev = 0;
			while (it.hasNext()) {
				int curr = it.next();
				sb.append(curr - prev);
				sb.append(it.hasNext() ? "," : "");
				prev = curr;
			}
			System.out.println(sb);
			sb.setLength(0);
			dists.clear();
		}
		br.close();
	}
}