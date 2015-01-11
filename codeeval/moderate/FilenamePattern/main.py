import sys
import re

with open(sys.argv[1],'r') as lines:
    for line in lines:
        split   = line.split(" ")
        regex   = "^" + split[0].replace("*","[^\s]*").replace(".","\.").replace("?",".") + "$"
        matcher = re.compile(regex)
        
        res     = []
        for i in range(1,len(split)):
            if re.search(matcher,split[i]):
                res.append(split[i].strip())
        print(" ".join(res) if len(res) > 0 else "-")
        
