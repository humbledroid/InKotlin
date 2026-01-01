package lesson45.content.state08

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking<Unit>(Dispatchers.Default) {

    val orders = produce(capacity = BUFFERED) {
        send(1)
        send(5)
        send(3)
    }

    val deliveries = produce(capacity = BUFFERED) {
        orders.consumeEach { count ->
            delay(500.milliseconds)
            send("🍕".repeat(count))
        }
        println("Done fulfilling orders.")
        orders.receiveCatching()
    }

    val pizzaDeliveryGuy = launch {
        var orderNumber = 0

        deliveries.consumeEach { pizza ->
            println("Order #${++orderNumber} $pizza")
        }

        println("Done delivering pizzas.")
    }

}
