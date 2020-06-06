import sys
lines = open(sys.argv[1], 'r')
for line in lines:
	found = {}
	for i,x in enumerate([ int(x) for x in line.split(" ") ]):
		found[x] = (i + 1,1) if x not in found else (i + 1,found[x][1] + 1)
	
	min   = 10
	imin  = 0
	for (k,i) in [ (k,v[0]) for k,v in found.items() if v[1] == 1]:
		if k <= min: 
			min  = k
			imin = i
	print(imin)
lines.close()