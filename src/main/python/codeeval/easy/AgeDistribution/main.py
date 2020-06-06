import sys
categories = [ (0,2),(3,4),(5,11),(12,14),(15,18),(19,22),(23,65),(66,100) ]
msg = [ "Still in Mama's arms","Preschool Maniac","Elementary school","Middle school","High school","College","Working for the man","The Golden Years" ]
with open(sys.argv[1],'r') as lines:
    for line in lines:
        n        = int(line)
        category = [ i for i,x in enumerate(categories) if x[0] <= n and n <= x[1] ]
        print("This program is for humans" if len(category) == 0 else msg[category[0]])
