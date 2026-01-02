package learning.coroutines.cancellations

import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun main() {

    suspend fun prepare(food: String, duration: Duration) {
        println("$food - Preparing")
        delay(duration)
        if ("\uD83E\uDD69" in food) throw KitchenFireException("Grill caught fire!!")
        println("$food - Ready")
    }

    lateinit var sallyOrder: Job
    lateinit var peterOrder: Job

    runBlocking {
        val tableOrder = launch {
            sallyOrder = launch {
                launch { prepare("1 🍔 Hamburger", 5.seconds) }
                launch { prepare("1 🍟 Fries    ", 3.seconds) }
                launch { prepare("1 🥗 Salad    ", 2.seconds) }
                launch { prepare("1 🍰 Cake     ", 4.seconds) }
            }
            peterOrder = launch {
                launch {
                    try {
                        prepare("2 🥩 Steak    ", 4.seconds)
                    } catch (e: KitchenFireException) {
                        println("not making steak!!")
                    }
                }
                launch { prepare("2 🥔 Potato   ", 2.seconds) }
                launch { prepare("2 🫛 Peas     ", 1.seconds) }
                launch { prepare("2 🍪 Cookie   ", 3.seconds) }
            }
        }
    }
}

class KitchenFireException(message: String) : Exception(message)
