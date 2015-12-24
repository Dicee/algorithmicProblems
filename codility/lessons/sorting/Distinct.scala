package codility.lessons.sorting

/**
 * Level : painless
 */
object DistinctScala {
    object Solution {
        // I know this is cheating, but why would I sort the array if I can do more efficient ? This problem
        // is pointless anyway
        def solution(arr: Array[Int]) = arr.toSet.size
    }
}