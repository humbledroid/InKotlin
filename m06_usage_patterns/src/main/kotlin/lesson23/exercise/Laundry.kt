package lesson23.exercise

import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   "George's Laundry Service" is busy washing tons of clothes for its
   customers. Today, he's got Polly, Henry, and Frank dropping off their
   laundry.

   The code below is working, but it's also creating more coroutines than
   necessary, making the code more challenging to read than it needs to be.

   George has enough equipment to process multiple garments in parallel -
   their per-garment steps remain sequential. However, he only has a single
   iron, so the ironing station must remain single-threaded.

   Remove the unnecessary coroutines from this code.

   ************************************************************************* */

val ironingDispatcher = Dispatchers.Default.limitedParallelism(1)

fun main() {
    runBlocking(Dispatchers.Default) {
        val customerOrders = listOf(
            Order.from(Customer("Polly"), "👗", "👚", "👖"),
            Order.from(Customer("Henry"), "👔", "🧥"),
            Order.from(Customer("Frank"), "👕", "🩳", "🧦"),
        )

        val result = customerOrders.map { order ->
            async {
                order to order.garments
                    .map { garment -> process(garment) }
                    .awaitAll()
            }
        }.awaitAll()

        result.forEach { (order, cleaned) ->
            println("Order for ${order.customer.name}:")
            cleaned.forEach { garment ->
                println("    ${garment.label}: ${garment.cost}")
            }
            println("    Total: ${cleaned.sumOf { it.cost }}")
        }

        println("-----------------------------")
        println("Total for today: ${result.sumOf { (_, cleaned) -> cleaned.sumOf { it.cost } }}")
    }
}

fun CoroutineScope.process(garmentToProcess: Garment) = async {
    var garment = garmentToProcess
    println("${garment.label} - Received 🆕")

    garment = async { garment.receive() }.await()
    garment = async { garment.soak() }.await()
    garment = async { garment.wash() }.await()
    garment = async { garment.dry() }.await()
    garment = async { garment.iron() }.await()
    garment = async { garment.bagAndBill() }.await()

    println("${garment.label} - Completed ✅")

    return@async garment
}

suspend fun Garment.receive(): Garment {
    println("$label - Tagged & logged 🏷️")
    delay(0.25.seconds)
    return this.copy(cost = cost + (lookup[label]?.baseCost ?: 0))
}

suspend fun Garment.soak(): Garment {
    println("$label - Soaking 🫧")
    delay(lookup[label]?.soakTime ?: 0.seconds)
    return copy(cost = cost + 1)
}

suspend fun Garment.wash(): Garment {
    println("$label - Washing 🚿")
    delay(lookup[label]?.washTime ?: 0.seconds)
    return copy(cost = cost + 2)
}

suspend fun Garment.dry(): Garment {
    println("$label - Drying 💨")
    delay(0.5.seconds)
    return copy(cost = cost + 1)
}

suspend fun Garment.iron(): Garment {
    coroutineScope {
        launch(ironingDispatcher) {
            println("$label - Ironing 🥵")
            Thread.sleep(1.0.seconds.inWholeMilliseconds)
        }
    }
    return copy(cost = cost + 2)
}

suspend fun Garment.bagAndBill(): Garment {
    delay(0.3.seconds)
    println($$"$$label - Bagged & billed for $$${cost} 🛍️💵")
    return this
}

// Model for an order that can have multiple articles of clothing.

data class Order(val customer: Customer, val garments: List<Garment>) {
    companion object {
        fun from(customer: Customer, vararg labels: String) = Order(customer, labels.map(::Garment))
    }
}

@JvmInline
value class Customer(val name: String)

data class Garment(
    val label: String,
    val cost: Int = 0
)

// Lookup for how long things take and how much they cost.

data class Item(val label: String, val soakTime: Duration, val washTime: Duration, val baseCost: Int)

val lookup: Map<String, Item> = listOf(
    Item("👗", 1.0.seconds, 2.5.seconds, 12),
    Item("👚", 0.5.seconds, 2.0.seconds, 9),
    Item("👖", 0.75.seconds, 2.5.seconds, 10),
    Item("👔", 0.5.seconds, 1.75.seconds, 8),
    Item("🧥", 1.5.seconds, 3.0.seconds, 15),
    Item("👕", 0.25.seconds, 1.5.seconds, 7),
    Item("🩳", 0.25.seconds, 1.5.seconds, 6),
    Item("🧦", 0.25.seconds, 1.0.seconds, 5)
).associateBy { it.label }
