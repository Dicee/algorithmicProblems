package codeeval.moderate.GuessTheNumber;

import sys
lines = open(sys.argv[1], 'r')
for line in lines:
	split  = line.split(" ")
	lower  = 0
	higher = int(split[0])
	mid    = (lower + higher + 1) // 2
	for hint in split[1:]:
		if hint == "Lower":
			higher = mid - 1
		elif hint == "Higher":
			lower  = mid + 1
		else:
			print(mid)			
		mid = (lower + higher + 1) // 2
lines.close()