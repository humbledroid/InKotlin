package lesson45.content.state09

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.consumeEach
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking<Unit>(Dispatchers.Default) {
    val orders = Channel<Int>(BUFFERED) {
        println("This element got dropped: $it")
    }
    val deliveries = Channel<String>(BUFFERED)

    launch {
        orders.send(1)
        orders.send(5)
        orders.send(3)
        orders.close()
    }

    launch {
        orders.consumeEach { count ->
            delay(500.milliseconds)
            deliveries.send("🍕".repeat(count))
            orders.cancel()
        }
        deliveries.close()
        println("Done fulfilling orders.")
    }

    launch {
        var orderNumber = 0

        deliveries.consumeEach { pizza ->
            println("Order #${++orderNumber} $pizza")
        }

        println("Done delivering pizzas.")
    }
}
