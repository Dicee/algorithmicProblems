import sys
lines = open(sys.argv[1], 'r')
for line in lines:
	sum = 0
	for ch in line.strip():
		sum += int(ch)
	print(sum)
lines.close()