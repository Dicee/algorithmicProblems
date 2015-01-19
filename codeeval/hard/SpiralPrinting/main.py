import sys
from math import *
def reached_goal(i,j,wbounds,hbounds,incs):

	if incs[0] != 0:
		return j == (wbounds[0] if incs[0] < 0 else wbounds[1])
	else:
		return i == (hbounds[0] if incs[1] <1 else hbounds[1])

with open(sys.argv[1], 'r') as lines:
	for line in lines:
		split = line.split(";")
		(w,h) = (int(split[1]),int(split[0]))
		arr   = split[2].strip().split(" ")
		
		(winc,hinc) = (1,    0)
		(wmin,wmax) = (0,w - 1)
		(hmin,hmax) = (0,h - 1)
		
		res = [ arr[0] ]
		i   = j = 0
		while len(res) != len(arr):
			(i,j) = (i + hinc,j + winc)				
			reached = reached_goal(i,j,(wmin,wmax),(hmin,hmax),(winc,hinc))
			res.append(arr[i*w + j])
				
			if reached:
				if winc != 0:
					winc = 0
					hinc = 1 if i == hmin else -1
					if i == hmax:
						hmax = max(hmin,hmax - 1)
					else:
						hmin = min(hmin + 1,hmax)
				else:
					hinc = 0
					winc = 1 if j == wmin else -1
					if j == wmax:
						wmax = max(wmin,wmax - 1)
					else:
						wmin = min(wmin + 1,wmax)
		print(" ".join(res))
