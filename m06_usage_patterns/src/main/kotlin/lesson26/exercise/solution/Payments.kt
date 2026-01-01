package lesson26.exercise.solution

import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import lesson26.exercise.thirdparty.Customer
import lesson26.exercise.thirdparty.PaymentCallback
import lesson26.exercise.thirdparty.StoreCreditCallback
import kotlin.coroutines.resume
import lesson26.exercise.thirdparty.PaymentSystem as BasePaymentSystem

/* ****************************************************************************

   George's Laundry Service uses a third-party library to handle payment
   processing, but that library provides a callback-based API, as found in the
   nested `thirdparty` package.

   Create an adapter to wrap the `PaymentSystem` with a coroutine-based version
   of that class, and then update the `main()` function so that it uses the
   adapter instead of the original `PaymentSystem`.

   ************************************************************************* */

fun main() {
    val paymentSystem = PaymentSystem(BasePaymentSystem())
    val customers = listOf(Customer(123), Customer(456), Customer(789), Customer(555))
    val amountToCharge = 20

    runBlocking {
        customers.map { customer ->
            launch {
                val due = when (val result = paymentSystem.payWithStoreCredit(customer, amountToCharge)) {
                    is StoreCreditResult.Applied -> result.due
                    StoreCreditResult.NotFound   -> amountToCharge
                }

                if (due == 0) {
                    println("${customer.id} Paid in full")
                } else {
                    when (paymentSystem.payWithPaymentCard(customer, due)) {
                        PaymentCardResult.APPROVED  -> println("${customer.id} Paid in full")
                        PaymentCardResult.DECLINED,
                        PaymentCardResult.NOT_FOUND -> println("${customer.id} Still due: $due")
                    }
                }
            }
        }.joinAll()
    }
}

class PaymentSystem(private val base: BasePaymentSystem) {
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
