package codeeval.moderate.ReverseAndAdd;

import sys
import math

def reverse(n):
    (j,rev) = (n,0)
    while (j > 0):
      rev  = rev*10 + j % 10
      j  //= 10
    return rev

with open(sys.argv[1],'r') as lines:
    queries = []
    nmax    = 0
    for line in lines:
        n     = int(line.strip())
        rev   = reverse(n) 
        count = 0

        while rev != n: 
            n     += rev
            count += 1
            rev    = reverse(n)
        
        print(str(count) + " " + str(n))
