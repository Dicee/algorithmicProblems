import sys
with open(sys.argv[1],'r') as lines:
	corresponding = { ")":"(", "]":"[", "}":"{", "(":")", "[":"]", "{":"}" }
	for line in lines:
		stack   = []*len(line)
		correct = True
		for ch in line.strip():
			if ch in "([{":
				stack.append(ch)
			else:
				correct = len(stack) != 0 and corresponding[stack.pop()] == ch
			if not correct:
				break
		correct = correct and len(stack) == 0
		print(correct)