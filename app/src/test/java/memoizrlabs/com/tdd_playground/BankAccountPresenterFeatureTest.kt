package memoizrlabs.com.tdd_playground

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.lang.kotlin.PublishSubject
import java.util.*

class BankAccountPresenterFeatureTest {
//    "11/11/13 | 11 | 11"
//    "12/11/13 | 45 | 56"
//    "13/11/13 | -6 | 50"
    val view = mock<AccountPresenter.AccountView>()
    val calendar = mock<Calendar>()
    val transactionRepository = TransactionRepository(calendar)
    val transactionPrinter = TransactionPrinter()
    val accountPresenter = AccountPresenter(transactionRepository, transactionPrinter)
    val depositSubject = PublishSubject<Int>()
    val printStatementSubject = PublishSubject<Unit>()

    @Before
    fun before() {
        whenever(view.deposits()).thenReturn(depositSubject)
        whenever(view.printStatementRequest()).thenReturn(printStatementSubject)
    }

    @Test
    fun `with no transactions prints only header`() {
        accountPresenter.onViewAttached(view)
        printStatement()

        verify(view).printTransactions(listOf(
                "DATE | AMOUNT | BALANCE"))
    }

    @Test
    fun `with no one deposit prints header and deposit`() {
        accountPresenter.onViewAttached(view)
        val date = Date(2013, GregorianCalendar.NOVEMBER, 11)
        whenever(calendar.getDate()).thenReturn(date)

        val amount = 10
        deposit(amount)
        printStatement()

        verify(view).printTransactions(listOf(
                "DATE | AMOUNT | BALANCE",
                "11/11/13 | 10 | 10"))
    }

    @Test
    fun `with several deposits prints header and deposit`() {
        accountPresenter.onViewAttached(view)
        val date11 = Date(2013, GregorianCalendar.NOVEMBER, 11)
        val date12 = Date(2013, GregorianCalendar.NOVEMBER, 12)
        whenever(calendar.getDate()).thenReturn(date11, date12)

        deposit(10)
        deposit(20)
        printStatement()

        verify(view).printTransactions(listOf(
                "DATE | AMOUNT | BALANCE",
                "11/11/13 | 10 | 10",
                "12/11/13 | 20 | 30"))
    }

    private fun printStatement() {
        printStatementSubject.onNext(null)
    }

    private fun deposit(amount: Int) {
        depositSubject.onNext(amount)
    }
}
