package memoizrlabs.com.tdd_playground

import java.text.SimpleDateFormat
import java.util.*

class TransactionPrinter {
    fun printTransactions(transaction: Transaction?, printer: Printer) {
        printHeader(printer)
        printTransactions(printer, transaction)
    }

    private fun printTransactions(printer: Printer, transaction: Transaction?) {
        printer.printTransactions("${formattedDate(transaction?.date)} | ${transaction?.amount} | ${transaction?.amount}")
    }

    private fun printHeader(printer: Printer) {
        printer.printTransactions("DATE | AMOUNT | BALANCE")
    }

    fun formattedDate(date: Date?): String? = if (date != null) SimpleDateFormat("dd/MM/yy").format(date) else null
}
interface Printer {
    fun  printTransactions(message: String)
}