package memoizrlabs.com.tdd_playground.bank_mockist

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test

class BankingFeatureTest {
    val printer = mock<Printer>()
    val clock = mock<Clock>()
    val transactionRepository = InMemoryTransactionRepository(clock)
    val transactionPrinter = TransactionPrinter(printer)

    @Test
    fun printsMostRecentTransactions() {
        whenever(clock.getDate()).thenReturn(
                aDate { day = 1 },
                aDate { day = 2 },
                aDate { day = 3 },
                aDate { day = 4 }
        )
        with(BankAccount(transactionRepository, transactionPrinter)) {
            deposit(5)
            deposit(8)
            deposit(13)
            withdraw(8)
            printBalance()
        }

        verify(printer).print("DATE | AMOUNT | BALANCE")
        verify(printer).print("11/1/11 | 5.00 | 5.00")
        verify(printer).print("11/2/11 | 8.00 | 13.00")
        verify(printer).print("11/3/11 | 13.00 | 26.00")
        verify(printer).print("11/4/11 | -8.00 | 18.00")
    }
}

private fun aDeposit(amount: Int) = Transaction(mock(), amount)
private fun aWithdrawal(amount: Int) = Transaction(mock(), -amount)
