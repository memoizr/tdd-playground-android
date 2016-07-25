package memoizrlabs.com.tdd_playground.bank_realist

import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import memoizrlabs.com.tdd_playground.bank_mockist.Clock
import memoizrlabs.com.tdd_playground.bank_mockist.aDate
import org.junit.Test

class BankAccountAcceptanceTest {
    val clock = mock<Clock>()
    val printer = mock<Printer>()
    val bankAccount = BankAccount(InMemoryTransactionRepository(clock), TransactionPrinter(printer))

    @Test
    fun withNoTransactionsPrintsOnlyTheHeader() {
        bankAccount.printBalance()

        verify(printer).print("DATE | ACCOUNT | BALANCE")
    }

    @Test
    fun withOneDepositPrintsHeaderAndDeposit() {
        whenever(clock.getDate()).thenReturn(aDate())
        bankAccount.deposit(5)

        bankAccount.printBalance()

        with(inOrder(printer)) {
            verify(printer).print("DATE | ACCOUNT | BALANCE")
            verify(printer).print("11/11/11 | 5.00 | 5.00")
        }
    }

    @Test
    fun withOneWithdrawalPrintsHeaderAndWithdrawal() {
        whenever(clock.getDate()).thenReturn(aDate())
        bankAccount.withdraw(5)

        bankAccount.printBalance()

        with(inOrder(printer)) {
            verify(printer).print("DATE | ACCOUNT | BALANCE")
            verify(printer).print("11/11/11 | -5.00 | -5.00")
        }
    }

    @Test
    fun withSeveralTransactionsPrintsHeaderAndTransactionsAndRunningBalance() {
        whenever(clock.getDate()).thenReturn(aDate(),
                aDate { day = 12 },
                aDate { day = 13 },
                aDate { day = 14 })
        bankAccount.deposit(5)
        bankAccount.deposit(13)
        bankAccount.withdraw(3)
        bankAccount.deposit(10)

        bankAccount.printBalance()

        with(inOrder(printer)) {
            verify(printer).print("DATE | ACCOUNT | BALANCE")
            verify(printer).print("11/11/11 | 5.00 | 5.00")
            verify(printer).print("11/12/11 | 13.00 | 18.00")
            verify(printer).print("11/13/11 | -3.00 | 15.00")
            verify(printer).print("11/14/11 | 10.00 | 25.00")
        }
    }
}
