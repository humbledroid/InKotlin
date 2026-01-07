package learning.coroutines.patterns


import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   The laundry service front desk has three checkout stations, which are
   printing out receipts and sending text messages with the totals.

   Fix the following issues:

   1) Remove the unnecessary coroutines.
   2) Fix the bug so that the text messages are properly sent.
   3) Replace the builder wrapper with a plain suspending function, and keep
      concurrency at its call site.

   ************************************************************************* */
@JvmInline
value class CustomerV1(val name: String)

@JvmInline
value class Coupon(val amountOff: Int)

fun main() {

    data class LineItem(val label: String, val price: Int)

    data class Receipt(
        val customer: CustomerV1,
        val subtotal: Int,
        val discount: Int,
        val tax: Int
    ) {
        val total = subtotal - discount + tax

        override fun toString(): String {
            return """
            Order for ${customer.name}:
                subtotal=${subtotal}
                discount=${discount}
                tax=${tax}
                total=${total}
            """.trimIndent()
        }
    }

    data class Order(
        val customer: CustomerV1,
        val items: List<LineItem>,
        val coupon: Coupon? = null
    )

    suspend fun subtotal(order: Order): Int {
        delay(0.3.seconds)
        return order.items.sumOf { it.price }
    }

    suspend fun applyCoupon(order: Order, subtotal: Int): Int {
        delay(0.2.seconds)
        return (order.coupon?.amountOff ?: 0).coerceAtMost(subtotal)
    }

    suspend fun calculateTax(amount: Int): Int {
        delay(0.15.seconds)
        val percent = 20
        return (amount * percent) / 100
    }

    fun printReceiptBlocking(receipt: Receipt) {
        println("🖨️ Printing receipt for ${receipt.customer.name} …")
        Thread.sleep(800)
        println($$"🖨️ Printed receipt for $${receipt.customer.name}, total $$${receipt.total}")
    }

    suspend fun sendThankYouSms(customer: CustomerV1, total: Int) {
        delay(0.25.seconds)
        println($$"📱 SMS to $${customer.name}: Your total is $$${total}.")
    }

    suspend fun checkoutAsync(order: Order): Receipt {
        println("🧾 Starting checkout for ${order.customer.name}")

        val subtotal =  subtotal(order)
        val discount =  applyCoupon(order, subtotal)
        val tax = calculateTax(subtotal - discount)

        val receipt = Receipt(order.customer, subtotal, discount, tax)

        withContext(Dispatchers.IO){
            printReceiptBlocking(receipt)
        }

        sendThankYouSms(order.customer, receipt.total)

        println("✅ Checkout complete for ${order.customer.name}")
        return receipt
    }

    fun sampleOrders(): List<Order> = listOf(
        Order(
            customer = CustomerV1("Polly"),
            items = listOf(LineItem("👚 Blouse", 9), LineItem("👖 Jeans", 10)),
            coupon = Coupon(10)
        ),
        Order(
            customer = CustomerV1("Henry"),
            items = listOf(LineItem("👔 Shirt", 8), LineItem("🧥 Coat", 15))
        ),
        Order(
            customer = CustomerV1("Frank"),
            items = listOf(LineItem("👕 Tee", 7), LineItem("🩳 Shorts", 6), LineItem("🧦 Socks", 5)),
            coupon = Coupon(5)
        )
    )

    runBlocking {
        val receipts = sampleOrders()
            .map { order -> async { checkoutAsync(order) }}
            .awaitAll()

        println("----- RECEIPTS -----")
        receipts.forEach(::println)
    }
}
