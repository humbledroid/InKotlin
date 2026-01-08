package learning.coroutines.callbacks.exercise

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.suspendCancellableCoroutine
import learning.coroutines.callbacks.exercise.thirdparty.Customer
import learning.coroutines.callbacks.exercise.thirdparty.PaymentCallback
import learning.coroutines.callbacks.exercise.thirdparty.PaymentSystem as BasePaymentSystem
import learning.coroutines.callbacks.exercise.thirdparty.StoreCreditCallback
import kotlin.coroutines.resume


/* ****************************************************************************

   George's Laundry Service uses a third-party library to handle payment
   processing, but that library provides a callback-based API, as found in the
   nested `thirdparty` package.

   Create an adapter to wrap the `PaymentSystem` with a coroutine-based version
   of that class, and then update the `main()` function so that it uses the
   adapter instead of the original `PaymentSystem`.

   ************************************************************************* */



class PaymentSystem(private val base: BasePaymentSystem = BasePaymentSystem()) {
    suspend fun payWithStoreCredit(customer: Customer, amountToCharge: Int) = suspendCancellableCoroutine { cont ->
        base.payWithStoreCredit(customer, amountToCharge, object : StoreCreditCallback {
            override fun onApplied(balanceDue: Int) = cont.resume(StoreCreditResult.Applied(balanceDue))
            override fun onNotFound() = cont.resume(StoreCreditResult.NotFound)
        })
    }

    suspend fun payWithPaymentCard(customer: Customer, amountToCharge: Int) = suspendCancellableCoroutine { cont ->
        base.payWithPaymentCard(customer, amountToCharge, object : PaymentCallback {
            override fun onApproved() = cont.resume(PaymentCardResult.APPROVED)
            override fun onDeclined() = cont.resume(PaymentCardResult.DECLINED)
            override fun onNotFound() = cont.resume(PaymentCardResult.NOT_FOUND)
        })
    }
}

sealed class StoreCreditResult {
    class Applied(val due: Int) : StoreCreditResult()
    object NotFound : StoreCreditResult()
}

enum class PaymentCardResult {
    APPROVED, DECLINED, NOT_FOUND
}


suspend fun main() {
    val paymentSystem = PaymentSystem()
    val customers = listOf(Customer(123), Customer(456), Customer(789), Customer(555))
    val amountToCharge = 20

    coroutineScope {
        for (customer in customers) {
            val status = paymentSystem.payWithStoreCredit(customer, amountToCharge)
            when (status) {
                is StoreCreditResult.Applied -> {
                    when (val balanceDue = paymentSystem.payWithPaymentCard(customer, amountToCharge)) {
                        PaymentCardResult.APPROVED -> println("${customer.id} Paid in full")
                        PaymentCardResult.DECLINED -> println("${customer.id} Still due: $balanceDue")
                        PaymentCardResult.NOT_FOUND -> println("${customer.id} Still due: $balanceDue")
                    }
                }

                StoreCreditResult.NotFound -> {
                    when (val amountToChargeNew = paymentSystem.payWithPaymentCard(customer, amountToCharge)) {
                        PaymentCardResult.APPROVED -> println("${customer.id} Paid in full")
                        PaymentCardResult.DECLINED -> println("${customer.id} Still due: $amountToChargeNew")
                        PaymentCardResult.NOT_FOUND -> println("${customer.id} Still due: $amountToChargeNew")
                    }
                }

            }
        }

    }

}
