import sys
with open(sys.argv[1],'r') as lines:
	queries = []
	nmax    = 0
	for line in lines:
		bounds = [ int(s) for s in line.split(",") ] 
		nmax   = max(nmax,bounds[1])
		queries.append(bounds)

		sieve = [ False ]*2 + [ True ]*(nmax - 1)
		for i in range(2,len(sieve)):
			if sieve[i]:
				for j in range(2*i,len(sieve),i): 
					sieve[j] = False

		for (lower,upper) in queries:
			count = 0
			for i in range(lower,upper + 1):
				if sieve[i]:
					count += 1
		print(count)