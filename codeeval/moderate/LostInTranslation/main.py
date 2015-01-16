import sys
with open(sys.argv[1],'r') as lines:
	s0     = "rbc vjnmkf kd yxyqci na rbc zjkfoscdd ew rbc ujllmcp thg"
	s1     = "the public is amazed by the quickness of the juggler wxv"
	decode = {}
	for i in range(len(s0)):
		decode[s0[i]] = s1[i]
		
	for line in lines:
		print("".join([ decode[ch] for ch in line.strip() ]))
