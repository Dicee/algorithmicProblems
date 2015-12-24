def solution(A):
    """Level : respectable"""
    res = start = 0
    end = 1
    sum = A[0] + A[1]
    min = sum/2.0
    for i in range(1,len(A)) :
        # if the average of the current slice to which we append the current
        # element is higher than the average of the two last elements, there
        # is no interest in keeping it in the solution slice
        if 2 * (sum + A[i]) > (end - start + 2) * (A[i] + A[i-1]) :
            start = i - 1
            end   = i
            sum   = A[i] + A[i-1]
        # in the other case, we keep going with the current slice
        else :
            sum += A[i]
            end += 1
        
        mean = float(sum)/(end - start + 1)
        if mean < min :
            min = mean
            res = start
            
    return res


