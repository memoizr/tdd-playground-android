package memoizrlabs.com.tdd_playground.bank_realist

import memoizrlabs.com.tdd_playground.bank_mockist.Transaction
import java.text.DateFormat.SHORT
import java.text.DateFormat.getDateInstance
import java.util.*

class TransactionPrinter(private val printer: Printer) {
    fun print(transactions: List<Transaction>) {
        printHeader()
        printTransactions(transactions)
    }

    private fun printHeader() {
        printer.print("DATE | ACCOUNT | BALANCE")
    }

    private fun printTransactions(transactions: List<Transaction>) {
        transactions.zip(calculateRunningBalance(transactions))
                .forEach { printTransactionWithRunningBalance(it) }
    }

    private fun calculateRunningBalance(transactions: List<Transaction>): List<Int> {
        return transactions.scanl(0) { runningBalance, transaction ->
            runningBalance + transaction.amount
        }
    }

    private fun printTransactionWithRunningBalance(it: Pair<Transaction, Int>) {
        val (transaction, runningBalance) = it
        val (date, amount) = transaction
        printer.print("${formatDate(date)} | $amount.00 | $runningBalance.00")
    }

    private fun formatDate(date: Date): String = getDateInstance(SHORT).format(date)
}

private inline fun <T, R> List<T>.scan(initial: R, operation: (R, T) -> R): List<R> {
    var accumulator = initial
    return this.map {
        accumulator = operation(accumulator, it)
        accumulator
    }
}

private fun <T, R> List<T>.scanl(initial: R, operation: (R, T) -> R): List<R> {
    tailrec fun innerScan(acc: LinkedList<R>, rest: List<T>): List<R> =
            if (rest.isEmpty()) acc
            else innerScan(acc.append(operation(acc.last(), rest.first())), rest.drop(1))
    return innerScan(aLinkedList(initial), this).drop(1)
}

private fun <T> LinkedList<T>.append(item: T): LinkedList<T> = apply { add(item) }
private fun <T> aLinkedList(vararg items: T): LinkedList<T> = LinkedList<T>().apply { addAll(items) }


