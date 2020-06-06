import sys
with open(sys.argv[1],'r') as lines:
	for line in lines:
		print(min([ s.index('Y') - s.rfind('X') - 1 for s in line.split(",") ]))