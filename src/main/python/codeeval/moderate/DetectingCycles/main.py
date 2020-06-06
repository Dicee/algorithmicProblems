import sys
from math import sqrt
with open(sys.argv[1], 'r') as lines:
	for line in lines:
		split  = line.strip().split(" ")
		index  = len(split) - 1
		length = 0
		for i in reversed(range(0,index)):
			if split[i] != split[index]:
				index   = len(split) - 1
			else:
				index  -= 1
				length  = index - i + 1
		print(" ".join(split[len(split) - length:]))