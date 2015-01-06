import sys
lines = open(sys.argv[1], 'r')
for line in lines:
	split = line.split(",")
	(x,y) = (int(split[0]),int(split[1]))
	res   = y
	
	while res < x :
		res += y
	print(res)
lines.close()