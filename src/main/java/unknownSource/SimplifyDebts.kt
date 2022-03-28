package unknownSource

import java.math.BigDecimal
import java.util.*

const val DAVID = "David"
const val SARA = "Sara"
const val THAMI = "Thami"
const val ZOUZOU = "Zouzou"

/**
 * Simplification of the Scala version we made in the same git repo (no need to use a graph), and fixing some bugs we had in it
 */
fun main() {
    val project = Project(setOf(DAVID, SARA, THAMI, ZOUZOU))
    project.addExpense(SARA, BigDecimal("251.42"))
    project.addExpense(ZOUZOU, BigDecimal("144"))
    project.addExpense(DAVID, BigDecimal("77"))

    println(project.simplifiedDebts())
}

private class Project(val actors: Set<String>) {
   private val debts = mutableListOf<Debt>()

    fun addExpense(actor: String, amount: BigDecimal, debtors: Set<String> = actors) {
        val due = amount.divide(BigDecimal(debtors.size))
        debtors.asSequence().filter { it != actor }.forEach { debts += Debt(it, actor, due) }
    }

    fun simplifiedDebts(): List<Debt> {
        val simplifiedDebts = mutableListOf<Debt>()
        val balances = computeBalances(debts)

        while (balances.isNotEmpty()) {
            val largestCreditor = balances.pollLast()!!
            val largestDebtor = balances.pollFirst()!!

            val transfer = minOf(largestCreditor.amount, -largestDebtor.amount)
            simplifiedDebts += Debt(largestDebtor.actor, largestCreditor.actor, transfer)

            updateBalance(largestCreditor, -transfer, balances)
            updateBalance(largestDebtor, transfer, balances)
        }

        return simplifiedDebts
    }

    private fun computeBalances(debts: List<Debt>): NavigableSet<Balance> {
        val balances = mutableMapOf<String, BigDecimal>()

        for (debt in debts) {
            balances.merge(debt.from, -debt.amount) { a, b -> a + b }
            balances.merge(debt.to, debt.amount) { a, b -> a + b }
        }

        return balances.asSequence().map { entry -> Balance(entry.key, entry.value) }.toCollection(TreeSet())
    }

    private fun updateBalance(balance: Balance, diff: BigDecimal, balances: NavigableSet<Balance>) {
        val newAmount = balance.amount + diff
        if (newAmount.abs().stripTrailingZeros() != BigDecimal.ZERO) balances += balance.copy(amount = newAmount)
    }
}

private data class Debt(val from: String, val to: String, val amount: BigDecimal) {
    override fun toString() = "$from owes ${"%.2f".format(amount)}€ to $to"
}

private data class Balance(val actor: String, val amount: BigDecimal) : Comparable<Balance> {
    override fun compareTo(other: Balance) = amount.compareTo(other.amount)
}
