package memoizrlabs.com.tdd_playground.bank_mockist

class BankAccount(private val transactionRepository: TransactionRepository,
                  private val transactionPrinter: TransactionPrinter) {

    fun deposit(depositAmount: Int) {
        transactionRepository.addTransaction(depositAmount)
    }

    fun withdraw(withdrawalAmount: Int) {
        transactionRepository.addTransaction(-withdrawalAmount)
    }

    fun printBalance() {
        transactionPrinter.printTransactions(transactionRepository.getAllTransactions())
    }
}