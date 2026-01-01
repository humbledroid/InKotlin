package lesson30.content.state09

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

suspend fun main() = coroutineScope {
    val job = launch {
        listOf("🍔", "🍟", "🍪").asFlow()
            .cancellable()
            .onEach {
                println("$it is cooking")
                Thread.sleep(1_000)
            }
            .collect { println("$it is ready to serve!") }
    }

    delay(1.5.seconds)
    println("Cancelling order")
    job.cancel()
}
