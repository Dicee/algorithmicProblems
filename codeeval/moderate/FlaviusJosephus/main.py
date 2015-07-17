package codeeval.moderate.FlaviusJosephus;

import sys
with open(sys.argv[1],'r') as lines:
	for line in lines:
		split  = [ int(s) for s in line.split(",") ]
		alive  = [True]*split[0]
		res    = []
		
		i = -1
		while len(res) != split[0]:
			count = 0
			while count < split[1]:
				i = (i + 1) % split[0]
				if alive[i]:
					count += 1
			alive[i] = False
			res.append(str(i))
		print(" ".join(res))