from collections import deque

def solution(A, B):
    """Level : respectable"""
    count   = len(A)
    dstream = deque()
    ustream = deque()
    for i in range(len(A)) : 
        if B[i] == 0 and len(dstream) != 0 :
            ustream.append(A[i])
        if B[i] == 1 :
            dstream.append(A[i])

        while len(ustream) != 0 and len(dstream) != 0 :
            if ustream[-1] < dstream[-1] :
                ustream.pop()
            else :
                dstream.pop()
            count -= 1
            
            if len(dstream) == 0 :
                ustream = deque()

    return count

