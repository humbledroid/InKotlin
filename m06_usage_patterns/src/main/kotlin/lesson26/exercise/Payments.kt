package lesson26.exercise

import lesson26.exercise.thirdparty.Customer
import lesson26.exercise.thirdparty.PaymentCallback
import lesson26.exercise.thirdparty.PaymentSystem
import lesson26.exercise.thirdparty.StoreCreditCallback

/* ****************************************************************************

   George's Laundry Service uses a third-party library to handle payment
   processing, but that library provides a callback-based API, as found in the
   nested `thirdparty` package.

   Create an adapter to wrap the `PaymentSystem` with a coroutine-based version
   of that class, and then update the `main()` function so that it uses the
   adapter instead of the original `PaymentSystem`.

   ************************************************************************* */

fun main() {
    val paymentSystem = PaymentSystem()
    val customers = listOf(Customer(123), Customer(456), Customer(789), Customer(555))
    val amountToCharge = 20

    for (customer in customers) {
        paymentSystem.payWithStoreCredit(customer, amountToCharge, object : StoreCreditCallback {
            override fun onApplied(balanceDue: Int) {
                if (balanceDue > 0) {
                    paymentSystem.payWithPaymentCard(customer, balanceDue, object : PaymentCallback {
                        override fun onApproved() = println("${customer.id} Paid in full")
                        override fun onDeclined() = println("${customer.id} Still due: $balanceDue")
                        override fun onNotFound() = println("${customer.id} Still due: $balanceDue")
                    })
                } else {
                    println("${customer.id} Paid in full")
                }
            }

            override fun onNotFound() {
                paymentSystem.payWithPaymentCard(customer, amountToCharge, object : PaymentCallback {
                    override fun onApproved() = println("${customer.id} Paid in full")
                    override fun onDeclined() = println("${customer.id} Still due: $amountToCharge")
                    override fun onNotFound() = println("${customer.id} Still due: $amountToCharge")
                })
            }
        })
    }
}
