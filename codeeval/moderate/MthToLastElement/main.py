import sys
# Do you really call that a moderate problem ? Damn... the difficulty of CodeEval's 
# "challenges" is so poorly evaluated... And not only for this one...
with open(sys.argv[1], 'r') as lines:
	for line in lines:
		split = line.split(" ")
		k     = int(split[-1])
		arr   = split[:-1]
		if k <= len(arr):
			print(arr[len(arr) - k])