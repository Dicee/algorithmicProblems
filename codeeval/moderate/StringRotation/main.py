import sys
with open(sys.argv[1],'r') as lines:
	for line in lines:
		split                    = [ s.strip() for s in line.split(",") ]
		first_letter_occurrences = [ i for (i,ch) in enumerate(split[1]) if ch == split[0][0] ]
		length                   = len(split[0])
		found                    = False
		for i in first_letter_occurrences:
			found = split[0][0:length - i] == split[1][i:] and split[0][length - i:] == split[1][:i]
			if found:
				break
		print(found)
