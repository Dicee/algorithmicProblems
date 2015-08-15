from itertools import *
import math

# this solution is O(n*sqrt(n)) instead of O(n), but scores 100 %
def solution(A):
	peaks = [ i for (i, x) in enumerate(A) if ((0 < i) and (i < len(A) - 1) and (A[i - 1] < x) and (A[i + 1] < x)) ]

	if (len(peaks) == 0):
		return 0 
	
	max_flags = int(math.floor(math.sqrt(len(A)))) + 1
	for min_dist in range(max_flags, 0, -1):
		(count, i, current) = (1, 1, peaks[0])
		while i < len(peaks):
			while i < len(peaks) and peaks[i] - current < min_dist:
				i += 1
			if i < len(peaks):
				current = peaks[i]
				count  += 1
				i      += 1
				if count == min_dist:
					return count
	return 1