import sys
with open(sys.argv[1],'r') as lines:
	for line in lines:
		letters = {}
		for ch in line:
			if not ch in letters:
				letters[ch] = 1
			else:
				letters[ch] += 1
		for ch in line:
			if letters[ch] == 1:
				print(ch)
				break
