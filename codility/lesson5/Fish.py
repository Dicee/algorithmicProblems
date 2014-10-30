from collections import deque

def solution(A, B):
    """Level : respectable"""
    
    # find the fishes that will never meet other fishes
    first = -1
    while first + 1 < len(B) and B[first + 1] == 0 :
        first += 1
    
    ustream = deque()
    dstream = deque()
    
    for i in range(first + 1,len(A)) :
        if B[i] == 0 :
            ustream.appendleft(A[i])
        else :
            dstream.appendleft(A[i])
    
    living = len(A)
    while (len(ustream) != 0 and len(dstream) != 0) :
        if ustream[-1] < dstream[0] :
            ustream.pop()
        else :
            dstream.popleft()
        living -= 1
        
    return living


