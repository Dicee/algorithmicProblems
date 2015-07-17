package codeeval.moderate.PrimeNumbers;

import sys
import math

with open(sys.argv[1],'r') as lines:
    queries = []
    nmax    = 0
    for line in lines:
        val  = int(line.strip())
        nmax = max(nmax,val)
        queries.append(val)

    sieve = [ False ]*2 + [ True ]*(nmax - 1)
    for i in range(2,len(sieve)):
        if sieve[i]:
            for j in range(2*i,len(sieve),i): 
                sieve[j] = False
    
    for q in queries:
        primes = [ str(i) for i,x in enumerate(sieve) if x and i <= q ]
        print(",".join(primes))
