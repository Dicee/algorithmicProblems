package codeeval.hard.StringSearching;

import sys
with open(sys.argv[1], 'r') as lines:
	def match(s,pattern):
		i       = 0
		escaped = False
		for j in range(len(s)):
			if i == len(pattern) or (not escaped and i == len(pattern) - 1 and pattern[i] == '*'):
				return "true"
			elif pattern[i] == '\\':
				escaped = True
				if pattern[i+1] == s[j]:
					i += 2
				else:
					i = 0
			elif not escaped and pattern[i] == '*':
				i += 2 if pattern[i+1] == s[j] else 0
			elif pattern[i] == s[j]:
				if pattern[i] == '*':
					escaped = False
				i += 1
		return str(i == len(pattern)).lower()
		
	for line in lines:
		split = line.split(",")
		print(match(split[0],split[1].strip()))
lines.close()