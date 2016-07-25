package memoizrlabs.com.tdd_playground

class AccountPresenter(
        private val view: AccountView,
        private val transactionRepository: TransactionRepository,
        private val transactionPrinter: TransactionPrinter){

    fun printStatement() {
        transactionPrinter.printTransactions(transactionRepository.getLastTransaction(), view)
    }

    fun deposit(amount: Int) {
        transactionRepository.deposit(amount)
    }

    interface AccountView: Printer
}
