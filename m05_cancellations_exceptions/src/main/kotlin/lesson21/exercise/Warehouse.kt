package lesson21.exercise

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   Out-of-stock issues are causing problems at the warehouse again!

   Without using a try-catch, debug the code below, and fix it so that all
   items that are in stock are properly shipped.

   Just like last time, when you're done:

   - 8 items should be packed and shipped.
   - 1 item should not be processed, since it's out of stock.

   It's fine for an exception message to print in the console output, as long
   as the process finishes with exit code 0.

   ************************************************************************* */

fun main() {
    val scope = CoroutineScope(EmptyCoroutineContext)

    val aceOrder = scope.launch {
        launch { ship("Ace", "🎧", 1.seconds) }
        launch { ship("Ace", "📱", 3.seconds) }
        launch { ship("Ace", "🎮", 2.seconds) }
        launch { ship("Ace", "🛹", 5.seconds) }
    }
    val joeOrder = scope.launch {
        launch { ship("Joe", "👕", 1.seconds) }
        launch { ship("Joe", "👖", 3.seconds) }
    }
    val zebOrder = scope.launch {
        launch { ship("Zeb", "🧸", 3.seconds) }
        launch { ship("Zeb", "🪀", 1.seconds) }
        launch { ship("Zeb", "🪁", 4.seconds) }
    }

    runBlocking {
        aceOrder.join()
        joeOrder.join()
        zebOrder.join()
    }
}

suspend fun ship(recipient: String, merchandise: String, duration: Duration) {
    checkStock(merchandise)
    println("$recipient: $merchandise - Packing it up!")
    delay(duration)
    println("$recipient: $merchandise - Shipping the merchandise!")
}

val stock = listOf("🎧", "📱", "🎮", "🛹", "👕", "👖", "🧸", "🪁")

fun checkStock(merchandise: String) {
    if (merchandise !in stock) throw OutOfStockException("$merchandise is not in stock!")
}

class OutOfStockException(message: String) : IllegalStateException(message)
