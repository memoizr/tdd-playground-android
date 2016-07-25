package memoizrlabs.com.tdd_playground

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import java.util.*

class BankAccountPresenterFeatureTest {
//    "11/11/13 | 11 | 11"
//    "12/11/13 | 45 | 56"
//    "13/11/13 | -6 | 50"
    val view = mock<AccountPresenter.AccountView>()
    val calendar = mock<Calendar>()
    val transactionRepository = TransactionRepository(calendar)
    val transactionPrinter = TransactionPrinter()
    val accountPresenter = AccountPresenter(view, transactionRepository, transactionPrinter)

    @Test
    fun `with no transactions prints only header`() {
        accountPresenter.printStatement()

        verify(view).printTransactions("DATE | AMOUNT | BALANCE")
    }

    @Test
    fun `with no one deposit prints header and deposit`() {
        val date = Date(2013, GregorianCalendar.NOVEMBER, 11)
        whenever(calendar.getDate()).thenReturn(date)

        accountPresenter.deposit(10)
        accountPresenter.printStatement()

        verify(view).printTransactions("DATE | AMOUNT | BALANCE")
        verify(view).printTransactions("11/11/13 | 10 | 10")
    }
}


