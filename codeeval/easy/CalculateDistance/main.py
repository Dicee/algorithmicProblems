package codeeval.easy.CalculateDistance;

import sys
import re
from math import sqrt
lines = open(sys.argv[1], 'r')
for line in lines:
	arr     = [ int(x) for x in re.split(r"[^0-9-]+",line) if len(x) > 0 ]
	(dx,dy) = (arr[0] - arr[2],arr[1] - arr[3])
	print("%.0f" % sqrt(dx*dx + dy*dy))
lines.close()