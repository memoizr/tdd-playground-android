package memoizrlabs.com.tdd_playground

import rx.Observable

class AccountPresenter(
        private val transactionRepository: TransactionRepository,
        private val transactionPrinter: TransactionPrinter) {

    fun onViewAttached(view: AccountView) {
        view.deposits().subscribe { amount ->
            transactionRepository.deposit(amount)
        }

        view.printStatementRequest().subscribe {
            transactionPrinter.printTransactions(transactionRepository.getAllTransactions(), HeaderPrinter(view))
        }

    }

    interface AccountView : Printer {
        fun deposits(): Observable<Int>
        fun printStatementRequest(): Observable<Unit>
    }
}
