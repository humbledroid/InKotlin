package lesson21.content.state07

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlin.time.Duration.Companion.milliseconds

fun main() {
    runBlocking {
        supervisorScope {
            println("Peter's Order")

            launch { prepare("🥩") }
            launch { prepare("🥔") }
            launch { prepare("🫛") }
            launch { prepare("🍪") }
        }
    }
}

suspend fun prepare(food: String) {
    delay(500.milliseconds)
    println(food)
}
