package memoizrlabs.com.tdd_playground.bank_mockist

import java.text.DateFormat
import java.text.DateFormat.SHORT

open class TransactionPrinter(private val printer: Printer) {

    open fun printTransactions(transactions: List<Transaction>) {
        println("printing")
        printHeader()
        printAllTransactions(transactions)
    }

    private fun printHeader() {
        printer.print("DATE | AMOUNT | BALANCE")
    }

    private fun printAllTransactions(transactions: List<Transaction>) {
        transactions.fold(0) { count: Int, transaction: Transaction ->
            val total = count + transaction.amount
            printTransaction(total, transaction)
            total
        }
    }

    private fun printTransaction(total: Int, transaction: Transaction) {
        printer.print("${formatDate(transaction)} | ${transaction.amount}.00 | $total.00")
    }

    private fun formatDate(transaction: Transaction) = DateFormat.getDateInstance(SHORT).format(transaction.date)
}