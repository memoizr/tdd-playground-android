package memoizrlabs.com.tdd_playground.bank_realist

class BankAccount(private val transactionRepository: InMemoryTransactionRepository, private val transactionPrinter: TransactionPrinter) {

    fun printBalance() {
        val transactions = transactionRepository.getTransactions()
        transactionPrinter.print(transactions)
    }

    fun deposit(amount: Int) {
        transactionRepository.deposit(amount)
    }

    fun withdraw(amount: Int) {
        transactionRepository.withdraw(amount)
    }
}