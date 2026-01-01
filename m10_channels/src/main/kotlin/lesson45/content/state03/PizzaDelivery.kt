package lesson45.content.state03

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

fun main() = runBlocking<Unit>(Dispatchers.Default) {
    val orders = Channel<Int>(BUFFERED)
    val deliveries = Channel<String>(BUFFERED)

    launch {
        try {
            for (count in orders) {
                delay(500.milliseconds)
                deliveries.send("🍕".repeat(count))
                orders.cancel()
            }
        } finally {
            println("Done fulfilling orders.")
            deliveries.close()
        }
    }

    launch {
        var orderNumber = 0
        for (pizza in deliveries) {
            println("Order #${++orderNumber} $pizza")
        }
        println("Done delivering pizzas.")
    }

    orders.send(1)
    orders.send(5)
    orders.send(3)
}
