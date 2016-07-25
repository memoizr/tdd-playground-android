package memoizrlabs.com.tdd_playground.bank_mockist

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class TransactionPrinterTest {
    val date = aDate { day = 11 }
    val date2 = aDate { day = 12 }
    val date3 = aDate { day = 13 }
    val printer: Printer = mock<Printer>()
    val transactionPrinter = TransactionPrinter(printer)

    @Test
    fun alwaysPrintsHeader() {
        transactionPrinter.printTransactions(emptyList())

        verify(printer).print("DATE | AMOUNT | BALANCE")
    }

    @Test
    fun printsTransaction() {
        transactionPrinter.printTransactions(listOf(Transaction(date, 5)))

        verify(printer).print("11/11/11 | 5.00 | 5.00")
    }

    @Test
    fun printsTransactionsInOrder() {
        transactionPrinter.printTransactions(listOf(
                Transaction(date, 5),
                Transaction(date2, 10),
                Transaction(date3, -3)
        ))

        val inorder = Mockito.inOrder(printer)
        with(inorder) {
            verify(printer).print("DATE | AMOUNT | BALANCE")
            verify(printer).print("11/11/11 | 5.00 | 5.00")
            verify(printer).print("11/12/11 | 10.00 | 15.00")
            verify(printer).print("11/13/11 | -3.00 | 12.00")
        }
    }
}

class DateBuilder {
    var year: Int = 2011
    var month: Int = 10
    var day: Int = 11

    fun build() = Date(GregorianCalendar(year, month, day).timeInMillis)
}

fun aDate(init: DateBuilder.() -> Unit = {}) = DateBuilder().apply(init).build()