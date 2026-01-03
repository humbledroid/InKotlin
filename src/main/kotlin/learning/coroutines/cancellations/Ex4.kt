package learning.coroutines.cancellations

import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


fun main() {
    val stock = listOf("🎧", "📱", "🎮", "🛹", "👕", "👖", "🧸", "🪁")

    fun checkStockInThis(merchandise: String) {
        if (merchandise !in stock) throw OutOfStockException("$merchandise is not in stock!")
    }

    suspend fun ship(recipient: String, merchandise: String, duration: Duration) {
        checkStockInThis(merchandise)
        println("$recipient: $merchandise - Packing it up!")
        delay(duration)
        println("$recipient: $merchandise - Shipping the merchandise!")
    }

    val scope = CoroutineScope(EmptyCoroutineContext)

    val aceOrder = scope.launch {
        supervisorScope {
            launch { ship("Ace", "🎧", 1.seconds) }
            launch { ship("Ace", "📱", 3.seconds) }
            launch { ship("Ace", "🎮", 2.seconds) }
            launch { ship("Ace", "🛹", 5.seconds) }
        }
    }
    val joeOrder = scope.launch {
        supervisorScope {
            launch { ship("Joe", "👕", 1.seconds) }
            launch { ship("Joe", "👖", 3.seconds) }
        }
    }
    val zebOrder = scope.launch {
        supervisorScope {
            launch { ship("Zeb", "🧸", 3.seconds) }
            launch { ship("Zeb", "🪀", 1.seconds) }
            launch { ship("Zeb", "🪁", 4.seconds) }
        }

    }

    runBlocking {
        aceOrder.join()
        joeOrder.join()
        zebOrder.join()
    }
}