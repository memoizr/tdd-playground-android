package memoizrlabs.com.tdd_playground

class TransactionRepository(private val calendar: Calendar){
    var transaction: Transaction? = null
    fun deposit(amount: Int) {
        transaction = Transaction(calendar.getDate(), amount)
    }

    fun getLastTransaction(): Transaction? = transaction
}