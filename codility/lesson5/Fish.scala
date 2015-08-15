package codility.lesson5

object Solution {
    import Fish.bigger
	
    def solution(A: Array[Int], B: Array[Int]): Int = {
		def recSolution(fishes: List[Fish], dsFishes: List[Fish], count: Int): Int = fishes match {
			case up :: tail if up.direction == Upstream => dsFishes match {
				case ds :: q => if (bigger(ds, up) == up) recSolution(fishes, q, count) else recSolution(tail, dsFishes, count)
				case Nil     => recSolution(tail, Nil, count + 1)	
			}
			case down :: tail => recSolution(tail, down :: dsFishes, count)
			case Nil          => count + dsFishes.length
		}
		val fishes = A.zip(B).map { case (size, dir) => Fish(size, Direction.fromInt(dir)) }.toList
        recSolution(fishes, Nil, 0)
    }
	
	abstract class Direction
	object Direction {
		def fromInt(value: Int) = if (value == 0) Upstream else Downstream
	}
	case object Upstream extends Direction
	case object Downstream extends Direction
	
	case class Fish(val size: Int, val direction: Direction)
	object Fish {
		def bigger(fish0: Fish, fish1: Fish) = if (fish0.size < fish1.size) fish1 else fish0 
	}
}