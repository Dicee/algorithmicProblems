from collections import deque

###############
# To be fixed #
###############

def solution(arr) :
    """Level : respectable"""
    def merge(arr,imin,imax) :
        count = 0
        mid   = (imin + imax) / 2 
        left  = deque(arr[imin:mid])
        right = deque(arr[mid:imax])
        i     = imin

        while len(left) != 0 and len(right) != 0 :
            if left[0] <= right[0] :
                arr[i] = left.popleft()
            else :
                arr[i] = right.popleft()
                count += 1
            i  += 1

        for k in (left if len(right) == 0 else right) :
            arr[i] = k
            i     += 1

        return count

    def merge_sort(arr,imin,imax) :
        count = 0
        if imax - imin > 1 :
            mid    = (imin + imax) / 2 
            count += merge_sort(arr,imin,mid)
            count += merge_sort(arr,mid,imax)
            count += merge(arr,imin,imax)
        return count
        
    return merge_sort(arr,0,len(arr))

       
