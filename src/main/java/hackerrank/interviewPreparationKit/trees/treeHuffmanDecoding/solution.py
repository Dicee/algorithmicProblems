# Difficulty: trivial

# https://www.hackerrank.com/challenges/tree-huffman-decoding/problem?h_l=interview&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=trees
def decodeHuff(root, s):
    decoded_chars = []
    current_node = root
    for ch in s:
        if current_node.left == None and current_node.right == None:
            decoded_chars.append(current_node.data)
            current_node = root
       
        current_node = current_node.left if ch == '0' else current_node.right
    
    decoded_chars.append(current_node.data)
    print("".join(decoded_chars))
