package learning.coroutines.cancellations

import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun main() {

    val handler = CoroutineExceptionHandler { _, throwable ->
        println("Exception: ${throwable.message}")
    }

    val stock = listOf("🎧", "📱", "🎮", "🛹", "👕", "👖", "🧸", "🪁")

    fun checkStock(merchandise: String) {
        if (merchandise !in stock) throw OutOfStockException("$merchandise is not in stock!")
    }

    class OutOfStockException(message: String) : IllegalStateException(message)

    suspend fun ship(recipient: String, merchandise: String, duration: Duration) {
        checkStock(merchandise)
        println("$recipient: $merchandise - Packing it up!")
        delay(duration)
        println("$recipient: $merchandise - Shipping the merchandise!")
    }

    runBlocking(handler) {
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