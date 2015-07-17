package codeeval.moderate.Sudoku;

import sys
import math
with open(sys.argv[1],'r') as lines:
	def getRow(grid,i):
		return [ grid[i][j] for j in range(0,len(grid)) ]
			
	def getCol(grid,j):
		return [ grid[i][j] for i in range(0,len(grid)) ]
		
	def cartesian_product(range0,range1):
		res = []
		for i in range0:
			for j in range1:
				res.append((i,j))
		return res
		
	def getSquare(grid,i,j):
		length = int(math.sqrt(len(grid)))
		return [ grid[k][l] for (k,l) in cartesian_product(range(i*length,(i + 1)*length),range(j*length,(j + 1)*length)) ]
		
	def isValid(block):
		to_set = set(block)
		return len(to_set) == len(block) and all(1 <= i and i <= len(block) for i in to_set)
		
	for line in lines:
		split = line.split(";")
		n     = int(split[0])
		grid  = [None]*n
		
		for i,x in enumerate([ int(y) for y in split[1].split(",") ]):
			if (i % n == 0):
				grid[i//n] = [0]*n
			grid[i//n][i % n] = x
			
		m   = int(math.sqrt(len(grid)))
		res = True
		for i in range(m):
			for j in range(m):
				res = all(isValid(block) for block in [ getRow(grid,i*m + j),getCol(grid,i*m + j),getSquare(grid,i,j) ])
				if not res:
					break
			if not res:
				break
		print(res)