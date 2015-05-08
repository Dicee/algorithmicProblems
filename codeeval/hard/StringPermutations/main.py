package codeeval.hard.StringPermutations;

import sys

def swap(arr,i,j):
	(arr[i],arr[j]) = (arr[j],arr[i])
	
def reverse(arr,min,max):
	while (min < max):
		swap(arr,min,max)
		(min,max) = (min + 1,max - 1)
	
def sorted_right(arr,i):
	for j in range(i,len(arr) - 1):
		if arr[j] < arr[j + 1]:
			return False
	return True

with open(sys.argv[1], 'r') as lines:
	for line in lines:
		word = list(line.strip())
		word.sort()
		
		perm = list(range(0,len(word)))
		end  = list(perm)
		end.reverse()
		
		res   = [ "".join(word) ]
		index = 0
		while (perm != end):
			if index != 0 and sorted_right(perm,index):
				while index > 0 and perm[index - 1] > perm[index]:
					index -= 1
				k = index - 1
				while index < len(perm) - 1 and perm[k] < perm[index + 1]:
					index += 1

				swap(perm,index,k)
				reverse(perm,k + 1,len(perm) - 1)	
				
				swap(word,index,k)
				reverse(word,k + 1,len(word) - 1)
				
				index = k
				res.append(",")
				res.append("".join(word))
			else:
				index += 1
		print("".join(res))
lines.close()