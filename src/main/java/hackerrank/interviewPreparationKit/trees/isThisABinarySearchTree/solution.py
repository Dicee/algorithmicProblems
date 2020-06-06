# Difficulty: trivial

# https://www.hackerrank.com/challenges/ctci-is-binary-search-tree/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=trees
def check_BST(root):
    current = None
    for x in in_order_traversal(root):
        if current != None and current >= x:
            return False
        current = x    
    return True
    
# Simplest way of checking for duplicates, and is lazy (I don't know how Python makes this happen though, but I'm guessing it involves
# storing quite a few stack frames somewhere to be abe to retrieve them later on. I only used Python because Scala was not available)
def in_order_traversal(root):
    if root == None:
        return iter(())
    
    yield from in_order_traversal(root.left)
    yield root.data
    yield from in_order_traversal(root.right)
