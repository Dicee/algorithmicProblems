import sys
with open(sys.argv[1],'r') as lines:
    for line in lines:
        alphabet = set("abcdefghijklmnopqrstuvwxyz")
        for ch in line.lower():
            if ch in alphabet:
                alphabet.remove(ch)

        res = list(alphabet)
        res.sort()
        print("NULL" if len(res) == 0 else "".join(res))
