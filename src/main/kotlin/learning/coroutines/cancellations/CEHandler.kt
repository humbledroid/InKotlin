package learning.coroutines.cancellations

import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

val handler = CoroutineExceptionHandler { context, throwable ->
    println("${context.job} - ${throwable.message}}")
}

fun main() {

    class KitchenFireException(message: String) : Exception(message)

    suspend fun prepare(food: String, duration: Duration): String {
        println("$food - Preparing")
        delay(duration)
        if ("🥩" in food) throw KitchenFireException("🔥 Grill caught on fire!")
        println("$food - Ready")
        return food.split(" ")[1]
    }

    runBlocking {
        lateinit var sallyOrder: Job
        lateinit var peterOrder: Job

        val tableOrder = launch {
            supervisorScope {
                sallyOrder = launch(handler) {
                    launch { prepare("1 🍔 Hamburger", 5.seconds) }
                    launch { prepare("1 🍟 Fries    ", 3.seconds) }
                    launch { prepare("1 🥗 Salad    ", 2.seconds) }
                    launch { prepare("1 🍰 Cake     ", 4.seconds) }
                }
                peterOrder = launch(handler) {
                    launch { prepare("2 🥩 Steak    ", 2.5.seconds) }
                    launch { prepare("2 🥔 Potato   ", 2.seconds) }
                    launch { prepare("2 🫛 Peas     ", 1.seconds) }
                    launch { prepare("2 🍪 Cookie   ", 3.seconds) }
                }
            }
        }
    }
}