package lesson21.content.state06

import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

fun main() {
    runBlocking {
        launch(SupervisorJob()) {
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
