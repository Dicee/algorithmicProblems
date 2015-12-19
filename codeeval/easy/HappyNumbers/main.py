import sys
lines = open(sys.argv[1], 'r')
for line in lines:
	i     = int(line)
	found = {} 
	while i not in found:
		found[i] = None
		(sum,cp) = (0,i)
		while cp:
			mod  = cp % 10
			sum += mod*mod
			cp //= 10
		i = sum
	print(1 if i == 1 else 0)
lines.close()