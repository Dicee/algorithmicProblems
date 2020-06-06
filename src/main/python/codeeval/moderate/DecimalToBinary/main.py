import sys
import re

with open(sys.argv[1],'r') as lines:
    for line in lines:
        n   = int(line)
        j   = n
        res = []
        
        while j > 0:
        	res.append(str(j % 2))
        	j //= 2
        
        print("".join(reversed(res)))
