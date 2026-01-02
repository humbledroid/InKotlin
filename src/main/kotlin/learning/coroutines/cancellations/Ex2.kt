package learning.coroutines.cancellations

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   Even though Joe cancelled his order, he still received it!

   Debug and fix the code below so that Joe's order is properly cancelled.

   When you're done, the warehouse should start packing up all the
   items, but only 7 of them should actually ship.

   ************************************************************************* */

fun main() {

    suspend fun ship(recipient: String, merchandise: String, duration: Duration) {
        try {
            checkStock(merchandise)
            println("$recipient: $merchandise - Packing it up!")
            delay(duration)
            println("$recipient: $merchandise - Shipping the merchandise!")
        } catch (e: IllegalStateException) {
            println("Could not process order: ${e.message}")
        }
    }


    runBlocking(Dispatchers.IO) {
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

        delay(0.5.seconds)
        joeOrder.cancel()
    }
}

val stock = listOf("🎧", "📱", "🎮", "🛹", "👕", "👖", "🧸", "🪀", "🪁")

fun checkStock(merchandise: String) {
    if (merchandise !in stock) throw IllegalStateException("$merchandise is not in stock!")
}
