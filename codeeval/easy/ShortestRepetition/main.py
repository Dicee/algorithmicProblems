import sys
import re

lines = open(sys.argv[1], 'r')
for line in lines:
	(end,count,res) = (0,0,1)
	for i,ch in enumerate([ x for x in line[1:] if re.match(r"[^\n ]",x) ]):
		if ch != line[count]:
			count = 0
			res  += 1
			end  += 1
		else:
			count += 1
	print(res)
lines.close()