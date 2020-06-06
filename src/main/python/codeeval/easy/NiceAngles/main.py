import sys
lines = open(sys.argv[1], 'r')
for line in lines:
	i           = line.find(".")
	decimalPart = float(line[i:])
	mins        = int(decimalPart*60)
	secs        = str((decimalPart - mins/60)*36)
	secs        = secs[secs.find(".") + 1:][:2]
	if (len(secs) == 1):
		secs = "0" + secs
	if (len(str(mins)) == 1):
	    mins = "0" + str(mins)
	print("{}.{}'{}\"".format(line[:i],mins,secs))
lines.close()