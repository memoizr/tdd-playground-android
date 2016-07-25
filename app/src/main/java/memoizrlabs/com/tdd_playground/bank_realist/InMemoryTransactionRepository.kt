package memoizrlabs.com.tdd_playground.bank_realist

import memoizrlabs.com.tdd_playground.bank_mockist.Clock
import memoizrlabs.com.tdd_playground.bank_mockist.Transaction

class InMemoryTransactionRepository(private val clock: Clock) {
    private val transactions = mutableListOf<Transaction>()

    fun deposit(amount: Int) {
        transactions.add(createTransaction(amount))
    }

    fun withdraw(amount: Int) {
        transactions.add(createTransaction(-amount))
    }

    fun getTransactions(): List<Transaction> = transactions.toList()

    private fun createTransaction(amount: Int) = Transaction(clock.getDate(), amount)
}