package lesson30.content.state10

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

suspend fun main() = coroutineScope {
    val job = launch {
        flowOf("🍔", "🍟", "🍪")
            .onEach {
                println("$it is cooking")
                delay(1_000)
            }
            .collect { println("$it is ready to serve!") }
    }

    delay(1.5.seconds)
    println("Cancelling order")
    job.cancel()
}
