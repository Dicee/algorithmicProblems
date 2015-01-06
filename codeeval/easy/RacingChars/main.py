import sys
lines = open(sys.argv[1], 'r')
i    = -1
init = True
for line in lines:	
	checkpoint = line.find("C")
	gate       = line.find("_")

	if init:
		rep  = "|"
		init = False
		(ch,i) = ("C",checkpoint) if checkpoint >= 0 else ("_",gate)
	else:
		(ch,choice) = ("C",checkpoint) if checkpoint >= 0 else ("_",gate)
		rep         = "/" if choice < i else "|" if choice == i else "\\"
		i           = choice
	print(line.replace(ch,rep).strip())	
lines.close()