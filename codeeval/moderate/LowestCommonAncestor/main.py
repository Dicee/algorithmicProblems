package codeeval.moderate.LowestCommonAncestor;

import re
import sys

"""It wasn't necessary to code a whole class but it was a little exercise for me
who have so few manipulated OOP in Python"""
class BinarySearchTree:	
	def __init__(self,root=None):
		self.root = root
		
	def __iadd__(self,value):
		to_add = Node(value)
		
		if self.root == None:
			self.root = to_add
			return self
		
		node = self.root
		while True:
			if value < node.value:
				if node.left == None:
					node.left = to_add
					return self
				else:
					node = node.left
			elif node.right == None:
				node.right = to_add
				return self
			else:
				node = node.right
		
	def __str__(self):
		return self.root.__str__()
		
	def __repr__(self):
		return self.__str__()
		
class Node:
	def __init__(self,value,left=None,right=None):
		self.value = value
		self.left  = left
		self.right = right
		
	def __str__(self):
		return self._str("")
		
	def _str(self,indent):
		def _str_child(child):
			return indent + "\t" + str(child) if child == None else child._str(indent + "\t")
		return "{}{:d}\n{}\n{}".format(indent,self.value,_str_child(self.left),_str_child(self.right))

with open(sys.argv[1], 'r') as lines:
	build = [ 30,8,52,3,20,10,29 ]
	bst   = BinarySearchTree()
	for i in build:
		bst += i
	for line in lines:
		split   = line.split(" ")
		x       = int(split[0])
		y       = int(split[1])
		
		node    = bst.root
		(dx,dy) = (x - node.value,y - node.value)
		while dx*dy > 0:
			node = node.left if dx < 0 else node.right
			(dx,dy) = (x - node.value,y - node.value)
		print(node.value)		