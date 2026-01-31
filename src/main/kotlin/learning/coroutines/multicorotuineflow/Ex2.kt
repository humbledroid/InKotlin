package learning.coroutines.multicorotuineflow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration.Companion.milliseconds

/* ****************************************************************************

   Curtis is managing the stockroom for a busy department store. Two things are
   happening at once:

   - **Deliveries arrive from the loading dock.** The deliveries arrive quickly
     as they're unloaded from the truck, but it takes some time to process them
     and load them into the store's inventory.
   - **Customers purchase items.** The customers stand in line with their
     merchandise, but the clerk can only process one customer's order at a
     time.

   For this exercise, complete the to-do items marked below.

   - For deliveries:
       - For each item, use `logInbound()` to print that the item was
         received - e.g., "👕 Received item"
       - When collecting the item, add it to the `Inventory`.
       - Be sure to use a flow operator that will allow Curtis to receive
         deliveries without making the truck wait for Curtis to process
         every box.

   - For purchases:
       - For each item, use `logOutbound()` to print that the item is
         being purchased - e.g., "👕 Customer purchasing"
       - When collecting the item, call `Inventory.purchase()` to remove
         it from inventory.
       - As with deliveries, be sure to use a flow operator that will allow
         the customers to queue up in a line immediately.

   Because deliveries and purchases happen concurrently, you'll want to make
   sure the `Inventory` object updates safely when accessed from multiple
   coroutines. Make any changes necessary to the `Inventory` object to make it
   safe. Review the "Shared Mutable State" unit of the course if needed!

   ************************************************************************* */

val deliveries = flow {
    logInbound("📦 Delivery truck has arrived!")
    listOf("👕", "👗", "🛼", "👕", "🛼").forEach { emit(it) }
    logInbound("📦 Delivery truck has departed.")
}

val purchases = flowOf("🛼", "👕", "👕", "👗", "🛼", "🛼")
    .onEach { logOutbound("💵 Customer joins the line with $it") }

fun main() {
    println("Starting inventory: $Inventory")

    runBlocking(Dispatchers.Default) {
        launch {
            deliveries
                .onEach {
                    logInbound("$it Received item")
                }
                .buffer()
                .collect {
                    Inventory.add(it)
                }
        }
        launch {
            purchases
                .buffer()
                .onEach {
                    logOutbound("$it Customer purchasing")
                }
                .collect {
                    Inventory.purchase(it)
                }
        }
    }

    println("Ending inventory: $Inventory")
}

object Inventory {
    private val data = mutableMapOf("👕" to 5, "👗" to 3, "🛼" to 4)
    private var snapshot = data.toMap()

    private val mutex = Mutex()
    override fun toString() = snapshot.toString()

    suspend fun add(item: String) {
        logInbound("$item Adding to inventory...")
        delay(500.milliseconds)

        mutex.withLock {
            val count = data.getOrDefault(item, 0)
            data[item] = count + 1
            snapshot = data
        }

        logInbound("$item Done processing!")
        logInbound("   $data")
    }

    suspend fun purchase(item: String) {
        logOutbound("$item Purchase initiated...")
        delay(200.milliseconds)
        mutex.withLock {
            val count = data.getOrDefault(item, 0)
            if (count == 0) throw IllegalArgumentException("This item is no longer in stock.")
            data[item] = count - 1
            snapshot = data
        }
        logOutbound("$item Purchase complete.")
        logOutbound("   $data")
    }
}

fun logInbound(message: String) = println(message)
fun logOutbound(message: String) = println(" ".repeat(35) + message)