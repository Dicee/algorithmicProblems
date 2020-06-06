import sys
from collections import Counter
import codecs

def calulate_combinations_rec(letters,nSelect):
	res = []
	if nSelect > len(letters):
		return []
	elif nSelect == 0:
		return [Counter([''])]
	elif nSelect == 1:
		return [ Counter([letter]) for letter in letters ]
	else:
		for i in range(len(letters)):
			for sub_word in calulate_combinations_rec(letters[i+1:],nSelect - 1):
				sub_word.update([letters[i]])
				res.append(sub_word)
		return res
		
def main():	
	with codecs.open("words.txt","r","utf-8") as lines:
		nSelect      = int(sys.argv[2])
		combinations = calulate_combinations_rec(sys.argv[1],nSelect)
		
		qualified = set()
		i         = 1
		for line in lines:
			word = line.strip()
			if len(word) == nSelect:
				for combination in combinations:
					sub = Counter(word)
					sub.subtract(combination)
					if set(sub.values()) == {0}:
						qualified.add(word)
						print(word," qualified")
						break
			if (i % 10000 == 0):
				print(i,"th word")
			i += 1
			
		print("Found",len(qualified),"words. Display them (y/n) ?")
		ans = input()
		if len(ans) != 0 and ans[0] == 'y':
			for word in qualified:
				print(word)
				
main()
