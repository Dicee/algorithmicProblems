import sys
with open(sys.argv[1],'r') as lines:
	for line in lines:
		split  = [ int(s) for s in line.split(",") ]
		length = len(split)
		count  = 0
		for i in range(0,length):
			for j in range(i+1,length):
				for k in range(j+1,length):
					for l in range(k+1,length):
						if split[i] + split[j] + split[k] + split[l] == 0:
							count += 1
		print(count)