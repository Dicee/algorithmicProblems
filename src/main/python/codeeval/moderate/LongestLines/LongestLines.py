import sys
from heapq import *

class StringWrapper:
	def __init__(self,s):
		self.s = "".join([ x for x in s if x != "\n" ])
	
	def __eq__(self,that):
		return len(self.s) == len(that.s)
		
	def __lt__(self,that):
		return len(self.s) < len(that.s)
		
	def __gt__(self,that):
		return len(self.s) > len(that.s)
		
	def __le__(self,that):
		return len(self.s) <= len(that.s)
		
	def __ge__(self,that):
		return len(self.s) >= len(that.s)
		
	def __str__(self):
		return self.s
		
	def __repr__(self):
		return self.s

if __name__ == "__main__":
	n     = 0
	heap  = []
	lines = open(sys.argv[1], 'r')
	for line in lines:
		if n == 0:
			n = int(line)
		else:
			wrapper = StringWrapper(line)
			if len(heap) == n:
				if wrapper >= heap[0]:
					heapreplace(heap,wrapper)
			else:
				heappush(heap,wrapper)
			
	res = [0]*len(heap)
	i   = len(heap) - 1
	while len(heap) > 0:
		res[i] = heappop(heap).s
		i     -= 1
	print("\n".join(res))
	lines.close()