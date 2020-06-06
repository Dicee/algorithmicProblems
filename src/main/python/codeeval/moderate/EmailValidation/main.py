import re
import sys
matcher = re.compile('^"[\w_\-+\.@]+"|[\w_\-+\.?]*@{1}[a-z0-9]+\.{1}[a-z0-9-]+\.?[a-z0-9-]{2,}')
with open(sys.argv[1], 'r') as lines:
	for line in lines:
		print("true " if re.match(matcher,line) != None else "false ")