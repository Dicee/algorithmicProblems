def solution(S, P, Q) :
    """Level : respectable"""
    impact_dict = { "A":1,"C":2,"G":3,"T":4 }  
    values      = [ impact_dict[s] for s in S ]
    positions   = [ [],[],[],[] ]
    
    for value in range(1,5) :
        pos = -1
        for i in reversed(range(len(values))) :
            if values[i] == value :
                pos = i
            positions[value - 1].append(pos)
        positions[value - 1].reverse()
           
    result = [] 
    for i in range(len(P)) :
        for j in range(4) :
            next = positions[j][P[i]]
            if next != -1 and next <= Q[i] :
                min = j + 1
                break
        result.append(min)   
        
    return result
