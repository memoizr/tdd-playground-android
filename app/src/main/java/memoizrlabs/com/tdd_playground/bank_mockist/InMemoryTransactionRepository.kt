package memoizrlabs.com.tdd_playground.bank_mockist

class InMemoryTransactionRepository(val clock: Clock) : TransactionRepository {
    private val transactions = mutableListOf<Transaction>()

    override fun addTransaction(transactionAmount: Int) {
        println(transactionAmount)
        transactions.add(createTransaction(transactionAmount))
    }

    private fun createTransaction(transactionAmount: Int) = Transaction(clock.getDate(), transactionAmount)

    override fun getAllTransactions(): List<Transaction> = transactions.toList()
}