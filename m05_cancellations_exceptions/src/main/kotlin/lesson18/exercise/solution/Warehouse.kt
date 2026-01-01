package lesson18.exercise.solution

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   It's a busy day at the warehouse! The team is currently processing orders
   for three different customers. But some customers are cancelling their
   orders and others are cancelling individual items in their orders.

   Update the code below to handle these cancellations:

   - After delaying 1 second, Ace cancels the phone from his order.
   - One second after that, Joe cancels his entire order. (Too bad his shirt
     already shipped!)
   - One second after that, Zeb cancels his kite.

   When you're done, the output should show that 9 items are being packed up,
   but only 6 items actually shipped.

   ************************************************************************* */

fun main() {
    runBlocking {
        lateinit var phone: Job
        lateinit var kite: Job

        val aceOrder = launch {
            launch { ship("Ace", "🎧", 1.seconds) }
            phone = launch { ship("Ace", "📱", 3.seconds) }
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
        phone.cancel()

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
