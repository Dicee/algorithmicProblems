import sys

lines = open(sys.argv[1], 'r')
for line in lines:
	(count,res) = (0,1)
	for ch in line.strip()[1:]:
		if ch != line[count]:
			count = 0
			res  += 1
		else:
			count += 1
	print(res)
lines.close()
