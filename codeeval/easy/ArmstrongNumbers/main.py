package codeeval.easy.ArmstrongNumbers;

import sys
import math
with open(sys.argv[1],'r') as lines:
	for line in lines:
		line = line.strip()
		n    = int(line)
		cp   = n
		while cp != 0:
			n   -= int(math.pow(cp % 10,len(line)))
			cp //= 10
		print(n == 0)