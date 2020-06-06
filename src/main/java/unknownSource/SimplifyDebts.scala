package unknownSource

import scala.collection.mutable
import scala.math._

/**
 * Given a set of debts from one person to another, returns an alternative set of debts allowing all
 * accounts to be settled in an optimal number of transactions. Thought about this after using the Splitwise
 * application.
 */
object SimplifyDebts {
  def simplifyDebts(debts: List[Debt]) = {
    val nodes = debts.view
      .flatMap { case Debt(from, to, _) => List(from, to) }
      .distinct
      .map(name => (name, new Node(name)))
      .toMap

    debts.foreach { case Debt(from, to, amount) => nodes(from) give (nodes(to), amount) }

    for (node <- nodes.values; if node.balance >= 0) node.sellAllDebts()
    nodes.values.foreach(_.simplifyBidirectionalDebts())
    nodes.values.flatMap(node => node.debts).toList
  }

  private class Node(val name: String) {
    private val in = mutable.HashMap[Node, Int]()
    private val out = mutable.HashMap[Node, Int]()

    def balance = in.values.sum - out.values.sum
    def debts = out.map { case( to, amount) => Debt(name, to.name, amount) }.toList

    def give(receiver: Node, amount: Int): Unit = {
      val debt = out.getOrElse(receiver, 0) + amount
      this    .out += receiver -> debt
      receiver.in  += this     -> debt
    }

    def reimburse(receiver: Node, amount: Int): Unit = {
      val debt = out(receiver) - amount
      assert(debt >= 0)
      if (debt == 0) this    .out -= receiver else this    .out += receiver -> debt
      if (debt == 0) receiver.in  -= this     else receiver.in  += this     -> debt
    }

    def sellAllDebts(): Unit = {
      assert(balance >= 0)
      while (out.nonEmpty) {
        val (giver   , credit) = in .iterator.next()
        val (receiver, debit ) = out.iterator.next()

        val debtToSell = min(debit, credit)
        giver give      (receiver, debtToSell)
        giver reimburse (this    , debtToSell)
        this  reimburse (receiver, debtToSell)
      }
    }

    def simplifyBidirectionalDebts(): Unit = {
      for ((node, received) <- in) {
        out.get(node).foreach(given => {
          val exchange = min(given, received)
          this reimburse (node, exchange)
          node reimburse (this, exchange)
        })
      }
    }
  }

  case class Debt(from: String, to: String, amount: Int) {
    override def toString: String = s"$from owes ${amount}£ to $to"
  }

  def main(args: Array[String]): Unit = {
    val debts = List(
      Debt("David", "Baptiste", 30),
      Debt("David", "Camille", 20),
      Debt("Julia", "Baptiste", 60),
      Debt("Julia", "David", 60),
      Debt("Julia", "Camille", 40),
      Debt("Julia", "Lucie", 40),
      Debt("Lucie", "Julia", 100),
      Debt("Baptiste", "Camille", 20),
      Debt("Camille", "Baptiste", 40)
    )

    println(s"Initial debts:\n${debts.mkString("- ", "\n- ", "\n")}")
    val simplifiedDebts = simplifyDebts(debts)
    println(s"Simplified debts:\n${simplifiedDebts.mkString("- ", "\n- ", "\n")}")

//    Initial debts:
//      - David owes 30£ to Baptiste
//      - David owes 20£ to Camille
//      - Julia owes 60£ to Baptiste
//      - Julia owes 60£ to David
//      - Julia owes 40£ to Camille
//      - Julia owes 40£ to Lucie
//      - Lucie owes 100£ to Julia
//      - Baptiste owes 20£ to Camille
//      - Camille owes 40£ to Baptiste
//             
//    Simplified debts:
//      - Julia owes 110£ to Baptiste
//      - Julia owes 40£ to Camille
//      - Julia owes 10£ to David
//      - Lucie owes 60£ to Julia
  }
}
