package memoizrlabs.com.tdd_playground

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jakewharton.rxbinding.view.clicks
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observable
import java.lang.Integer.parseInt
import java.util.*

class MainActivity : AppCompatActivity(), AccountPresenter.AccountView {
    private val transactionAdapter = TransactionAdapter()
    private val presenter = AccountPresenter(TransactionRepository(SimpleCalendar()), TransactionPrinter())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recylerView.layoutManager = LinearLayoutManager(this)
        recylerView.adapter = transactionAdapter
    }

    override fun onStart() {
        super.onStart()
        presenter.onViewAttached(this)
    }

    override fun deposits(): Observable<Int> = depositButton.clicks().map { parseInt(depositEditText.text.toString()) }
    override fun printStatementRequest(): Observable<Unit> = printStatementButton.clicks()
    override fun printTransactions(message: List<String>) { transactionAdapter.setItems(message) }
}

class SimpleCalendar : Calendar {
    override fun getDate(): Date = Date()
}

class TransactionAdapter() : RecyclerView.Adapter<TransactionAdapter.Holder>() {
    private var items: List<String> = emptyList()

    fun setItems(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.content.text = items[position]
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val content = view.findViewById(R.id.transactionTextView) as TextView
    }
}
