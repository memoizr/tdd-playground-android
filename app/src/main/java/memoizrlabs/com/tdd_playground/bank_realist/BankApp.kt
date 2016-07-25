package memoizrlabs.com.tdd_playground.bank_realist

import memoizrlabs.com.tdd_playground.bank_mockist.Clock
import java.util.*

fun main(args: Array<String>) {
//    val clock = RealClock()
//    val bankAccount = BankAccount(ConsolePrinter(), clock)
//
//    bankAccount.deposit(5)
//    bankAccount.deposit(10)
//    bankAccount.withdraw(6)
//    bankAccount.deposit(9)
//
//    bankAccount.printBalance()
}

class RealClock : Clock {
    override fun getDate(): Date = Date()
}

class ConsolePrinter : Printer {
    override fun print(message: String) {
        println(message)
    }
}