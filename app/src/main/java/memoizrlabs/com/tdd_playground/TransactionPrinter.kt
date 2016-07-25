package memoizrlabs.com.tdd_playground

import java.text.SimpleDateFormat
import java.util.*

class TransactionPrinter {

    fun printTransactions(transactions: List<Transaction>, printer: Printer) {
        printTransactions(printer, transactions)
    }

    private fun printTransactions(printer: Printer, transactions: List<Transaction>) {
        var balance = 0
        printer.printTransactions((transactions).map { transaction ->
            balance += transaction.amount
            "${formattedDate(transaction.date)} | ${transaction.amount} | $balance"
        })
    }

    private fun formattedDate(date: Date): String = SimpleDateFormat("dd/MM/yy").format(date)
}

class HeaderPrinter(private val printer: Printer) : Printer {
    override fun printTransactions(message: List<String>) {
        printer.printTransactions(listOf("DATE | AMOUNT | BALANCE") + message)
    }
}
