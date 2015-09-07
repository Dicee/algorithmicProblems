package miscellaneous.skiller.indexation.model;

import static java.util.stream.Collectors.toMap;
import static com.dici.check.Check.notNull;

import java.util.Map;
import java.util.stream.IntStream;

import com.dici.check.Check;

public class DescriptionFormatter {
	private final String	unformattedDescription;
	private String[]		paramNames;

	public DescriptionFormatter(String unformattedDescription, String... paramNames) {
		this.unformattedDescription = notNull(unformattedDescription);
		this.paramNames             = notNull(paramNames);
	}
	
	public String format(Object... params) {
		Check.areEqual(params.length,paramNames.length);
		
		Map<String,Object> paramsMap = IntStream.range(0,params.length).boxed().collect(toMap(i -> paramNames[i],i -> params[i]));
		String formatted = unformattedDescription;
		for (String name : paramNames) formatted = formatted.replaceAll(findNameRegex(name),paramsMap.get(name).toString());
		return formatted;
	}
	
	private String findNameRegex(String name) { return String.format("\\$\\{(%s)\\}",name); }
}
