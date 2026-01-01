package lesson26.exercise.thirdparty

import java.util.concurrent.ConcurrentHashMap

/**
 * Simulates a third-party payment system that provides a callback-based API.
 * For this exercise, do not make any changes to the contents of this file.
 */
class PaymentSystem {
    fun payWithStoreCredit(customer: Customer, amountToCharge: Int, callback: StoreCreditCallback) {
        Thread {
            try {
                val due = storeCredit.chargeAvailable(customer, amountToCharge)
                callback.onApplied(due)
            } catch (_: AccountNotFoundException) {
                callback.onNotFound()
            }
        }.start()
    }

    fun payWithPaymentCard(customer: Customer, amountToCharge: Int, callback: PaymentCallback) {
        Thread {
            try {
                val approved = bank.chargeOrDecline(customer, amountToCharge)
                if (approved) callback.onApproved() else callback.onDeclined()
            } catch (_: AccountNotFoundException) {
                callback.onNotFound()
            }
        }.start()
    }

    private val storeCredit =
        AccountSource(mapOf(Customer(123) to 25, Customer(789) to 16))

    private val bank =
        AccountSource(mapOf(Customer(123) to 2_500, Customer(456) to 1_000, Customer(789) to 32))
}

class AccountSource(accountData: Map<Customer, Int>) {
    private val accounts = ConcurrentHashMap(accountData)

    fun chargeAvailable(customer: Customer, amount: Int): Int {
        Thread.sleep(500) // ... processing ...
        var due = 0

        accounts.compute(customer) { customer: Customer?, balance: Int? ->
            if (balance == null) throw AccountNotFoundException()
            val applied = minOf(balance, amount)
            due = amount - applied
            balance - applied
        }

        return due
    }

    fun chargeOrDecline(customer: Customer, amount: Int): Boolean {
        Thread.sleep(500) // ... processing ...
        var approved = false

        accounts.compute(customer) { customer: Customer?, balance: Int? ->
            if (balance == null) throw AccountNotFoundException()
            if (balance >= amount) {
                approved = true
                balance - amount
            } else {
                balance
            }
        }

        return approved
    }
}

interface StoreCreditCallback {
    fun onApplied(balanceDue: Int)
    fun onNotFound()
}

interface PaymentCallback {
    fun onApproved()
    fun onDeclined()
    fun onNotFound()
}

@JvmInline
value class Customer(val id: Int)

class AccountNotFoundException() : Exception()
