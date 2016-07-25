package memoizrlabs.com.tdd_playground.bank_mockist

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test

class BankAccountTest {
    val transactionRepository = mock<TransactionRepository>()
    val transactionPrinter = mock<TransactionPrinter>()
    val transactions = listOf(aDeposit(5), aWithdrawal(1))

    @Before
    fun before() {
        whenever(transactionRepository.getAllTransactions()).thenReturn(transactions)
    }

    @Test
    fun deposits() {
        val bankAccount = BankAccount(transactionRepository, transactionPrinter)

        bankAccount.deposit(5)

        verify(transactionRepository).addTransaction(5)
    }

    @Test
    fun withdraws() {
        val bankAccount = BankAccount(transactionRepository, transactionPrinter)

        bankAccount.withdraw(5)

        verify(transactionRepository).addTransaction(-5)
    }

    @Test
    fun printsBalance() {
        val bankAccount = BankAccount(transactionRepository, transactionPrinter)

        bankAccount.printBalance()

        verify(transactionPrinter).printTransactions(transactions)
    }
}


private fun aDeposit(amount: Int) = Transaction(mock(), amount)
private fun aWithdrawal(amount: Int) = Transaction(mock(), -amount)
