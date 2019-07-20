package hackerrank.algorithms.search.hackerlandRadioTransmitters

// Difficulty: fairly simple. This is a O(n.log(n)) greedy solution since Range.contains runs in constant time.
//             I haven't tried optimizing beyond that as it was passing all the tests already.

// https://www.hackerrank.com/challenges/hackerland-radio-transmitters/problem
object Solution {
    def hackerlandRadioTransmitters(houses: Array[Int], maxRange: Int): Int = {
        def centeredRange(house: Int) = Range.inclusive(house - maxRange, house + maxRange)

        @tailrec
        def recSol(house: Int, range: Range, remainingHouses: List[Int], acc: Int): Int = remainingHouses match {
            case h :: t => 
                val candidateRange = centeredRange(h)
                if (candidateRange contains house) recSol(house, candidateRange, t,     acc)
                else if (range contains h)         recSol(house, range,          t,     acc)
                else                               recSol(h    , candidateRange, t, 1 + acc) 
            case Nil    => 1 + acc
        }
        
        if (houses.isEmpty) 0 else {
            Sorting.quickSort(houses)
            val h = houses.head
            recSol(h, centeredRange(h), houses.toList.tail, 0)
        }
    }
}
