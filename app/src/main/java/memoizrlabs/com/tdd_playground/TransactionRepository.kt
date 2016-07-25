package memoizrlabs.com.tdd_playground

class TransactionRepository(private val calendar: Calendar){
    private var transactions = mutableListOf<Transaction>()

    fun deposit(amount: Int) {
        transactions.add(Transaction(calendar.getDate(), amount))
    }

    fun getAllTransactions(): List<Transaction> = transactions
}