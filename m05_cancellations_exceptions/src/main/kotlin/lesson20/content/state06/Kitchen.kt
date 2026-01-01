package lesson20.content.state06

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun main() {
    runBlocking {
        val steak = async { prepare("2 🥩 Steak ", 4.seconds) }
        val potato = async { prepare("2 🥔 Potato", 2.seconds) }
        val peas = async { prepare("2 🫛 Peas  ", 1.seconds) }
        val cookie = async { prepare("2 🍪 Cookie", 3.seconds) }

        tryEating(peas)
        tryEating(potato)
        tryEating(steak)
        tryEating(cookie)
    }
}

suspend fun tryEating(food: Deferred<String>) = try {
    println("Eating ${food.await()}")
} catch (_: KitchenFireException) {
    println("Not eating that...")
}

suspend fun prepare(food: String, duration: Duration): String {
    println("$food - Preparing")
    delay(duration)
    if ("🥩" in food) throw KitchenFireException("🔥 Grill caught on fire!")
    println("$food - Ready")
    return food.split(" ")[1]
}

class KitchenFireException(message: String) : Exception(message)
