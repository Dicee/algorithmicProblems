package codeeval.moderate.OverlappingRectangles;

import sys
with open(sys.argv[1],'r') as lines:
    for line in lines:
        split     = [ int(i) for i in line.split(",") ]
        (tla,tlb) = ((split[0],split[1]),(split[4],split[5]))
        (bra,brb) = ((split[2],split[3]),(split[6],split[7]))

        (tl0,br0) = (tlb,bra) if tla[0] <= tlb[0] else (tla,brb) 
        (tl1,br1) = (tla,brb) if tla[1] <= tlb[1] else (tlb,bra)
        print(str(tl0[0] <= br0[0] and br1[1] <= tl1[1])) 
