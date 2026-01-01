package lesson22.exercise

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   The code below completes successfully, but it also outputs an error message
   and stack trace that look something like this...

   Exception in thread "..." ...OutOfStockException: 🪀 is not in stock!
	at ...WarehouseKt.checkStock(Warehouse.kt:71)
	at ...WarehouseKt.ship-exY8QGI(Warehouse.kt:62)

   Apply a last-resort exception handler so that it prints this instead of
   the exception and stack trace above:

   Exception: 🪀 is not in stock!

   ************************************************************************* */

fun main() {
    runBlocking {
        val aceOrder = launch {
            supervisorScope {
                launch { ship("Ace", "🎧", 1.seconds) }
                launch { ship("Ace", "📱", 3.seconds) }
                launch { ship("Ace", "🎮", 2.seconds) }
                launch { ship("Ace", "🛹", 5.seconds) }
            }
        }
        val joeOrder = launch {
            supervisorScope {
                launch { ship("Joe", "👕", 1.seconds) }
                launch { ship("Joe", "👖", 3.seconds) }
            }
        }
        val zebOrder = launch {
            supervisorScope {
                launch { ship("Zeb", "🧸", 3.seconds) }
                launch { ship("Zeb", "🪀", 1.seconds) }
                launch { ship("Zeb", "🪁", 4.seconds) }
            }
        }
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
