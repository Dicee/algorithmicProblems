from collections import deque

def solution(H) :
    """Level : respectable"""
    height = 0
    d      = deque()    
    count  = 0

    for h in H :
        while height > h : 
            height -= d.pop()

        if height < h :
            d.append(h - height)
            height = h
            count += 1

    return count
