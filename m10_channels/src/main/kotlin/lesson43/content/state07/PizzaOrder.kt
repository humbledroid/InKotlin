package lesson43.content.state07

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

suspend fun main(): Unit = coroutineScope {
    val isabellaPhone = Channel<String>()
    val fernandoPhone = Channel<String>()

    launch {
        val duration = Random.nextDouble(5.0).seconds
        println("Isabella is busy for $duration")
        delay(duration)
        val order = isabellaPhone.receive()
        println("Isabella's Pizza is preparing: $order")
    }

    launch {
        val duration = Random.nextDouble(5.0).seconds
        println("Fernando is busy for $duration")
        delay(duration)
        val order = fernandoPhone.receive()
        println("Fernando's Pizza is preparing: $order")
    }

    val request = "🍕 Pepperoni Pizza"

    select {
        isabellaPhone.onSend(request) { println("Isabella picked up the phone.") }
        fernandoPhone.onSend(request) { println("Fernando picked up the phone.") }
    }
}
