package codeeval.easy.MatrixRotation;

import sys
from math import sqrt
with open(sys.argv[1], 'r') as lines:
	for line in lines:
		split = line.split(" ")
		n     = int(sqrt(len(split)))
		res   = []
		for j in range(n):
			for i in reversed(range(n)):
				res.append(split[i*n + j].strip())
		print(" ".join(res))