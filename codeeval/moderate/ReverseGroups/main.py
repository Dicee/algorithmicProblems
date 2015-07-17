package codeeval.moderate.ReverseGroups;

import sys
with open(sys.argv[1],'r') as lines:
    for line in lines:
        split = line.split(";")
        n     = int(split[1])
        arr   = split[0].split(",")
        res   = [ None ]*len(arr)

        q     = len(arr) // n
        for i in range(q):
            res[i*n:(i+1)*n] = reversed(arr[i*n:(i+1)*n])

        res[q*n:] = arr[q*n:]
        print(",".join(res))

