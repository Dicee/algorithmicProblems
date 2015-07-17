package codeeval.easy.MaxRangeSum;

import sys
from functools import reduce
with open(sys.argv[1],'r') as lines:
	for line in lines:
		split      = line.split(";")
		(days,arr) = (int(split[0]),[ int(x) for x in split[1].split(" ") ])
		max_gain   = reduce((lambda x,y: x + y),arr[:days])
		sum        = max_gain
		for i in range(days,len(arr)):
			sum     += arr[i] - arr[i - days]
			max_gain = max(max_gain,sum)
		print(max(0,max_gain))