package lesson18.content.state02

import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun main() {
    val scope = CoroutineScope(EmptyCoroutineContext)

    lateinit var sallyOrder: Job
    lateinit var cakeOrder: Job
    lateinit var peterOrder: Job

    val tableOrder = scope.launch {
        sallyOrder = launch {
            launch { prepare("1 🍔 Hamburger", 5.seconds) }
            launch { prepare("1 🍟 Fries    ", 3.seconds) }
            launch { prepare("1 🥗 Salad    ", 2.seconds) }
            cakeOrder = launch { prepare("1 🍰 Cake     ", 4.seconds) }
        }
        peterOrder = launch {
            launch { prepare("2 🥩 Steak    ", 4.seconds) }
            launch { prepare("2 🥔 Potato   ", 2.seconds) }
            launch { prepare("2 🫛 Peas     ", 1.seconds) }
            launch { prepare("2 🍪 Cookie   ", 3.seconds) }
        }
    }

    runBlocking {
        delay(1.5.seconds)
        cakeOrder.cancel()
        tableOrder.join()
    }
}

suspend fun prepare(food: String, duration: Duration) {
    println("$food - Preparing")
    delay(duration)
    println("$food - Ready")
}
