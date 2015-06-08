package miscellaneous.utils.files;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TokenReader extends BufferedReader {
	
	private List<Character> token;

	public TokenReader(Reader reader, String token) throws FileNotFoundException {
		super(reader);
		this.token = token.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
	}
	
	public String readToNextToken() throws IOException {
		StringBuilder    result = new StringBuilder();
		Deque<Character> delim  = new LinkedList<>();
		int              len    = token.size();
		
		int c;
		while ((c = read()) != -1 && !delim.equals(token)) {
			if (delim.size() >= len) 
				delim.pollFirst();
			char ch = (char) c;
			result.append(ch);
			delim.addLast(ch);
		}
		return result.length() < len ? null : result.substring(0,result.length() - len);
	}
}