package learning.coroutines.cancellations

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun main() {
    runBlocking {
        lateinit var phoneOrder: Job
        lateinit var kite: Job
        val aceOrder = launch {
            launch { ship("Ace", "🎧", 1.seconds) }
            phoneOrder = launch { ship("Ace", "📱", 3.seconds) }
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
            kite = launch { ship("Zeb", "🪁", 4.seconds) }
        }

        delay(1.seconds)
        phoneOrder.cancel()
        delay(1.seconds)
        joeOrder.cancel()
        delay(1.seconds)
        kite.cancel()
    }
}

suspend fun ship(recipient: String, merchandise: String, duration: Duration) {
    println("$recipient: $merchandise - Packing it up!")
    delay(duration)
    println("$recipient: $merchandise - Shipping the merchandise!")
}