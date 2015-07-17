package codeeval.easy.PenultimateWord;

import sys
lines = open(sys.argv[1], 'r')
for line in lines:
	print(line.split(" ")[-2])
lines.close()