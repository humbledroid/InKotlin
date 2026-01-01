package lesson20.exercise.solution

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   The warehouse was supposed to process three orders today, but for some
   reason, none of the orders shipped!

   Debug the code below, and fix it so that all items that are in stock are
   properly shipped.

   When you're done:

   - 8 items should be packed and shipped.
   - 1 item should not be processed, since it's out of stock.

   ************************************************************************* */

fun main() {
    runBlocking {
        val aceOrder = launch {
            launch { ship("Ace", "🎧", 1.seconds) }
            launch { ship("Ace", "📱", 3.seconds) }
            launch { ship("Ace", "🎮", 2.seconds) }
            launch { ship("Ace", "🛹", 5.seconds) }
        }
        val joeOrder = launch {
            launch { ship("Joe", "👕", 1.seconds) }
            launch { ship("Joe", "👖", 3.seconds) }
        }
        val zebOrder = launch {
            launch { ship("Zeb", "🧸", 3.seconds) }
            launch { ship("Zeb", "🪀", 1.seconds) }
            launch { ship("Zeb", "🪁", 4.seconds) }
        }
    }
}

suspend fun ship(recipient: String, merchandise: String, duration: Duration) {
    try {
        checkStock(merchandise)
        println("$recipient: $merchandise - Packing it up!")
        delay(duration)
        println("$recipient: $merchandise - Shipping the merchandise!")
    } catch (e: OutOfStockException) {
        println("Could not process order: ${e.message}")
    }
}

val stock = listOf("🎧", "📱", "🎮", "🛹", "👕", "👖", "🧸", "🪁")

fun checkStock(merchandise: String) {
    if (merchandise !in stock) throw OutOfStockException("$merchandise is not in stock!")
}

class OutOfStockException(message: String) : IllegalStateException(message)
