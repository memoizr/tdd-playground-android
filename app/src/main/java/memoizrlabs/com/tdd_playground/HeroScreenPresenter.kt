package memoizrlabs.com.tdd_playground

class HeroScreenPresenter(private val transactionRepository: TransactionRepository,
                          private val transactionPrinter: TransactionPrinter) {
    fun  onViewAttached(view: View) {
        transactionPrinter.printTransactions(transactionRepository.getAllTransactions(), view)

    }

    interface View: Printer {}

}