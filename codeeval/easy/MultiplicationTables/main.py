package codeeval.easy.MultiplicationTables;

for i in range(1,13):
	for j in range(1,13):
		s = str(i*j);
		print(" " * (4 - len(s)) + s,end = "")
	print()
