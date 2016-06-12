package codeeval.hard.FollowingInteger

import scala.annotation.tailrec

/**
 * For performance, everything should be done in an array and only ever swap values within the array, except in the case we need to add a zero.
 * However, performance is not a concern here since the input is very small, so I'll use a more functional style.
 */
object Main extends App {
	@tailrec
	def reverseInsertingZero(nonZeros: List[Int], suffix: List[Int]): List[Int] = (nonZeros, suffix) match {
		case (t :: Nil, z) => t :: 0 :: z
		case (t ::   q, z) => reverseInsertingZero(q, t :: z)
		case (Nil     , _) => throw new IllegalArgumentException("At least one non-zero digit required")
	}

	def successorWithoutInversion(digitsWithInversion: List[Int]): List[Int] = {
		val head = digitsWithInversion.head
		val smallestBiggerThanHead = digitsWithInversion.tail.lastIndexWhere(_ > head)
		// not super efficient but the lists are small in this problem anyway (length < 6)
		digitsWithInversion.tail.splitAt(smallestBiggerThanHead) match { case (left, newHead :: q) => newHead :: q.reverse ::: (head :: left.reverse) }
	}

	scala.io.Source.fromFile(args(0)).getLines()
		.map(line => {
			val digits = line.map(_ - '0').toList
			val lastInversionIndex = (Int.MaxValue :: digits).zip(digits).lastIndexWhere { case (prev, current) => current > prev  }

			val newDigits =
				if (lastInversionIndex == -1) {
					digits.span(_ != 0) match { case (nonZeros, zeros) => reverseInsertingZero(nonZeros, zeros) }
				} else {
					digits.splitAt(lastInversionIndex - 1) match { case (prefix, suffixWithFirstInversion) => prefix ::: successorWithoutInversion(suffixWithFirstInversion) }
				}

				newDigits.mkString.toInt
		})
		.foreach(println)
}
