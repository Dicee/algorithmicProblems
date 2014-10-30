/**
 * Level : painless
 */
object Solution {
    def solution(T : Tree) : Int = if (T == null) -1 else 1 + Math.max(solution(T.l),solution(T.r))    
}
