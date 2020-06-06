import sys
import math

with open(sys.argv[1],'r') as lines:
    for line in lines:
        split = line.split(";")
        arr   =  [ int(s) for s in split[0].split(",") ]
        n     = int(split[1])   
        res   = []
        
        for i,x in enumerate(arr):
            if x >= arr[-1]:
                break
            for j in range(i,len(arr)):
                sum = x + arr[j] 
                if sum > n:
                    break
                elif sum == n:
                    res.append("{:d},{:d}".format(x,arr[j]))
        print("NULL" if len(res) == 0 else ";".join(res))
