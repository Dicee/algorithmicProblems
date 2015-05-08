package codeeval.moderate.FindASquare;

import sys
import re
import math

# We will use a general algorithm working also with pointing float coordinates
EPSILON = 0.001

def cmp(d0,d1=0):
	return abs(d0 - d1) < EPSILON

class vector:
	def __init__(self,p0=0,p1=0):
		(self.x,self.y) = (p1.x - p0.x,p1.y - p0.y) if type(p0) is vector and type(p1) is vector else (p0,p1)

	def __sub__(self,v):
		return vector(v,self)
	
	def __add__(self,v):
		return vector(-self,v)
				
	def __neg__(self):
		return vector(self,vector())
	
	def dist(self,v):
		w = v - self
		return math.sqrt(w.x*w.x + w.y*w.y)	
		
	def __eq__(self,v):
		return self.x == v.x and self.y == v.y

	@staticmethod
	def mid(v,w):
		m = v + w
		return vector(m.x/2,m.y/2)

	def norm(self):
		return self.dist(vector())
		
	def cross(self,v):
		return self.x*v.x + self.y*v.y
		
	def __str__(self):
		return "({:.2f},{:.2f})".format(self.x,self.y)
		
	def __repr__(self):
		return self.__str__()

with open(sys.argv[1],'r') as lines:
	for line in lines:
		pts = [ vector(int(a),int(b)) for (a,b) in re.findall("\((\d+),(\d)+\)",line) ]

		# we choose arbitrarily two lines with the 4 points. The lines may be
		# parallel, in that case we try to swap the order and get orthogonal lines
		v0  = pts[1] - pts[0]
		v1  = pts[3] - pts[2]
		res = v0 != vector() and v1 != vector()

		if res:			
			if v0 == v1 or v0 == -v1:
				(a,b) = ((0,2),(1,3)) if v0 == v1 else ((0,3),(1,2))
				res   = pts[a[0]] - pts[a[1]] == pts[b[0]] - pts[b[1]]
			else:
				res   = cmp(v0.cross(v1)) and v0.norm() == v1.norm() and vector.mid(pts[1],pts[0]) == vector.mid(pts[3],pts[2])
		print(str(res).lower())