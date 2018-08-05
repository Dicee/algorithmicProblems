package hackerrank.interviewPreparationKit.miscellaneous.maximumXor

// Difficulty: medium. The implementation is easy but you need to get the right idea to solve it efficiently. I really enjoyed it, 
//             it's one of these problems that is sufficiently elegant to be satisfying to solve, while also not being too hard.

// https://www.hackerrank.com/challenges/maximum-xor/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=miscellaneous
object Solution {
    // all values are guaranteed to be lower than 10^9. Could limit to the maximum bit size of the values in the array, but overkill for this problem
    private val NumBits = 30

    def maxXor(arr: Array[Int], queries: Array[Int]): Array[Int] = {
        val prefixTree = new BinaryPrefixTree
        for (value <- arr) prefixTree.insert(value)
        queries.map(prefixTree.maximumXor)        
    }
    
    private class BinaryPrefixTree {
        private val root = new Node()
        
        def insert    (value: Int): Unit = root.insert    (value, 1 << NumBits)
        def maximumXor(value: Int): Int  = root.maximumXor(value, 1 << NumBits, 0)
        
        private class Node {
            private val children = Array[Node](null, null)
            
            def insert(value: Int, mask: Int): Unit = if (mask != 0) {
                val bit = if ((value & mask) > 0) 1 else 0       
                if (children(bit) == null) children(bit) = new Node
                children(bit).insert(value, mask >> 1)
            } 
            
            def maximumXor(value: Int, mask: Int, acc: Int): Int = 
                if (mask == 0) acc
                else {
                    val masked     = value & mask
                    val bit        = if (masked > 0) 1 else 0
                    val optimalBit = bit ^ 1 // XOR is optimal if the left bit is the opposite of the right bit
                    if (children(optimalBit) != null) children(optimalBit).maximumXor(value, mask >> 1, acc + mask)
                    else if (children(bit) != null) children(bit).maximumXor(value, mask >> 1, acc)
                    else throw new IllegalStateException("All leaves should be at depth ${Solution.NumBits}")
                }
        }
    }
}
