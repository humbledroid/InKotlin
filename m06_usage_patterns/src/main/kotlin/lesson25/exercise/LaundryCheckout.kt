package lesson25.exercise

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

fun main() = runBlocking {
    val receipts = sampleOrders()
        .map { order -> checkoutAsync(this, order) }
        .awaitAll()

    println("----- RECEIPTS -----")
    receipts.forEach(::println)
}

fun checkoutAsync(scope: CoroutineScope, order: Order): Deferred<Receipt> = scope.async {
    println("🧾 Starting checkout for ${order.customer.name}")

    val subtotal = async { subtotal(order) }.await()
    val discount = async { applyCoupon(order, subtotal) }.await()
    val tax = async { calculateTax(subtotal - discount) }.await()

    val receipt = Receipt(order.customer, subtotal, discount, tax)

    async(Dispatchers.IO) { printReceiptBlocking(receipt) }.await()

    GlobalScope.launch {
        sendThankYouSms(order.customer, receipt.total)
    }

    println("✅ Checkout complete for ${order.customer.name}")
    receipt
}

// No changes are required below this line ------------------------------------

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

suspend fun sendThankYouSms(customer: Customer, total: Int) {
    delay(0.25.seconds)
    println($$"📱 SMS to $${customer.name}: Your total is $$${total}.")
}

data class Order(
    val customer: Customer,
    val items: List<LineItem>,
    val coupon: Coupon? = null
)

@JvmInline
value class Customer(val name: String)

@JvmInline
value class Coupon(val amountOff: Int)

data class LineItem(val label: String, val price: Int)

data class Receipt(
    val customer: Customer,
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

fun sampleOrders(): List<Order> = listOf(
    Order(
        customer = Customer("Polly"),
        items = listOf(LineItem("👚 Blouse", 9), LineItem("👖 Jeans", 10)),
        coupon = Coupon(10)
    ),
    Order(
        customer = Customer("Henry"),
        items = listOf(LineItem("👔 Shirt", 8), LineItem("🧥 Coat", 15))
    ),
    Order(
        customer = Customer("Frank"),
        items = listOf(LineItem("👕 Tee", 7), LineItem("🩳 Shorts", 6), LineItem("🧦 Socks", 5)),
        coupon = Coupon(5)
    )
)
