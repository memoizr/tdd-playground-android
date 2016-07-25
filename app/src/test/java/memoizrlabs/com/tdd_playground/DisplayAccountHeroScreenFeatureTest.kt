package memoizrlabs.com.tdd_playground

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import java.util.*

class DisplayAccountHeroScreenFeatureTest {
    val view = mock<HeroScreenPresenter.View>()
    val calendar = mock<Calendar>()
    val transactionRepository = TransactionRepository(calendar)
    val transactionPrinter = TransactionPrinter()
    val accountPresenter = HeroScreenPresenter(transactionRepository, transactionPrinter)

    @Test
    fun `immediately shows the transactions`() {
//        val date11 = Date(2013, GregorianCalendar.NOVEMBER, 11)
//        val date12 = Date(2013, GregorianCalendar.NOVEMBER, 12)
        val date11 = mock<Date>()
        val date12 = mock<Date>()
        whenever(date11.time).thenReturn(GregorianCalendar(2013, GregorianCalendar.NOVEMBER, 11).timeInMillis)
        whenever(date12.time).thenReturn(GregorianCalendar(2013, GregorianCalendar.NOVEMBER, 12).timeInMillis)
        whenever(calendar.getDate()).thenReturn(date11, date12)
        transactionRepository.deposit(10)
        transactionRepository.deposit(20)
        accountPresenter.onViewAttached(view)

        verify(view).printTransactions(listOf(
                "11/11/13 | 10 | 10",
                "12/11/13 | 20 | 30"
        ))
    }
}

