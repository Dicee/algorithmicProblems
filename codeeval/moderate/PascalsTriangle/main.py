import sys
with open(sys.argv[1],'r') as lines:
    for line in lines:
        n      = int(line)
        res    = ["1"]
        series = [ 1 ] 

        for i in range(1,n):
            series = [ a + b for (a,b) in zip([0] + series,series + [0]) ]
            res   += [ str(i) for i in series ]
        print(" ".join(res))
