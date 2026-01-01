package lesson20.content.state01

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun main() {
    runBlocking {
        lateinit var sallyOrder: Job
        lateinit var peterOrder: Job

        val tableOrder = launch {
            sallyOrder = launch {
                launch { prepare("1 🍔 Hamburger", 5.seconds) }
                launch { prepare("1 🍟 Fries    ", 3.seconds) }
                launch { prepare("1 🥗 Salad    ", 2.seconds) }
                launch { prepare("1 🍰 Cake     ", 4.seconds) }
            }
            peterOrder = launch {
                launch { prepare("2 🥩 Steak    ", 4.seconds) }
                launch { prepare("2 🥔 Potato   ", 2.seconds) }
                launch { prepare("2 🫛 Peas     ", 1.seconds) }
                launch { prepare("2 🍪 Cookie   ", 3.seconds) }
            }
        }
    }
}

suspend fun prepare(food: String, duration: Duration) {
    println("$food - Preparing")
    delay(duration)
    println("$food - Ready")
}
