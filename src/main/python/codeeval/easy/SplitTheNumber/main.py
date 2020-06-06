import sys
lines = open(sys.argv[1], 'r')
for line in lines:
	split = line.split(" ")
	arr   = [ x.strip() for x in split[1].split("+" if "+" in split[1] else "-") ]
	(a,b) = (int(split[0][:len(arr[0])]),int(split[0][len(arr[0]):]))
	print((a + b if "+" in split[1] else a - b))
lines.close()