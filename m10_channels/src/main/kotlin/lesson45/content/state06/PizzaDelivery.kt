package lesson45.content.state06

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking<Unit>(Dispatchers.Default) {

    val orders = produce(capacity = BUFFERED) {
        send(1)
        send(5)
        send(3)
    }

    val deliveries = produce(capacity = BUFFERED) {
        var inventory = 7

        orders.consumeEach { count ->
            delay(500.milliseconds)
            inventory -= count
            if (inventory <= 0) throw Exception("Out of pizza!")
            send("🍕".repeat(count))
        }
        println("Done fulfilling orders.")
    }

    val pizzaDeliveryGuy = launch {
        var orderNumber = 0

        deliveries.consumeEach { pizza ->
            println("Order #${++orderNumber} $pizza")
        }

        println("Done delivering pizzas.")
    }
}
