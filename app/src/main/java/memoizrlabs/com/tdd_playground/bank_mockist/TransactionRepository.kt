package memoizrlabs.com.tdd_playground.bank_mockist

interface TransactionRepository {
    fun addTransaction(transactionAmount: Int)
    fun getAllTransactions(): List<Transaction>
}