package hackerrank.algorithms.gameTheory.kittyAndKatty

// Difficulty: fairly easy. The final solution is ridiculously simple but I've had fun reasoning about the problem on a
//             sheet of paper until it becomes clear that the solution was so trivial. Here's how I solved it:
//
//             The problem can be hugely simplified by using properties of the modulo operator. In particular, for any
//             integers (a, b), (a % n = k and b % n = j) => (a + b) % n = (k + j) % n
//
//             Therefore, for any integer k <= n, the state of boxes B = (b1, b2, ..., bk, ..., bn) is equivalent to the
//             problem (b1, b2, ..., bk + 3*sigma, ..., bn) where sigma is either 1 or -1. This means the problem can
//             actually be represented by three integers (t0, t1, t2) where ti = card({b in B, b % 3 = i}).
//
//             My original idea was to use DP to store the solution for each triplet with sum 2, 3, etc. However, there
//             are (n + 1)(n + 2)/2 distinct triplets such that t0 + t1 + t2 = n (can be derived with simple combinatorial
//             calculus). Since n < 10^5, this would lead us to store up to 5 billion solutions just for all problems of
//             size n. This is still way too much. Therefore, I tried looking for relationships between triplets to see
//             if I could reduce the number of distinct configurations.
//
//             Starting with n = 2. All possible triplets are the following: (0, 0, 2), (0, 1, 1), (0, 2, 0), (1, 0, 1),
//             (1, 1, 0), (2, 0, 0). It is trivial to show that for all of these configurations there is a winning move:
//
//             - (0, 0, 2), (0, 2, 0) and (2, 0, 0) correspond to the case when A % 3 = B % 3 <=> (A - B) % 3 = 0, which
//               gives a winning move to the player who makes it since it's the last move (n = 2).
//
//             - (1, 1, 0) and (0, 1, 1) both give the choice to output A - B = C such that C % 3 in {1, -1} which is
//               equivalent to C % 3 in {1, 2}. Therefore, whoever is the current player, there's always an option to play
//               a winning move in those two cases.
//
//             - similarly, (1, 0, 1) gives the choice between {2, -2} <=> {2, 1}, hence the same conclusion as above
//
//             We've proven that for n = 2, there is always a winning move independently of who is playing it ! That means
//             that for all n >= 2, the last player always wins, which boils down to knowing the parity of n. The only edge
//             case is n = 1, which is odd but is still a win for Kitty (trivial).
//
//             We thus have the final solution: if (n == 1 || n % 2 == 0) "Kitty" else "Katty"

// https://www.hackerrank.com/challenges/kitty-and-katty/problem
object Solution {
  def main(args: Array[String]) {
    scala.io.Source.stdin.getLines().drop(1).map(_.toInt).map(n => if (n == 1 || n % 2 == 0) "Kitty" else "Katty").foreach(println)
  }
}
