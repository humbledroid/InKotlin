package learning.coroutines.cancellations

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun main() {

    val stock = listOf("🎧", "📱", "🎮", "🛹", "👕", "👖", "🧸", "🪁")

    fun checkStock(merchandise: String) {
        if (merchandise !in stock) throw OutOfStockException("$merchandise is not in stock!")
    }

    suspend fun ship(recipient: String, merchandise: String, duration: Duration) {
        try{
            checkStock(merchandise)
            println("$recipient: $merchandise - Packing it up!")
            delay(duration)
            println("$recipient: $merchandise - Shipping the merchandise!")
        }catch (e: OutOfStockException) {
            println("Could not process order: ${e.message}")
        }

    }

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




class OutOfStockException(message: String) : IllegalStateException(message)