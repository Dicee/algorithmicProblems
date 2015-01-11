import sys
with open(sys.argv[1], 'r') as lines:
	for line in lines:
		arr = list(map(lambda x: int(x.strip()),line.split(",")))
		
		index = 0
		for i,x in enumerate(arr):
			if x > 0:
				index = i
				break
			elif x > arr[index]:
				index = i
		
		if arr[index] <= 0:
			print(arr[index])
		else:
			sum = arr[index]
			max = sum
			for i in range(index + 1,len(arr)):
				if sum + arr[i] < 0:
					sum = 0
				else:
					sum += arr[i]
					max  = max if max > sum else sum
			print(max)