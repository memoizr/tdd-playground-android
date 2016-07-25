package memoizrlabs.com.tdd_playground.bank_mockist

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import java.util.*

class InMemoryTransactionRepositoryTest {
    val clock = mock<Clock>()
    val transactionRepository = InMemoryTransactionRepository(clock)
    val date = mock<Date>()
    val date2 = mock<Date>()

    @Test
    fun addsTransactions() {
        whenever(clock.getDate()).thenReturn(date, date2)
        transactionRepository.addTransaction(5)
        transactionRepository.addTransaction(10)

        val transactions = transactionRepository.getAllTransactions()
        verify(clock, times(2)).getDate()
        assertThat(transactions.size, equalTo(2))
        assertThat(transactions[0], equalTo(Transaction(date, 5)))
        assertThat(transactions[1], equalTo(Transaction(date2, 10)))
    }
}

